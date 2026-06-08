import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom"
import { AuthProvider } from "@/context/AuthContext"
import { useAuth } from "@/context/useAuth"
import ProtectedRoute from "@/components/ProtectedRoute"
import LoginPage from "@/pages/LoginPage"
import ChatPage from "@/pages/ChatPage"

function HomeRedirect() {
  const { isAuthenticated } = useAuth()
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
