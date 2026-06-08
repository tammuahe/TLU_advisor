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
