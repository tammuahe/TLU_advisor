import { useState, useRef, useEffect } from "react"
import { useAuth } from "@/context/useAuth"
import { apiClient } from "@/lib/api"
import type { ChatMessage } from "@/types/api"
import { Button } from "@/components/ui/button"
import ChatBubble from "@/components/ChatBubble"
import MessageInput from "@/components/MessageInput"
import { LogOut } from "lucide-react"

export default function ChatPage() {
  const { logout } = useAuth()
  const [messages, setMessages] = useState<ChatMessage[]>([])
  const [sending, setSending] = useState(false)
  const [error, setError] = useState("")
  const scrollRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    scrollRef.current?.scrollTo({
      top: scrollRef.current.scrollHeight,
      behavior: "smooth",
    })
  }, [messages])

  async function handleSend(prompt: string) {
    const userMessage: ChatMessage = { role: "user", content: prompt }
    setMessages((prev) => [...prev, userMessage])
    setSending(true)
    setError("")

    try {
      const response = await apiClient<ChatMessage>(
        `/chat?prompt=${encodeURIComponent(prompt)}`
      )
      setMessages((prev) => [...prev, response])
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to send message"
      )
    } finally {
      setSending(false)
    }
  }

  return (
    <div className="h-screen flex flex-col bg-background">
      <header className="shrink-0 border-b px-6 py-4 flex items-center justify-between">
        <h1 className="font-['Noto_Serif'] text-xl font-semibold">
          TLU Advisor
        </h1>
        <Button variant="ghost" size="icon" onClick={logout} title="Logout">
          <LogOut className="h-5 w-5" />
        </Button>
      </header>

      <div ref={scrollRef} className="flex-1 overflow-y-auto p-4 space-y-4">
        {messages.length === 0 && (
          <p className="text-center text-muted-foreground mt-20">
            Ask a question to get started
          </p>
        )}
        {messages.map((msg, i) => (
          <ChatBubble key={i} message={msg} />
        ))}
        {sending && (
          <div className="flex justify-start">
            <div className="max-w-[75%] rounded-2xl rounded-bl-md px-4 py-3 text-sm bg-muted">
              <span className="inline-flex gap-1">
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce" />
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce [animation-delay:0.15s]" />
                <span className="w-2 h-2 rounded-full bg-muted-foreground/40 animate-bounce [animation-delay:0.3s]" />
              </span>
            </div>
          </div>
        )}
        {error && (
          <div className="rounded-lg bg-destructive/10 border border-destructive/20 p-3 text-sm text-destructive">
            {error}
          </div>
        )}
      </div>

      <div className="shrink-0 border-t p-4">
        <MessageInput onSend={handleSend} disabled={sending} />
      </div>
    </div>
  )
}
