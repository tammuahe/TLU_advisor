import { createContext, useContext } from "react"
import type { RegisterRequest } from "@/types/api"

export interface AuthContextType {
  token: string | null
  login: (email: string, password: string) => Promise<void>
  register: (data: RegisterRequest) => Promise<void>
  logout: () => void
  isAuthenticated: boolean
}

export const AuthContext = createContext<AuthContextType | null>(null)

export function useAuth(): AuthContextType {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error("useAuth must be used within AuthProvider")
  return ctx
}
