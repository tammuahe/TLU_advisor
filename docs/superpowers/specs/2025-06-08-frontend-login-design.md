# Frontend Login & Chat Shell — Design Spec

**Date:** 2025-06-08
**Project:** TLU Advisor

## Overview

Add login authentication and a chat page shell to the existing React + Vite + TypeScript frontend. The app already has a Tailwind CSS setup. The backend (`chat_host` on port 8080) provides JWT-based auth via `POST /auth/login`.

## Routes

| Path | Component | Auth Required | Description |
|---|---|---|---|
| `/login` | `LoginForm` | No | Email + password login |
| `/chat` | `ChatView` | Yes | Chat interface (skeleton) |

Unrecognized paths redirect to `/login`.

## Auth Flow

1. User enters email + password on `/login`
2. Frontend calls `POST http://localhost:8080/auth/login` with `{email, password}`
3. Backend returns `{token}` (JWT)
4. JWT stored in `localStorage` under key `token`
5. `AuthContext` updates its in-memory state
6. User redirected to `/chat`
7. On subsequent visits, `AuthContext` reads `localStorage` on mount to restore session
8. Logout clears `localStorage` and redirects to `/login`
9. All authenticated API calls include `Authorization: Bearer <token>` header

## File Structure

```
frontend/src/
├── context/
│   └── AuthContext.tsx        # createContext + AuthProvider with login/logout
├── components/
│   ├── ProtectedRoute.tsx    # Redirects to /login if not authenticated
│   ├── LoginForm.tsx         # Email + password form
│   └── ChatView.tsx          # Skeleton chat page
├── App.tsx                   # BrowserRouter with routes
└── main.tsx                  # Unchanged
```

## Dependencies to Install

- `react-router-dom` (routing)

## Login Page

### Layout
- Full-viewport background (slate/gray gradient)
- Centered card (white, rounded, shadow)
- Card content: "TLU Advisor" heading, email input, password input, "Sign in" button

### States & Animations

| State | Behavior | Animation |
|---|---|---|
| **Idle** | Form displayed, inputs empty | Card slides up + fades in on mount (`translate-y-4` → `translate-y-0`, `opacity-0` → `opacity-100`) |
| **Loading** | Button shows spinner (`animate-spin` circle), inputs disabled | Button text replaced by spinner |
| **Error** | Red error message above button, inputs keep values | Error message fades in; inputs get red border (`border-red-500`) with brief horizontal shake |
| **Success** | Brief pause, then navigate to `/chat` | — |

### Error Handling
- Non-2xx responses from `/auth/login` display the error message from the response body
- Network errors show "Unable to connect to server"

## Chat Page (Skeleton)

### Layout
- Full-height flex column
- **Header:** "TLU Advisor" title (left), "Sign out" button (right)
- **Message area:** Scrollable, flex-1, centered empty state ("Start a conversation" in muted text)
- **Input bar:** Text input (flex-1) + send button, fixed to bottom

### Animations
- Page entrance: subtle fade-in
- Empty state text: gentle pulse or fade
- Message bubble appearance (for future): slide-in from bottom

### State
- Empty message list (placeholder — chat logic deferred to later iteration)
- Logout button calls `logout()` from `AuthContext`

## Component Specs

### `AuthContext.tsx`
- Exports `AuthProvider` component and `useAuth` hook
- State: `{ user: { email: string } | null, token: string | null, loading: boolean }`
- Methods: `login(email, password): Promise<void>`, `logout(): void`
- On mount: reads token from `localStorage`, validates it exists, sets `user` and `token`

### `ProtectedRoute.tsx`
- Wraps `<Outlet />` from react-router-dom
- If not authenticated (no token in context), redirects to `/login`
- Shows a loading spinner while `loading` is true (initial token check)

### `LoginForm.tsx`
- Controlled form with `email` and `password` fields
- Client-side validation: email non-empty, password non-empty
- Calls `useAuth().login()` on submit
- Displays inline errors

### `ChatView.tsx`
- Renders header, message area (empty), and input bar
- Header logout button calls `useAuth().logout()`
- Send button is decorative (no-op for now)

## Open Questions / Future
- Chat API integration (streaming responses, message history)
- Persistent sessions across tabs (could use `storage` event later)
- Token refresh (not needed yet — 24h expiry)

## Commit Scope
- `frontend`
