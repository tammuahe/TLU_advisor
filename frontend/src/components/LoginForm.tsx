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
    <div className="relative flex min-h-screen items-center justify-center bg-bg">
      {/* Subtle grid overlay */}
      <div
        className="pointer-events-none fixed inset-0 opacity-[0.03]"
        style={{
          backgroundImage:
            'linear-gradient(rgba(255,255,255,0.05) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,0.05) 1px, transparent 1px)',
          backgroundSize: '64px 64px',
        }}
      />

      {/* Animated ambient glow */}
      <div className="pointer-events-none fixed left-1/2 top-1/3 h-96 w-96 -translate-x-1/2 -translate-y-1/2 rounded-full bg-accent opacity-[0.03] blur-[120px]" />

      <div className="animate-[slideUp_0.6s_ease-out] z-10 w-full max-w-sm">
        {/* Title */}
        <h1
          className="font-heading text-center text-4xl font-bold tracking-wider"
          style={{
            textShadow: '0 0 40px var(--color-accent-dim)',
          }}
        >
          TLU
          <span className="text-accent"> ADVISOR</span>
        </h1>
        <p className="mt-2 text-center font-body text-sm text-muted">University Knowledge Assistant</p>

        {/* Card */}
        <div className="mt-10 rounded-2xl border border-border bg-surface/50 p-8 backdrop-blur-xl">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="font-body text-xs font-medium uppercase tracking-widest text-muted">
                Email
              </label>
              <input
                type="email"
                aria-label="Email"
                placeholder="you@tlu.edu.vn"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                disabled={loading}
                className={`mt-1.5 w-full border-b-2 bg-transparent py-2.5 font-body text-sm text-text outline-none transition-all placeholder:text-muted/40 focus:border-accent disabled:opacity-50 ${
                  error ? 'border-error' : 'border-border'
                } ${error ? 'animate-[shake_0.35s_ease-in-out]' : ''}`}
              />
            </div>

            <div>
              <label className="font-body text-xs font-medium uppercase tracking-widest text-muted">
                Password
              </label>
              <input
                type="password"
                aria-label="Password"
                placeholder="······"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={loading}
                className={`mt-1.5 w-full border-b-2 bg-transparent py-2.5 font-body text-sm text-text outline-none transition-all placeholder:text-muted/40 focus:border-accent disabled:opacity-50 ${
                  error ? 'border-error' : 'border-border'
                } ${error ? 'animate-[shake_0.35s_ease-in-out]' : ''}`}
              />
            </div>

            {error && (
              <p role="alert" className="animate-[fadeIn_0.2s_ease-out] font-body text-sm text-error">
                {error}
              </p>
            )}

            <button
              type="submit"
              disabled={loading}
              className="mt-2 w-full rounded-xl border border-accent/30 bg-accent/10 py-3 font-heading text-sm font-semibold uppercase tracking-widest text-accent transition-all hover:border-accent/60 hover:bg-accent/15 hover:shadow-[0_0_30px_var(--color-accent-dim)] disabled:opacity-40"
            >
              {loading ? (
                <span className="flex items-center justify-center gap-2">
                  <span className="h-4 w-4 animate-spin rounded-full border-2 border-accent/50 border-t-accent" />
                  Authenticating
                </span>
              ) : (
                'Enter'
              )}
            </button>
          </form>
        </div>

        <p className="mt-6 text-center font-body text-xs text-muted/50">
          Secured by TLU Identity Service
        </p>
      </div>
    </div>
  )
}
