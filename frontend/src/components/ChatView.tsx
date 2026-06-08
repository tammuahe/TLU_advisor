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

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      sendMessage(e)
    }
  }

  return (
    <div className="flex h-screen flex-col bg-slate-950">
      {/* Top accent bar */}
      <div className="h-0.5 shrink-0 bg-gradient-to-r from-amber-500 via-amber-400 to-amber-600" />

      {/* Header */}
      <header className="flex shrink-0 items-center justify-between border-b border-slate-800 px-6 py-3">
        <div className="flex items-center gap-2">
          <h1 className="text-lg font-bold tracking-tight text-white">TLU Advisor</h1>
          <span className="rounded-full bg-amber-500/10 px-2 py-0.5 text-[10px] font-medium text-amber-400">
            BETA
          </span>
        </div>
        <div className="flex items-center gap-3">
          <span className="text-xs text-slate-500">{user?.email}</span>
          <button
            onClick={handleLogout}
            className="rounded-lg px-3 py-1.5 text-xs font-medium text-slate-500 transition hover:bg-slate-800 hover:text-slate-300"
          >
            Sign out
          </button>
        </div>
      </header>

      {/* Messages */}
      <main className="flex-1 overflow-y-auto px-6 py-6">
        <div className="mx-auto w-full max-w-3xl space-y-4">
          {messages.length === 0 && (
            <div className="flex flex-col items-center justify-center py-16">
              <div className="mb-4 rounded-full bg-amber-500/10 p-3">
                <svg
                  className="h-6 w-6 text-amber-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  strokeWidth={1.5}
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M9.813 15.904L9 18.75l-.813-2.846a4.5 4.5 0 00-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 003.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 003.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 00-3.09 3.09zM18.259 8.715L18 9.75l-.259-1.035a3.375 3.375 0 00-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 002.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 002.455 2.456L21.75 6l-1.036.259a3.375 3.375 0 00-2.455 2.456z"
                  />
                </svg>
              </div>
              <p className="text-sm text-slate-500">
                Ask about courses, programs, fees, or professors
              </p>
            </div>
          )}

          {messages.map((msg, i) => (
            <div
              key={i}
              className={`animate-[messageIn_0.3s_ease-out] flex ${
                msg.role === 'USER' ? 'justify-end' : 'justify-start'
              }`}
            >
              <div
                className={`max-w-[80%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
                  msg.role === 'USER'
                    ? 'bg-amber-500/10 border border-amber-500/20 text-slate-200'
                    : 'bg-slate-900 border border-slate-800 text-slate-300'
                }`}
              >
                {msg.content}
              </div>
            </div>
          ))}

          {sending && (
            <div className="animate-[fadeIn_0.2s_ease-out] flex justify-start">
              <div className="flex items-center gap-1.5 rounded-2xl border border-slate-800 bg-slate-900 px-4 py-3">
                <span className="h-2 w-2 animate-bounce rounded-full bg-amber-500 [animation-delay:0ms]" />
                <span className="h-2 w-2 animate-bounce rounded-full bg-amber-500 [animation-delay:120ms]" />
                <span className="h-2 w-2 animate-bounce rounded-full bg-amber-500 [animation-delay:240ms]" />
              </div>
            </div>
          )}

          <div ref={bottomRef} />
        </div>
      </main>

      {/* Input */}
      <footer className="shrink-0 border-t border-slate-800 bg-slate-900/50 px-4 py-4 backdrop-blur">
        <form onSubmit={sendMessage} className="mx-auto flex max-w-3xl gap-2">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Ask about courses, programs, fees..."
            aria-label="Chat message"
            disabled={sending}
            className="flex-1 rounded-xl border border-slate-800 bg-slate-950 px-4 py-3 text-sm text-white outline-none transition placeholder:text-slate-600 focus:border-amber-500/40 disabled:opacity-50"
          />
          <button
            type="submit"
            disabled={sending || !input.trim()}
            className="rounded-xl bg-amber-500 px-5 py-3 text-sm font-semibold text-slate-950 transition hover:bg-amber-400 disabled:opacity-30"
          >
            Send
          </button>
        </form>
      </footer>
    </div>
  )
}
