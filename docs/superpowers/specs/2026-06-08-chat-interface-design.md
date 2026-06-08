# Chat Interface + Login Screen — Design Spec

**Date:** 2026-06-08
**Status:** Approved

## Overview

Add a login/register screen and a multi-turn chat interface to the TLU Advisor frontend. The app talks to the existing `chat_host` backend (port 8080) via JWT-authenticated REST calls.

## Requirements

1. **Login page** — email + password sign-in, with inline toggle to registration form
2. **Registration** — extends login card with additional fields (first name, last name, student code, program ID)
3. **Chat page** — full-viewport multi-turn conversation, messages accumulate in a thread
4. **Auth flow** — JWT stored in localStorage, interceptor on all API calls, redirect to login on 401
5. **Protected routing** — `/chat` requires auth; unauthenticated users redirected to `/login`

## Architecture

```
src/
├── lib/
│   └── api.ts              # fetch wrapper with JWT interceptor
├── context/
│   └── AuthContext.tsx      # auth state (token, user, login/logout/register)
├── types/
│   └── api.ts              # DTO types matching backend
├── pages/
│   ├── LoginPage.tsx        # login + register UI
│   └── ChatPage.tsx         # chat interface
├── components/
│   ├── ProtectedRoute.tsx   # auth gate wrapper
│   ├── ChatBubble.tsx       # single message bubble
│   └── MessageInput.tsx     # text input + send button
├── App.tsx                  # routing setup
├── main.tsx                 # entry (unchanged)
└── index.css                # Tailwind v4 import
```

## Component Tree

```
App
└── AuthProvider (React Context)
    └── BrowserRouter
        ├── / → redirect to /chat if authed, /login otherwise
        ├── /login → LoginPage
        │     └── LoginForm (toggles Sign In ↔ Register)
        └── /chat → ProtectedRoute
              └── ChatPage
                    ├── header (title + logout button)
                    ├── MessageList (scrollable)
                    │     └── ChatBubble[] (user: right, assistant: left)
                    └── MessageInput (text field + send, pinned bottom)
```

## Styling

- **shadcn/ui** components generated from `https://ui.shadcn.com/` via `npx shadcn@latest`
- Components used: Button, Input, Card (CardHeader, CardContent, CardFooter), Label, ScrollArea
- Tailwind CSS v4 for layout, spacing, custom chat bubble styling
- Google Fonts: Noto Serif (headings) + Inter (body), already in `index.html`
- Login: centered card on light gray background, responsive (max-width constraint, full-width on mobile)
- Chat: full viewport height, sticky header + bottom input bar, scroll area in between

## Data Flow

```
Login:
  User submits email+password
  → POST /auth/login { email, password }
  → { token }
  → store token in localStorage + AuthContext
  → redirect to /chat

Register:
  User fills all fields
  → POST /auth/register { firstName, lastName, studentCode, programId, email, password }
  → { token }
  → store token in localStorage + AuthContext
  → redirect to /chat

Send message:
  User types prompt, hits send
  → append user message to local state
  → GET /chat?prompt=... (Authorization: Bearer <token>)
  → append assistant response to local state

Auth error:
  Any API call returns 401
  → clear token from localStorage + AuthContext
  → redirect to /login
```

## API Layer

`src/lib/api.ts`:
- `VITE_API_URL` env var for base URL (default `http://localhost:8080`)
- `apiClient(path, options?)` — thin wrapper around `fetch` that prepends base URL, attaches JWT from localStorage, parses JSON
- On 401: calls `onUnauthorized()` callback (set by AuthContext) to clear auth state

## Auth Context

`src/context/AuthContext.tsx`:
- State: `{ token: string | null, isLoading: boolean }`
- `login(email, password)` → calls API, stores token
- `register(data)` → calls API, stores token
- `logout()` → clears token from localStorage + state
- `isAuthenticated` derived from `token !== null`
- On mount: reads token from localStorage (if exists) → sets state

## Types

```typescript
// src/types/api.ts
interface LoginRequest { email: string; password: string }
interface RegisterRequest { firstName: string; lastName: string; studentCode: string; programId: number; email: string; password: string }
interface AuthResponse { token: string }
interface ChatMessage { role: "user" | "assistant"; content: string }
```

## Error Handling

- Login failure: show error message below form (invalid credentials, network error)
- Registration failure: show error per-field or general message
- Chat send failure: show error toast/banner, keep message in thread but mark as failed
- No retry logic needed for MVP

## Dependencies to Add

```
shadcn/ui (via npx shadcn@latest init + add)
  → radix-ui primitives, class-variance-authority, clsx/tailwind-merge, lucide-react
```

No additional npm packages beyond what shadcn pulls in.

## Files to Create/Modify

| File | Action |
|------|--------|
| `.env` | Create with `VITE_API_URL=http://localhost:8080` |
| `src/types/api.ts` | Create |
| `src/lib/api.ts` | Create |
| `src/context/AuthContext.tsx` | Create |
| `src/pages/LoginPage.tsx` | Create |
| `src/pages/ChatPage.tsx` | Create |
| `src/components/ProtectedRoute.tsx` | Create |
| `src/components/ChatBubble.tsx` | Create |
| `src/components/MessageInput.tsx` | Create |
| `src/App.tsx` | Rewrite |
| `src/main.tsx` | No change needed |
| `src/index.css` | Add shadcn theme CSS variables |

## Out of Scope

- Conversation history sidebar (no backend endpoint)
- Message search
- User profile page
- Dark mode toggle
- Loading skeletons
- Streaming/SSE responses
- Rate limiting UI
