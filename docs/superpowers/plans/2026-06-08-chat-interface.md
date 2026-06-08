# Chat Interface + Login Screen Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build login/register screen + full-viewport multi-turn chat interface with JWT auth using shadcn/ui components.

**Architecture:** React Context for auth state, fetch-based API client with JWT interceptor, React Router v7 for routing (/login, /chat). shadcn/ui provides Button, Input, Card, Label. Custom components for ChatBubble and MessageInput.

**Tech Stack:** React 19, TypeScript, Tailwind CSS v4, shadcn/ui, React Router v7, fetch API, localStorage for JWT.

---

## Task 1: Project Setup — shadcn/ui + path aliases + .env

**Files:**
- Create: `.env`
- Create: `components.json`
- Create: `src/lib/utils.ts`
- Modify: `src/index.css`
- Modify: `tsconfig.app.json`
- Modify: `vite.config.ts`

- [ ] **Step 1: Create .env**

```bash
echo 'VITE_API_URL=http://localhost:8080' > /mnt/d/code/TLU_advisor/frontend/.env
```

- [ ] **Step 2: Install shadcn dependencies**

```bash
npm install class-variance-authority clsx tailwind-merge lucide-react
```

- [ ] **Step 3: Create components.json**

File: `components.json`
```json
{
  "$schema": "https://ui.shadcn.com/schema.json",
  "style": "default",
  "rsc": false,
  "tsx": true,
  "tailwind": {
    "config": "",
    "css": "src/index.css",
    "baseColor": "neutral",
    "cssVariables": true,
    "prefix": ""
  },
  "aliases": {
    "components": "@/components",
    "utils": "@/lib/utils",
    "ui": "@/components/ui",
    "lib": "@/lib",
    "hooks": "@/hooks"
  }
}
```

- [ ] **Step 4: Create src/lib/utils.ts**

File: `src/lib/utils.ts`
```ts
import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}
```

- [ ] **Step 5: Create directories**

```bash
mkdir -p src/components/ui src/context src/lib src/pages src/types
```

- [ ] **Step 6: Add path alias to tsconfig.app.json**

Read the current content first, then add `baseUrl` and `paths`:

```json
{
  "extends": "./tsconfig.json",
  "compilerOptions": {
    "tsBuildInfoFile": "./node_modules/.tmp/tsconfig.app.tsBuildInfo",
    "target": "ES2023",
    "useDefineForClassFields": true,
    "lib": ["ES2023", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "skipLibCheck": true,
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "verbatimModuleSyntax": true,
    "erasableSyntaxOnly": true,
    "isolatedModules": true,
    "moduleDetection": "force",
    "noEmit": true,
    "jsx": "react-jsx",
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "noUncheckedSideEffectImports": true,
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    }
  },
  "include": ["src"]
}
```

- [ ] **Step 7: Add path alias to vite.config.ts**

```ts
import path from "node:path"
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({
  plugins: [tailwindcss(), react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
```

- [ ] **Step 8: Update src/index.css with shadcn theme**

```css
@import "tailwindcss";

@layer base {
  :root {
    --background: 0 0% 100%;
    --foreground: 0 0% 3.9%;
    --card: 0 0% 100%;
    --card-foreground: 0 0% 3.9%;
    --popover: 0 0% 100%;
    --popover-foreground: 0 0% 3.9%;
    --primary: 0 0% 9%;
    --primary-foreground: 0 0% 98%;
    --secondary: 0 0% 96.1%;
    --secondary-foreground: 0 0% 9%;
    --muted: 0 0% 96.1%;
    --muted-foreground: 0 0% 45.1%;
    --accent: 0 0% 96.1%;
    --accent-foreground: 0 0% 9%;
    --destructive: 0 84.2% 60.2%;
    --destructive-foreground: 0 0% 98%;
    --border: 0 0% 89.8%;
    --input: 0 0% 89.8%;
    --ring: 0 0% 3.9%;
    --chart-1: 12 76% 61%;
    --chart-2: 173 58% 39%;
    --chart-3: 197 37% 24%;
    --chart-4: 43 74% 66%;
    --chart-5: 27 87% 67%;
    --radius: 0.5rem;
  }
}

@layer base {
  * {
    @apply border-border;
  }
  body {
    @apply bg-background text-foreground;
    font-family: "Inter", sans-serif;
  }
}
```

- [ ] **Step 9: Generate shadcn ui components**

```bash
npx shadcn@latest add button input card label scroll-area --overwrite
```

- [ ] **Step 10: Verify setup compiles**

```bash
npm run build
```

Expected: Build succeeds. If shadcn add prompts for confirmation, type `y`.

- [ ] **Step 11: Commit**

```bash
git add .env components.json src/lib/utils.ts src/index.css tsconfig.app.json vite.config.ts src/components/ui/ package.json package-lock.json
git commit -m "chore(frontend): set up shadcn/ui, path aliases, and env"
```

---

## Task 2: TypeScript types

**Files:**
- Create: `src/types/api.ts`

- [ ] **Step 1: Create src/types/api.ts**

```ts
export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  firstName: string
  lastName: string
  studentCode: string
  programId: number
  email: string
  password: string
}

export interface AuthResponse {
  token: string
}

export interface ChatMessage {
  role: "user" | "assistant"
  content: string
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/types/api.ts
git commit -m "feat(frontend): add API TypeScript types"
```

---

## Task 3: API client

**Files:**
- Create: `src/lib/api.ts`

- [ ] **Step 1: Create src/lib/api.ts**

```ts
type UnauthorizedHandler = () => void

let onUnauthorized: UnauthorizedHandler | null = null

export function setUnauthorizedHandler(handler: UnauthorizedHandler) {
  onUnauthorized = handler
}

export async function apiClient<T>(
  path: string,
  options?: RequestInit
): Promise<T> {
  const baseUrl = import.meta.env.VITE_API_URL || "http://localhost:8080"
  const token = localStorage.getItem("token")

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  }

  if (options?.headers) {
    const incoming = options.headers as Record<string, string>
    for (const key of Object.keys(incoming)) {
      headers[key] = incoming[key]
    }
  }

  if (token) {
    headers["Authorization"] = `Bearer ${token}`
  }

  const response = await fetch(`${baseUrl}${path}`, {
    ...options,
    headers,
  })

  if (response.status === 401) {
    onUnauthorized?.()
    throw new Error("Session expired. Please log in again.")
  }

  if (!response.ok) {
    let message = "Request failed"
    try {
      const error = await response.json()
      if (error.message) message = error.message
    } catch {
      // use default message
    }
    throw new Error(message)
  }

  return response.json() as Promise<T>
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/lib/api.ts
git commit -m "feat(frontend): add API client with JWT interceptor"
```

---

## Task 4: AuthContext

**Files:**
- Create: `src/context/AuthContext.tsx`

- [ ] **Step 1: Create src/context/AuthContext.tsx**

```tsx
import {
  createContext,
  useContext,
  useState,
  useEffect,
  type ReactNode,
} from "react"
import { setUnauthorizedHandler, apiClient } from "@/lib/api"
import type { AuthResponse, RegisterRequest } from "@/types/api"

interface AuthContextType {
  token: string | null
  isLoading: boolean
  login: (email: string, password: string) => Promise<void>
  register: (data: RegisterRequest) => Promise<void>
  logout: () => void
  isAuthenticated: boolean
}

const AuthContext = createContext<AuthContextType | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const stored = localStorage.getItem("token")
    if (stored) setToken(stored)
    setIsLoading(false)
  }, [])

  useEffect(() => {
    setUnauthorizedHandler(() => {
      setToken(null)
      localStorage.removeItem("token")
    })
  }, [])

  async function login(email: string, password: string) {
    const { token } = await apiClient<AuthResponse>("/auth/login", {
      method: "POST",
      body: JSON.stringify({ email, password }),
    })
    localStorage.setItem("token", token)
    setToken(token)
  }

  async function register(data: RegisterRequest) {
    const { token } = await apiClient<AuthResponse>("/auth/register", {
      method: "POST",
      body: JSON.stringify(data),
    })
    localStorage.setItem("token", token)
    setToken(token)
  }

  function logout() {
    localStorage.removeItem("token")
    setToken(null)
  }

  return (
    <AuthContext.Provider
      value={{
        token,
        isLoading,
        login,
        register,
        logout,
        isAuthenticated: token !== null,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth(): AuthContextType {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error("useAuth must be used within AuthProvider")
  return ctx
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/context/AuthContext.tsx
git commit -m "feat(frontend): add AuthContext with login/register/logout"
```

---

## Task 5: ProtectedRoute component

**Files:**
- Create: `src/components/ProtectedRoute.tsx`

- [ ] **Step 1: Create src/components/ProtectedRoute.tsx**

```tsx
import { Navigate } from "react-router-dom"
import { useAuth } from "@/context/AuthContext"

export default function ProtectedRoute({
  children,
}: {
  children: React.ReactNode
}) {
  const { isAuthenticated, isLoading } = useAuth()

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p className="text-muted-foreground">Loading...</p>
      </div>
    )
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  return <>{children}</>
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/components/ProtectedRoute.tsx
git commit -m "feat(frontend): add ProtectedRoute auth gate"
```

---

## Task 6: ChatBubble component

**Files:**
- Create: `src/components/ChatBubble.tsx`

- [ ] **Step 1: Create src/components/ChatBubble.tsx**

```tsx
import type { ChatMessage } from "@/types/api"

interface Props {
  message: ChatMessage
}

export default function ChatBubble({ message }: Props) {
  const isUser = message.role === "user"

  return (
    <div className={`flex ${isUser ? "justify-end" : "justify-start"}`}>
      <div
        className={`max-w-[75%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
          isUser
            ? "bg-primary text-primary-foreground rounded-br-md"
            : "bg-muted text-foreground rounded-bl-md"
        }`}
      >
        {message.content}
      </div>
    </div>
  )
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/components/ChatBubble.tsx
git commit -m "feat(frontend): add ChatBubble component"
```

---

## Task 7: MessageInput component

**Files:**
- Create: `src/components/MessageInput.tsx`

- [ ] **Step 1: Create src/components/MessageInput.tsx**

```tsx
import { useState, type FormEvent, type KeyboardEvent } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Send } from "lucide-react"

interface Props {
  onSend: (message: string) => void
  disabled?: boolean
}

export default function MessageInput({ onSend, disabled }: Props) {
  const [value, setValue] = useState("")

  function handleSubmit(e: FormEvent) {
    e.preventDefault()
    if (!value.trim() || disabled) return
    onSend(value.trim())
    setValue("")
  }

  function handleKeyDown(e: KeyboardEvent<HTMLInputElement>) {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault()
      handleSubmit(e)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex gap-2">
      <Input
        value={value}
        onChange={(e) => setValue(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="Type your message..."
        disabled={disabled}
        className="flex-1"
      />
      <Button type="submit" disabled={disabled || !value.trim()}>
        <Send className="h-4 w-4" />
      </Button>
    </form>
  )
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/components/MessageInput.tsx
git commit -m "feat(frontend): add MessageInput component"
```

---

## Task 8: LoginPage

**Files:**
- Create: `src/pages/LoginPage.tsx`

- [ ] **Step 1: Create src/pages/LoginPage.tsx**

```tsx
import { useState, type FormEvent } from "react"
import { useAuth } from "@/context/AuthContext"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function LoginPage() {
  const { login, register } = useAuth()
  const [mode, setMode] = useState<"signin" | "register">("signin")
  const [error, setError] = useState("")
  const [loading, setLoading] = useState(false)

  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [firstName, setFirstName] = useState("")
  const [lastName, setLastName] = useState("")
  const [studentCode, setStudentCode] = useState("")
  const [programId, setProgramId] = useState("")

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setError("")
    setLoading(true)

    try {
      if (mode === "signin") {
        await login(email, password)
      } else {
        await register({
          email,
          password,
          firstName,
          lastName,
          studentCode,
          programId: parseInt(programId, 10),
        })
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : "An error occurred")
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-muted p-4">
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="font-['Noto_Serif'] text-2xl">
            TLU Advisor
          </CardTitle>
          <CardDescription>AI academic guidance</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            {mode === "register" && (
              <>
                <div className="grid grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="firstName">First name</Label>
                    <Input
                      id="firstName"
                      value={firstName}
                      onChange={(e) => setFirstName(e.target.value)}
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="lastName">Last name</Label>
                    <Input
                      id="lastName"
                      value={lastName}
                      onChange={(e) => setLastName(e.target.value)}
                      required
                    />
                  </div>
                </div>
                <div className="space-y-2">
                  <Label htmlFor="studentCode">Student code</Label>
                  <Input
                    id="studentCode"
                    value={studentCode}
                    onChange={(e) => setStudentCode(e.target.value)}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="programId">Program ID</Label>
                  <Input
                    id="programId"
                    type="number"
                    value={programId}
                    onChange={(e) => setProgramId(e.target.value)}
                    required
                  />
                </div>
              </>
            )}
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            {error && <p className="text-sm text-destructive">{error}</p>}
            <Button type="submit" className="w-full" disabled={loading}>
              {loading
                ? "Please wait..."
                : mode === "signin"
                  ? "Sign In"
                  : "Register"}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="justify-center">
          <button
            type="button"
            className="text-sm text-muted-foreground hover:text-foreground"
            onClick={() => {
              setMode(mode === "signin" ? "register" : "signin")
              setError("")
            }}
          >
            {mode === "signin"
              ? "No account? Register"
              : "Already have an account? Sign in"}
          </button>
        </CardFooter>
      </Card>
    </div>
  )
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/pages/LoginPage.tsx
git commit -m "feat(frontend): add LoginPage with sign-in and register toggle"
```

---

## Task 9: ChatPage

**Files:**
- Create: `src/pages/ChatPage.tsx`

- [ ] **Step 1: Create src/pages/ChatPage.tsx**

```tsx
import { useState, useRef, useEffect } from "react"
import { useAuth } from "@/context/AuthContext"
import { apiClient } from "@/lib/api"
import type { ChatMessage } from "@/types/api"
import { Button } from "@/components/ui/button"
import ChatBubble from "@/components/ChatBubble"
import MessageInput from "@/components/MessageInput"
import { LogOut } from "lucide-react"

export default function ChatPage() {
  const { logout } = useAuth()
  const [messages, setMessages] = useState<ChatMessage[]>([])
  const [sending, setSending] = useState(false)
  const [error, setError] = useState("")
  const scrollRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    scrollRef.current?.scrollTo({
      top: scrollRef.current.scrollHeight,
      behavior: "smooth",
    })
  }, [messages])

  async function handleSend(prompt: string) {
    const userMessage: ChatMessage = { role: "user", content: prompt }
    setMessages((prev) => [...prev, userMessage])
    setSending(true)
    setError("")

    try {
      const response = await apiClient<ChatMessage>(
        `/chat?prompt=${encodeURIComponent(prompt)}`
      )
      setMessages((prev) => [...prev, response])
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to send message"
      )
    } finally {
      setSending(false)
    }
  }

  return (
    <div className="h-screen flex flex-col bg-background">
      <header className="shrink-0 border-b px-6 py-4 flex items-center justify-between">
        <h1 className="font-['Noto_Serif'] text-xl font-semibold">
          TLU Advisor
        </h1>
        <Button variant="ghost" size="icon" onClick={logout} title="Logout">
          <LogOut className="h-5 w-5" />
        </Button>
      </header>

      <div ref={scrollRef} className="flex-1 overflow-y-auto p-4 space-y-4">
        {messages.length === 0 && (
          <p className="text-center text-muted-foreground mt-20">
            Ask a question to get started
          </p>
        )}
        {messages.map((msg, i) => (
          <ChatBubble key={i} message={msg} />
        ))}
        {sending && (
          <div className="flex justify-start">
            <div className="max-w-[75%] rounded-2xl rounded-bl-md px-4 py-3 text-sm bg-muted">
              <span className="inline-flex gap-1">
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce" />
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce [animation-delay:0.15s]" />
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce [animation-delay:0.3s]" />
              </span>
            </div>
          </div>
        )}
        {error && (
          <div className="rounded-lg bg-destructive/10 border border-destructive/20 p-3 text-sm text-destructive">
            {error}
          </div>
        )}
      </div>

      <div className="shrink-0 border-t p-4">
        <MessageInput onSend={handleSend} disabled={sending} />
      </div>
    </div>
  )
}
```

- [ ] **Step 2: Verify compilation**

```bash
npx tsc --noEmit
```

Expected: No errors.

- [ ] **Step 3: Commit**

```bash
git add src/pages/ChatPage.tsx
git commit -m "feat(frontend): add ChatPage with message thread and auto-scroll"
```

---

## Task 10: Wire App.tsx routing

**Files:**
- Modify: `src/App.tsx`

- [ ] **Step 1: Rewrite src/App.tsx**

```tsx
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom"
import { AuthProvider, useAuth } from "@/context/AuthContext"
import ProtectedRoute from "@/components/ProtectedRoute"
import LoginPage from "@/pages/LoginPage"
import ChatPage from "@/pages/ChatPage"

function HomeRedirect() {
  const { isAuthenticated, isLoading } = useAuth()

  if (isLoading) return null
  return <Navigate to={isAuthenticated ? "/chat" : "/login"} replace />
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<HomeRedirect />} />
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/chat"
            element={
              <ProtectedRoute>
                <ChatPage />
              </ProtectedRoute>
            }
          />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}
```

- [ ] **Step 2: Full build and lint**

```bash
npm run build && npm run lint
```

Expected: Build succeeds, lint passes with no errors.

- [ ] **Step 3: Commit**

```bash
git add src/App.tsx
git commit -m "feat(frontend): wire up routing with auth-protected chat"
```
