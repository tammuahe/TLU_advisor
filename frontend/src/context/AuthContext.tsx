import { useState, useEffect, type ReactNode } from "react"
import { setUnauthorizedHandler, apiClient } from "@/lib/api"
import { AuthContext } from "@/context/useAuth"
import type { AuthResponse } from "@/types/api"

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(
    () => localStorage.getItem("token")
  )

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

  function logout() {
    localStorage.removeItem("token")
    setToken(null)
  }

  return (
    <AuthContext.Provider
      value={{
        token,
        login,
        logout,
        isAuthenticated: token !== null,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}
