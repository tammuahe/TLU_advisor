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
