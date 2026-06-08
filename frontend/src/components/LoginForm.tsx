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
    <div className="flex min-h-screen flex-col items-center justify-center bg-slate-950 px-4">
      {/* Decorative top accent bar */}
      <div className="fixed inset-x-0 top-0 h-1 bg-gradient-to-r from-amber-500 via-amber-400 to-amber-600" />

      <div className="w-full animate-[slideUp_0.6s_ease-out]" style={{ maxWidth: 400 }}>
        <h1 className="text-center text-3xl font-bold tracking-tight text-white">
          TLU Advisor
        </h1>
        <p className="mt-2 text-center text-sm text-slate-400">
          University Knowledge Assistant
        </p>

        <div className="mt-8 rounded-xl border border-slate-800 bg-slate-900 p-8">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-xs font-medium uppercase tracking-wider text-slate-500">
                Email
              </label>
              <input
                type="email"
                aria-label="Email"
                placeholder="you@tlu.edu.vn"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                disabled={loading}
                className={`mt-1.5 w-full rounded-lg border bg-slate-950 px-4 py-2.5 text-sm text-white outline-none transition placeholder:text-slate-600 focus:border-amber-500/50 focus:ring-2 focus:ring-amber-500/10 disabled:opacity-50 ${
                  error ? 'border-red-500 animate-[shake_0.3s_ease-in-out]' : 'border-slate-800'
                }`}
              />
            </div>

            <div>
              <label className="block text-xs font-medium uppercase tracking-wider text-slate-500">
                Password
              </label>
              <input
                type="password"
                aria-label="Password"
                placeholder="······"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={loading}
                className={`mt-1.5 w-full rounded-lg border bg-slate-950 px-4 py-2.5 text-sm text-white outline-none transition placeholder:text-slate-600 focus:border-amber-500/50 focus:ring-2 focus:ring-amber-500/10 disabled:opacity-50 ${
                  error ? 'border-red-500 animate-[shake_0.3s_ease-in-out]' : 'border-slate-800'
                }`}
              />
            </div>

            {error && (
              <p role="alert" className="animate-[fadeIn_0.2s_ease-out] text-sm text-red-400">
                {error}
              </p>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full rounded-lg bg-amber-500 py-3 text-sm font-semibold text-slate-950 transition hover:bg-amber-400 disabled:opacity-50"
            >
              {loading ? (
                <span className="flex items-center justify-center gap-2">
                  <span className="h-4 w-4 animate-spin rounded-full border-2 border-slate-800 border-t-transparent" />
                  Signing in...
                </span>
              ) : (
                'Sign in'
              )}
            </button>
          </form>
        </div>

        <p className="mt-6 text-center text-xs text-slate-600">
          Secured by TLU Identity Service
        </p>
      </div>
    </div>
  )
}
