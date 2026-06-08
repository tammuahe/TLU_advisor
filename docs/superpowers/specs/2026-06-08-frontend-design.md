# Frontend Rebuild — Design Spec

**Date**: 2026-06-08
**Aesthetic**: Corporate + Academic, Light Theme, Professional
**Layout**: Single-column focused (chat-centric)

## Overview

Full rebuild of the TLU Advisor frontend. JWT login, then a single-column chat interface. Clean, professional aesthetic with institutional blue accents. No component library — plain Tailwind CSS v4 with custom CSS animations.

## Design Tokens

### Colors
| Token     | Value     | Usage |
|-----------|-----------|-------|
| bg        | `#ffffff` | Page background |
| surface   | `#f8f9fa` | Card/section background, login page bg |
| border    | `#e5e7eb` | Card borders, input borders |
| text      | `#1a1a2e` | Primary text |
| muted     | `#6b7280` | Secondary text, placeholders |
| accent    | `#1e3a5f` | Buttons, links, focus rings |
| error     | `#dc2626` | Error text, error borders |

### Typography
| Role     | Font            | Weight | Source |
|----------|-----------------|--------|--------|
| Headings | Noto Serif      | 500-600 | Google Fonts |
| Body     | Inter           | 400-500 | Google Fonts |
| Fallback | Georgia, system | —      | —           |

Both load with `font-display: swap` for Vietnamese diacritics resilience.

### Spacing
- Login card: max-width 400px, centered
- Chat: max-width 3xl (768px) content area, centered
- Header: 48px height
- Input bar: 64px height

### Animations
- **slideUp**: login card entrance (0.4s ease-out, fade+translateY)
- **fadeIn**: error messages (0.2s ease-out, opacity only)
- **messageIn**: chat messages (0.25s ease-out, fade+translateY)
- **Typing dots**: pulsing opacity staggered animation
- **No hover scaling, no shake, no bounce**

### Corners & Borders
- Buttons: `rounded-lg` (8px)
- Inputs: `rounded-lg` (8px)
- Cards: `rounded-xl` (12px)
- Message bubbles: `rounded-2xl` (16px)
- Borders: `border border-slate-200` throughout

## Routes

| Path    | Component       | Auth        |
|---------|-----------------|-------------|
| `/login` | LoginView       | public      |
| `/chat`  | ChatView        | protected   |
| `*`      | redirect `/login` | —         |

## Architecture

```
App
├── AuthProvider (context)
│   ├── user: { email, token } | null
│   ├── login(email, password) → Promise
│   └── logout() → void
└── BrowserRouter
    ├── /login  → <LoginView />
    │   └── LoginForm (local state: email, password, error, loading)
    └── /chat   → <ProtectedRoute> → <ChatView />
        ├── ChatHeader
        │   ├── Logo "TLU Advisor" (Noto Serif, accent color)
        │   └── User email + Sign out button
        ├── MessageList (scrollable, flex-1, auto-scroll-bottom via ref)
        │   ├── EmptyState ("Ask about courses, programs, fees...")
        │   ├── MessageBubble (right: user, left: assistant)
        │   └── TypingIndicator (3 pulsing dots)
        └── ChatInput (fixed bottom)
            ├── Text input (max-w-3xl mx-auto, full-width on mobile)
            ├── Send button (accent blue, right of input)
            └── Enter-to-send, Shift+Enter for newline
```

## Component Responsibilities

### AuthContext (`src/context/AuthContext.tsx`)
- Lazy `useState` initializer reads `localStorage["auth"]` — `{ email, token } | null`
- `login(email, password)`: POST `http://localhost:8080/login`, stores JWT in localStorage
- `logout()`: clears localStorage and state
- Exports `useAuth()` hook
- `// eslint-disable-next-line react-refresh/only-export-components` on the file

### ProtectedRoute (`src/components/ProtectedRoute.tsx`)
- If `user` is null, `<Navigate to="/login" replace />`
- If `user` exists, render `<Outlet />`

### LoginView (`src/components/LoginView.tsx`)
- Full viewport height, surface background
- Centered white card (max-w-[400px], rounded-xl, border)
- "TLU Advisor" heading (Noto Serif, text-accent)
- Subtitle "University Knowledge Assistant" (text-muted)
- Email input (type=email, aria-label)
- Password input (type=password, aria-label)
- Error message (text-error, aria role="alert")
- Submit button (bg-accent, text-white, full-width)
- Loading state: spinner icon + "Signing in..."

### ChatView (`src/components/ChatView.tsx`)
- Full viewport height, flex column
- State: `messages: Message[]`, `input: string`, `sending: boolean`
- On send: appends user message, calls `GET /chat?prompt=...` with JWT header, appends response
- Error handling: appends error message as assistant-style message
- Messages array only — no message ID, no conversation switching

### MessageBubble (`src/components/MessageBubble.tsx`)
- User messages: right-aligned, bg-accent/5 border-accent/10
- Assistant messages: left-aligned, bg-surface border-slate-200
- Props: `role: "USER" | "ASSISTANT"`, `content: string`

### TypingIndicator (`src/components/TypingIndicator.tsx`)
- Three small circles, staggered pulse animation
- Left-aligned in the message area
- Rendered when `sending` is true

### ChatInput (`src/components/ChatInput.tsx`)
- Fixed to bottom of viewport
- Border-top, bg-white, backdrop-blur
- Input with placeholder text
- Disabled during sending
- Enter sends, Shift+Enter adds newline

## API Integration

### Login
```
POST http://localhost:8080/login
Content-Type: application/json
{ "email": "...", "password": "..." }

Response: { "email": "...", "token": "..." }
```

### Chat
```
GET http://localhost:8080/chat?prompt=...
Authorization: Bearer <token>

Response: { "role": "ASSISTANT", "content": "..." }
```

No streaming — blocking GET only.

## Implementation Scope

### Will implement
- AuthContext, ProtectedRoute, LoginView, ChatView, MessageBubble, TypingIndicator, ChatInput
- Route wiring in App.tsx
- index.css with @theme block for design tokens and keyframes
- index.html with Google Fonts preconnect

### Will NOT implement
- Conversation history/sidebar
- Message editing, deleting, or regenerating
- Markdown rendering in messages
- Streaming responses
- Registration page
- Mobile-responsive design beyond basic padding

## File Manifest

```
frontend/
├── index.html                        ← Google Fonts links
├── src/
│   ├── main.tsx                      ← StrictMode + render
│   ├── App.tsx                       ← BrowserRouter + AuthProvider + Routes
│   ├── index.css                     ← Tailwind imports + @theme + keyframes
│   ├── context/
│   │   └── AuthContext.tsx           ← JWT auth state
│   └── components/
│       ├── ProtectedRoute.tsx        ← Auth gate
│       ├── LoginView.tsx             ← Login page
│       ├── ChatView.tsx              ← Main chat shell
│       ├── MessageBubble.tsx         ← Single message
│       ├── TypingIndicator.tsx       ← Loading dots
│       └── ChatInput.tsx             ← Message input bar
```

## Success Criteria

- Lint passes (`npm run lint`)
- Login with seed user `tammuahe2004@gmail.com` / `test` redirects to `/chat`
- Chat input sends `GET /chat?prompt=...` and displays response
- Error states: invalid credentials, network failure, empty fields
- All text renders correctly with Vietnamese diacritics
