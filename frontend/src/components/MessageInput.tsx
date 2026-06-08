import { useState, type FormEvent, type KeyboardEvent } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Send } from "lucide-react"

interface Props {
  onSend: (message: string) => void
  disabled?: boolean
}

export default function MessageInput({ onSend, disabled }: Props) {
  const [value, setValue] = useState("")

  function handleSubmit(e: FormEvent) {
    e.preventDefault()
    if (!value.trim() || disabled) return
    onSend(value.trim())
    setValue("")
  }

  function handleKeyDown(e: KeyboardEvent<HTMLInputElement>) {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault()
      handleSubmit(e)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex gap-2">
      <Input
        value={value}
        onChange={(e) => setValue(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="Type your message..."
        disabled={disabled}
        className="flex-1"
      />
      <Button type="submit" disabled={disabled || !value.trim()}>
        <Send className="h-4 w-4" />
      </Button>
    </form>
  )
}
