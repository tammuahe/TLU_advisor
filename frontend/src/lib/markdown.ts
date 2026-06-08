const SOURCE_REGEX = /<source>([^<]*)<\/source>/gi

export interface ParsedMessage {
  content: string
  sources: string[]
}

export function parseMessageSources(raw: string): ParsedMessage {
  const sources: string[] = []
  const content = raw.replace(SOURCE_REGEX, (_match, url) => {
    sources.push(url.trim())
    return ""
  })

  return { content: content.trim(), sources }
}
