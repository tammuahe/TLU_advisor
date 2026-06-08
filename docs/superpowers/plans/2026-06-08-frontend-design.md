# TLU Advisor Frontend Rebuild — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Rebuild the TLU Advisor frontend from scratch with a professional corporate+academic single-column chat interface.

**Architecture:** React 19 + Vite 8 + TypeScript, React Router v7, plain Tailwind CSS v4 via @tailwindcss/vite plugin. JWT auth via AuthContext, protected routes, single-column chat layout with thin header and fixed bottom input.

**Tech Stack:** React 19, React Router v7, Tailwind CSS v4, TypeScript ~6.0, Vite 8

---

### Task 1: Ensure dependencies exist

**Files:**
- Verify: `frontend/package.json`
- Verify: `frontend/node_modules/`

- [ ] **Step 1: Check react-router-dom is installed**

```bash
npm ls react-router-dom
```
Expected: `react-router-dom@^7.0.0` listed in dependencies.

- [ ] **Step 2: Verify node_modules are present**

```bash
ls frontend/node_modules/.package-lock.json
```
If missing, run:
```bash
npm install
```

- [ ] **Step 3: Commit (if changes made)**

Only commit if `npm install` was run:
```bash
git add frontend/package-lock.json
git commit -m "chore(frontend): install dependencies"
```

---

### Task 2: Setup index.html with Google Fonts

**Files:**
- Modify: `frontend/index.html`

- [ ] **Step 1: Add Google Fonts preconnect and stylesheet links**

Write to `frontend/index.html`:

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/favicon.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Serif:ital,wght@0,400;0,500;0,600;1,400&family=Inter:ital,opsz,wght@0,14..32,400;0,14..32,500;1,14..32,400&display=swap"
      rel="stylesheet"
    />
    <title>TLU Advisor</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
```

- [ ] **Step 2: Commit**

```bash
git add frontend/index.html
git commit -m "feat(frontend): add Google Fonts for Noto Serif and Inter"
```

---

### Task 3: Setup index.css with design tokens and keyframes

**Files:**
- Modify: `frontend/src/index.css`

- [ ] **Step 1: Write index.css with @theme block and keyframe animations**

Write to `frontend/src/index.css`:

```css
@import "tailwindcss";

@theme {
  --font-heading: "Noto Serif", Georgia, serif;
  --font-body: Inter, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  --color-accent: #1e3a5f;
  --color-surface: #f8f9fa;
  --color-primary: #1a1a2e;
  --color-muted: #6b7280;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #root {
  height: 100%;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(16px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes messageIn {
  from { opacity: 0; transform: translateY(6px); }
  to { opacity: 1; transform: translateY(0); }
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/index.css
git commit -m "feat(frontend): add design tokens and professional animations"
```

---

### Task 4: Create AuthContext

**Files:**
- Create: `frontend/src/context/AuthContext.tsx`

- [ ] **Step 1: Create the context directory**

```bash
mkdir -p frontend/src/context
```

- [ ] **Step 2: Write AuthContext**

Write to `frontend/src/context/AuthContext.tsx`:

```tsx
import { createContext, useContext, useState, useCallback } from 'react'
import type { ReactNode } from 'react'

interface User {
  email: string
  token: string
}

interface AuthContextValue {
  user: User | null
  login: (email: string, password: string) => Promise<void>
  logout: () => void
  token: string | null
}

const AuthContext = createContext<AuthContextValue | null>(null)

function loadUser(): User | null {
  try {
    const raw = localStorage.getItem('auth')
    if (!raw) return null
    const parsed = JSON.parse(raw) as User
    if (!parsed.email || !parsed.token) return null
    return parsed
  } catch {
    return null
  }
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(loadUser)

  const login = useCallback(async (email: string, password: string) => {
    const res = await fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    })
    if (!res.ok) {
      if (res.status === 401) throw new Error('Invalid email or password')
      throw new Error('Unable to connect to server')
    }
    const data: User = await res.json()
    localStorage.setItem('auth', JSON.stringify(data))
    setUser(data)
  }, [])

  const logout = useCallback(() => {
    localStorage.removeItem('auth')
    setUser(null)
  }, [])

  return (
    <AuthContext.Provider value={{ user, login, logout, token: user?.token ?? null }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth(): AuthContextValue {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
```

- [ ] **Step 3: Commit**

```bash
git add frontend/src/context/AuthContext.tsx
git commit -m "feat(frontend): add AuthContext with JWT login/logout"
```

---

### Task 5: Create ProtectedRoute

**Files:**
- Create: `frontend/src/components/ProtectedRoute.tsx`

- [ ] **Step 1: Create components directory**

```bash
mkdir -p frontend/src/components
```

- [ ] **Step 2: Write ProtectedRoute**

Write to `frontend/src/components/ProtectedRoute.tsx`:

```tsx
import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function ProtectedRoute() {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/login" replace />
  }

  return <Outlet />
}
```

- [ ] **Step 3: Commit**

```bash
git add frontend/src/components/ProtectedRoute.tsx
git commit -m "feat(frontend): add ProtectedRoute guard"
```

---

### Task 6: Create LoginView

**Files:**
- Create: `frontend/src/components/LoginView.tsx`

- [ ] **Step 1: Write LoginView**

Write to `frontend/src/components/LoginView.tsx`:

```tsx
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function LoginView() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')

    if (!email.trim() || !password.trim()) {
      setError('Please fill in all fields')
      return
    }

    setLoading(true)
    try {
      await login(email, password)
      navigate('/chat')
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unable to connect to server')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-surface px-4">
      <div className="w-full animate-[slideUp_0.4s_ease-out]" style={{ maxWidth: 400 }}>
        <div className="flex items-center justify-center gap-1.5">
          <svg className="h-5 w-5 text-accent" fill="currentColor" viewBox="0 0 24 24">
            <path d="M12 3L1 9l4 2.18v6L12 21l7-3.82v-6l2-1.09V17h2V9L12 3zm6.82 6L12 12.72 5.18 9 12 5.28 18.82 9zM17 15.99l-5 2.73-5-2.73v-3.72L12 15l5-2.73v3.72z" />
          </svg>
          <h1 className="text-2xl font-semibold tracking-tight text-accent" style={{ fontFamily: 'var(--font-heading)' }}>
            TLU Advisor
          </h1>
        </div>
        <p className="mt-2 text-center text-sm text-muted">
          University Knowledge Assistant
        </p>

        <div className="mt-8 rounded-xl border border-gray-200 bg-white p-8">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-xs font-medium uppercase tracking-wider text-muted">
                Email
              </label>
              <input
                type="email"
                aria-label="Email"
                placeholder="you@tlu.edu.vn"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                disabled={loading}
                className="mt-1.5 w-full rounded-lg border border-gray-200 bg-white px-4 py-2.5 text-sm text-primary outline-none transition placeholder:text-gray-400 focus:border-accent focus:ring-2 focus:ring-accent/10 disabled:opacity-50"
              />
            </div>

            <div>
              <label className="block text-xs font-medium uppercase tracking-wider text-muted">
                Password
              </label>
              <input
                type="password"
                aria-label="Password"
                placeholder="••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={loading}
                className="mt-1.5 w-full rounded-lg border border-gray-200 bg-white px-4 py-2.5 text-sm text-primary outline-none transition placeholder:text-gray-400 focus:border-accent focus:ring-2 focus:ring-accent/10 disabled:opacity-50"
              />
            </div>

            {error && (
              <p role="alert" className="animate-[fadeIn_0.2s_ease-out] text-sm text-red-600">
                {error}
              </p>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full rounded-lg bg-accent py-3 text-sm font-medium text-white transition hover:bg-accent/90 disabled:opacity-50"
            >
              {loading ? (
                <span className="flex items-center justify-center gap-2">
                  <span className="h-4 w-4 animate-spin rounded-full border-2 border-white/30 border-t-white" />
                  Signing in...
                </span>
              ) : (
                'Sign in'
              )}
            </button>
          </form>
        </div>

        <p className="mt-6 text-center text-xs text-muted">
          Secured by TLU Identity Service
        </p>
      </div>
    </div>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/components/LoginView.tsx
git commit -m "feat(frontend): add LoginView with form validation and loading state"
```

---

### Task 7: Create MessageBubble

**Files:**
- Create: `frontend/src/components/MessageBubble.tsx`

- [ ] **Step 1: Write MessageBubble**

Write to `frontend/src/components/MessageBubble.tsx`:

```tsx
interface MessageBubbleProps {
  role: 'USER' | 'ASSISTANT'
  content: string
}

export default function MessageBubble({ role, content }: MessageBubbleProps) {
  const isUser = role === 'USER'

  return (
    <div className={`animate-[messageIn_0.25s_ease-out] flex ${isUser ? 'justify-end' : 'justify-start'}`}>
      <div
        className={`max-w-[80%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
          isUser
            ? 'bg-accent/5 border border-accent/10 text-primary'
            : 'bg-surface border border-gray-200 text-primary'
        }`}
      >
        {content}
      </div>
    </div>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/components/MessageBubble.tsx
git commit -m "feat(frontend): add MessageBubble component"
```

---

### Task 8: Create TypingIndicator

**Files:**
- Create: `frontend/src/components/TypingIndicator.tsx`

- [ ] **Step 1: Write TypingIndicator**

Write to `frontend/src/components/TypingIndicator.tsx`:

```tsx
export default function TypingIndicator() {
  return (
    <div className="animate-[fadeIn_0.2s_ease-out] flex justify-start">
      <div className="flex items-center gap-1.5 rounded-2xl border border-gray-200 bg-surface px-4 py-3">
        <span
          className="h-2 w-2 animate-pulse rounded-full bg-accent/40"
          style={{ animationDuration: '0.8s', animationDelay: '0ms' }}
        />
        <span
          className="h-2 w-2 animate-pulse rounded-full bg-accent/40"
          style={{ animationDuration: '0.8s', animationDelay: '150ms' }}
        />
        <span
          className="h-2 w-2 animate-pulse rounded-full bg-accent/40"
          style={{ animationDuration: '0.8s', animationDelay: '300ms' }}
        />
      </div>
    </div>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/components/TypingIndicator.tsx
git commit -m "feat(frontend): add TypingIndicator component"
```

---

### Task 9: Create ChatInput

**Files:**
- Create: `frontend/src/components/ChatInput.tsx`

- [ ] **Step 1: Write ChatInput**

Write to `frontend/src/components/ChatInput.tsx`:

```tsx
interface ChatInputProps {
  value: string
  onChange: (value: string) => void
  onSend: () => void
  disabled: boolean
}

export default function ChatInput({ value, onChange, onSend, disabled }: ChatInputProps) {
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      onSend()
    }
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSend()
  }

  return (
    <footer className="shrink-0 border-t border-gray-200 bg-white px-4 py-3">
      <form onSubmit={handleSubmit} className="mx-auto flex max-w-3xl gap-2">
        <input
          type="text"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Ask about courses, programs, fees..."
          aria-label="Chat message"
          disabled={disabled}
          className="flex-1 rounded-xl border border-gray-200 bg-white px-4 py-3 text-sm text-primary outline-none transition placeholder:text-gray-400 focus:border-accent focus:ring-2 focus:ring-accent/10 disabled:opacity-50"
        />
        <button
          type="submit"
          disabled={disabled || !value.trim()}
          className="rounded-xl bg-accent px-5 py-3 text-sm font-medium text-white transition hover:bg-accent/90 disabled:opacity-30"
        >
          Send
        </button>
      </form>
    </footer>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/components/ChatInput.tsx
git commit -m "feat(frontend): add ChatInput component"
```

---

### Task 10: Create ChatView

**Files:**
- Create: `frontend/src/components/ChatView.tsx`

- [ ] **Step 1: Write ChatView**

Write to `frontend/src/components/ChatView.tsx`:

```tsx
import { useState, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import MessageBubble from './MessageBubble'
import TypingIndicator from './TypingIndicator'
import ChatInput from './ChatInput'

interface Message {
  role: 'USER' | 'ASSISTANT'
  content: string
}

export default function ChatView() {
  const navigate = useNavigate()
  const { user, token, logout } = useAuth()
  const [messages, setMessages] = useState<Message[]>([])
  const [input, setInput] = useState('')
  const [sending, setSending] = useState(false)
  const bottomRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages, sending])

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const sendMessage = async () => {
    const text = input.trim()
    if (!text || sending) return

    const userMsg: Message = { role: 'USER', content: text }
    setMessages((prev) => [...prev, userMsg])
    setInput('')
    setSending(true)

    try {
      const res = await fetch(
        `http://localhost:8080/chat?prompt=${encodeURIComponent(text)}`,
        { headers: { Authorization: `Bearer ${token}` } },
      )
      if (!res.ok) throw new Error('Failed to get response')
      const data: Message = await res.json()
      setMessages((prev) => [...prev, data])
    } catch {
      setMessages((prev) => [
        ...prev,
        { role: 'ASSISTANT', content: 'Sorry, something went wrong. Please try again.' },
      ])
    } finally {
      setSending(false)
    }
  }

  return (
    <div className="flex h-screen flex-col bg-white">
      <header className="flex shrink-0 items-center justify-between border-b border-gray-200 px-6" style={{ height: 48 }}>
        <div className="flex items-center gap-1.5">
          <svg className="h-4 w-4 text-accent" fill="currentColor" viewBox="0 0 24 24">
            <path d="M12 3L1 9l4 2.18v6L12 21l7-3.82v-6l2-1.09V17h2V9L12 3zm6.82 6L12 12.72 5.18 9 12 5.28 18.82 9zM17 15.99l-5 2.73-5-2.73v-3.72L12 15l5-2.73v3.72z" />
          </svg>
          <h1 className="text-sm font-semibold tracking-tight text-accent" style={{ fontFamily: 'var(--font-heading)' }}>
            TLU Advisor
          </h1>
        </div>
        <div className="flex items-center gap-3">
          <span className="text-xs text-muted">{user?.email}</span>
          <button
            onClick={handleLogout}
            className="rounded-lg px-3 py-1.5 text-xs font-medium text-muted transition hover:bg-surface hover:text-primary"
          >
            Sign out
          </button>
        </div>
      </header>

      <main className="flex-1 overflow-y-auto">
        <div className="mx-auto flex w-full max-w-3xl flex-col gap-3 px-6 py-4">
          {messages.length === 0 && (
            <div className="flex flex-col items-center justify-center py-24">
              <div className="mb-4 rounded-full bg-accent/5 p-3">
                <svg className="h-6 w-6 text-accent" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={1.5}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M9.813 15.904L9 18.75l-.813-2.846a4.5 4.5 0 00-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 003.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 003.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 00-3.09 3.09zM18.259 8.715L18 9.75l-.259-1.035a3.375 3.375 0 00-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 002.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 002.455 2.456L21.75 6l-1.036.259a3.375 3.375 0 00-2.455 2.456z" />
                </svg>
              </div>
              <p className="text-sm text-muted">
                Ask about courses, programs, fees, or professors
              </p>
            </div>
          )}

          {messages.map((msg, i) => (
            <MessageBubble key={i} role={msg.role} content={msg.content} />
          ))}

          {sending && <TypingIndicator />}

          <div ref={bottomRef} />
        </div>
      </main>

      <ChatInput
        value={input}
        onChange={setInput}
        onSend={sendMessage}
        disabled={sending}
      />
    </div>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/components/ChatView.tsx
git commit -m "feat(frontend): add ChatView with message history and API integration"
```

---

### Task 11: Wire up App.tsx with routes

**Files:**
- Modify: `frontend/src/App.tsx`

- [ ] **Step 1: Write App.tsx with BrowserRouter, AuthProvider, and routes**

Write to `frontend/src/App.tsx`:

```tsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import LoginView from './components/LoginView'
import ChatView from './components/ChatView'

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<LoginView />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/chat" element={<ChatView />} />
          </Route>
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}
```

- [ ] **Step 2: Commit**

```bash
git add frontend/src/App.tsx
git commit -m "feat(frontend): wire up routes with AuthProvider and ProtectedRoute"
```

---

### Task 12: Lint and verify

**Files:**
- Verify: all files above

- [ ] **Step 1: Run ESLint**

```bash
npm run lint
```
Expected: zero errors, zero warnings.

- [ ] **Step 2: Run TypeScript type-check**

```bash
npx tsc -b
```
Expected: zero type errors.

- [ ] **Step 3: Commit (if any lint fixes needed)**

Only if changes were made to fix lint issues:
```bash
git add -A
git commit -m "chore(frontend): fix lint and type issues"
```

---

### Task 13: Manual verification checklist

**No files to modify. Run the dev server and test manually.**

- [ ] **Step 1: Start dev server**

```bash
npm run dev
```

- [ ] **Step 2: Navigate to `http://localhost:5173`**
  - Should redirect to `/login`
  - Login form visible with "TLU Advisor" heading

- [ ] **Step 3: Submit empty form**
  - Should show "Please fill in all fields" error

- [ ] **Step 4: Login with invalid credentials**
  - Should show "Invalid email or password" error

- [ ] **Step 5: Login with valid credentials** (`tammuahe2004@gmail.com` / `test`)
  - Should redirect to `/chat`
  - Header shows user email
  - Empty state message visible

- [ ] **Step 6: Send a chat message**
  - User message appears right-aligned with blue-tinted background
  - Typing indicator shows while loading
  - Assistant response appears left-aligned
  - Input clears after send

- [ ] **Step 7: Click Sign out**
  - Should redirect to `/login`
  - Chat page not accessible without re-login

- [ ] **Step 8: Check Vietnamese text rendering**
  - Send message with Vietnamese characters: `tôi muốn hỏi`
  - All diacritics render correctly in both input and messages
