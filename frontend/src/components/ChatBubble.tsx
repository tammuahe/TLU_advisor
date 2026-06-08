import Markdown from "react-markdown"
import type { ChatMessage } from "@/types/api"

interface Props {
  message: ChatMessage
}

export default function ChatBubble({ message }: Props) {
  const isUser = message.role === "user"

  return (
    <div className={`flex ${isUser ? "justify-end" : "justify-start"}`}>
      <div
        className={`max-w-[75%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
          isUser
            ? "bg-primary text-primary-foreground rounded-br-md"
            : "bg-muted text-foreground rounded-bl-md prose prose-sm dark:prose-invert"
        }`}
      >
        {isUser ? (
          message.content
        ) : (
          <Markdown>{message.content}</Markdown>
        )}
      </div>
    </div>
  )
}
