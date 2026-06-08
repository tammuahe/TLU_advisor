import { createContext, useContext, useState, type ReactNode } from 'react'

interface AuthState {
  user: { email: string } | null
  token: string | null
  loading: boolean
}

interface AuthContextType extends AuthState {
  login: (email: string, password: string) => Promise<void>
  logout: () => void
}

const AuthContext = createContext<AuthContextType | null>(null)

function loadInitialState(): AuthState {
  try {
    const stored = localStorage.getItem('auth')
    if (stored) {
      const { email, token } = JSON.parse(stored)
      return { user: { email }, token, loading: false }
    }
  } catch {
    /* corrupted data — treat as unauthenticated */
  }
  return { user: null, token: null, loading: false }
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [state, setState] = useState<AuthState>(loadInitialState)

  const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

  const login = async (email: string, password: string) => {
    let res: Response
    try {
      res = await fetch(`${baseUrl}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      })
    } catch {
      throw new Error('Unable to connect to server')
    }
    if (!res.ok) {
      const err = await res.text()
      throw new Error(err || 'Login failed')
    }
    const { token } = await res.json()
    localStorage.setItem('auth', JSON.stringify({ email, token }))
    setState({ user: { email }, token, loading: false })
  }

  const logout = () => {
    localStorage.removeItem('auth')
    setState({ user: null, token: null, loading: false })
  }

  return (
    <AuthContext.Provider value={{ ...state, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
