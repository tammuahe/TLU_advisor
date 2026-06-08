import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function ChatView() {
  const navigate = useNavigate()
  const { user, logout } = useAuth()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <div className="flex h-screen flex-col bg-gray-50">
      {/* Header */}
      <header className="flex items-center justify-between border-b bg-white px-6 py-4 shadow-sm">
        <h1 className="text-lg font-bold text-gray-800">TLU Advisor</h1>
        <div className="flex items-center gap-3">
          <span className="text-sm text-gray-500">{user?.email}</span>
          <button
            onClick={handleLogout}
            className="rounded-lg bg-gray-100 px-3 py-1.5 text-sm text-gray-600 transition-colors hover:bg-gray-200"
          >
            Sign out
          </button>
        </div>
      </header>

      {/* Message area */}
      <main className="flex flex-1 flex-col items-center justify-center px-4">
        <p className="animate-[fadeIn_0.5s_ease-out] text-center text-gray-400">
          Start a conversation
        </p>
      </main>

      {/* Input bar */}
      <footer className="border-t bg-white px-4 py-4">
        <div className="mx-auto flex max-w-3xl gap-2">
          <input
            type="text"
            placeholder="Type a message..."
            disabled
            className="flex-1 rounded-lg border border-gray-300 px-4 py-2.5 text-sm outline-none transition-colors focus:border-blue-500"
          />
          <button
            disabled
            className="rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white opacity-50"
          >
            Send
          </button>
        </div>
      </footer>
    </div>
  )
}
