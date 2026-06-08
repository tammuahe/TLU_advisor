import { useState, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

interface Message {
  role: string
  content: string
}

export default function ChatView() {
  const navigate = useNavigate()
  const { user, token, logout } = useAuth()
  const [messages, setMessages] = useState<Message[]>([])
  const [input, setInput] = useState('')
  const [sending, setSending] = useState(false)
  const bottomRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const sendMessage = async (e: React.FormEvent) => {
    e.preventDefault()
    const text = input.trim()
    if (!text || sending) return

    const userMsg: Message = { role: 'USER', content: text }
    setMessages((prev) => [...prev, userMsg])
    setInput('')
    setSending(true)

    try {
      const res = await fetch(
        `http://localhost:8080/chat?prompt=${encodeURIComponent(text)}`,
        { headers: { Authorization: `Bearer ${token}` } },
      )
      if (!res.ok) throw new Error('Failed to get response')
      const data = await res.json()
      setMessages((prev) => [...prev, data])
    } catch {
      setMessages((prev) => [
        ...prev,
        { role: 'ASSISTANT', content: 'Sorry, something went wrong. Please try again.' },
      ])
    } finally {
      setSending(false)
    }
  }

  return (
    <div className="flex h-screen flex-col bg-bg font-body">
      {/* Header */}
      <header className="flex shrink-0 items-center justify-between border-b border-border bg-surface/60 px-6 py-3.5 backdrop-blur-lg">
        <h1 className="font-heading text-lg font-semibold tracking-wide">
          TLU <span className="text-accent">Advisor</span>
        </h1>
        <div className="flex items-center gap-4">
          <span className="text-sm text-muted">{user?.email}</span>
          <button
            onClick={handleLogout}
            className="rounded-lg border border-border bg-transparent px-3 py-1.5 text-xs font-medium text-muted transition-all hover:border-error/40 hover:text-error"
          >
            Sign out
          </button>
        </div>
      </header>

      {/* Messages */}
      <main className="flex-1 overflow-y-auto px-4 py-6">
        <div className="mx-auto max-w-3xl space-y-4">
          {messages.length === 0 && (
            <div className="flex flex-col items-center justify-center py-20">
              <div className="mb-4 rounded-full bg-accent/10 p-4">
                <svg className="h-8 w-8 text-accent" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={1.5}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M9.813 15.904L9 18.75l-.813-2.846a4.5 4.5 0 00-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 003.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 003.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 00-3.09 3.09zM18.259 8.715L18 9.75l-.259-1.035a3.375 3.375 0 00-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 002.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 002.455 2.456L21.75 6l-1.036.259a3.375 3.375 0 00-2.455 2.456z" />
                </svg>
              </div>
              <p className="text-center text-muted">
                Ask anything about courses, programs, fees, or professors.
              </p>
            </div>
          )}

          {messages.map((msg, i) => (
            <div
              key={i}
              className={`animate-[messageIn_0.3s_ease-out] flex ${msg.role === 'USER' ? 'justify-end' : 'justify-start'}`}
            >
              <div
                className={`max-w-[75%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
                  msg.role === 'USER'
                    ? 'bg-accent/10 border border-accent/20 text-text'
                    : 'bg-surface/80 border border-border text-text'
                }`}
              >
                {msg.content}
              </div>
            </div>
          ))}

          {sending && (
            <div className="animate-[fadeIn_0.2s_ease-out] flex justify-start">
              <div className="flex items-center gap-2 rounded-2xl border border-border bg-surface/80 px-4 py-3">
                <span className="flex gap-1">
                  <span className="h-1.5 w-1.5 animate-pulse rounded-full bg-accent/60 [animation-delay:0ms]" />
                  <span className="h-1.5 w-1.5 animate-pulse rounded-full bg-accent/60 [animation-delay:150ms]" />
                  <span className="h-1.5 w-1.5 animate-pulse rounded-full bg-accent/60 [animation-delay:300ms]" />
                </span>
              </div>
            </div>
          )}

          <div ref={bottomRef} />
        </div>
      </main>

      {/* Input */}
      <footer className="shrink-0 border-t border-border bg-surface/60 px-4 py-4 backdrop-blur-lg">
        <form onSubmit={sendMessage} className="mx-auto flex max-w-3xl gap-2">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Ask about courses, programs, fees..."
            aria-label="Chat message"
            disabled={sending}
            className="flex-1 rounded-xl border border-border bg-input-bg px-4 py-3 text-sm text-text outline-none transition-all placeholder:text-muted/40 focus:border-accent/40 focus:shadow-[0_0_12px_var(--color-accent-dim)] disabled:opacity-50"
          />
          <button
            type="submit"
            disabled={sending || !input.trim()}
            className="rounded-xl border border-accent/30 bg-accent/10 px-5 py-3 font-heading text-sm font-semibold uppercase tracking-wider text-accent transition-all hover:border-accent/60 hover:bg-accent/15 hover:shadow-[0_0_20px_var(--color-accent-dim)] disabled:opacity-30"
          >
            Send
          </button>
        </form>
      </footer>
    </div>
  )
}
