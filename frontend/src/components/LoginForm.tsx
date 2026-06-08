import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function LoginForm() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    if (!email || !password) {
      setError('Please fill in all fields')
      return
    }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      setError('Please enter a valid email address')
      return
    }
    setLoading(true)
    try {
      await login(email, password)
      navigate('/chat')
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unable to connect to server')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-slate-700 to-slate-900">
      <div className="w-full max-w-sm animate-[slideUp_0.5s_ease-out] rounded-xl bg-white px-8 py-10 shadow-2xl">
        <h1 className="mb-8 text-center text-2xl font-bold text-gray-800">
          TLU Advisor
        </h1>
        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <input
              type="email"
              placeholder="Email"
              aria-label="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={loading}
              className={`w-full rounded-lg border px-4 py-2.5 text-sm outline-none transition-colors focus:border-blue-500 disabled:opacity-50 ${
                error ? 'border-red-500 animate-[shake_0.3s_ease-in-out]' : 'border-gray-300'
              }`}
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Password"
              aria-label="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              disabled={loading}
              className={`w-full rounded-lg border px-4 py-2.5 text-sm outline-none transition-colors focus:border-blue-500 disabled:opacity-50 ${
                error ? 'border-red-500 animate-[shake_0.3s_ease-in-out]' : 'border-gray-300'
              }`}
            />
          </div>
          {error && (
            <p role="alert" className="animate-[fadeIn_0.2s_ease-out] text-sm text-red-500">{error}</p>
          )}
          <button
            type="submit"
            disabled={loading}
            className="flex w-full items-center justify-center rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white transition-colors hover:bg-blue-700 disabled:opacity-50"
          >
            {loading ? (
              <div className="h-5 w-5 animate-spin rounded-full border-2 border-white border-t-transparent" />
            ) : (
              'Sign in'
            )}
          </button>
        </form>
      </div>
    </div>
  )
}
