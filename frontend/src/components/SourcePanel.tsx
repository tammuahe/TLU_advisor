import { ExternalLink } from "lucide-react"

interface Props {
  sources: string[]
}

export default function SourcePanel({ sources }: Props) {
  if (sources.length === 0) return null

  return (
    <div className="flex justify-start">
      <div className="max-w-[75%] w-full">
        <div className="mt-2 rounded-lg border-l-2 border-primary bg-card px-4 py-3 shadow-sm">
          <p className="text-xs font-medium text-muted-foreground mb-2">
            Nguồn tham khảo
          </p>
          <ul className="space-y-1">
            {sources.map((url, i) => (
              <li key={i}>
                <a
                  href={url}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center gap-1.5 text-xs text-primary hover:underline transition-colors break-all"
                >
                  <ExternalLink className="h-3 w-3 shrink-0" />
                  {url}
                </a>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  )
}
