# TLU_advisor — AGENTS.md

## Project structure

Three independent modules (no parent POM). Each has its own build config, `.env`, and wrapper.

| Module | Port | Role |
|---|---|---|
| `mcp_document/` | 8081 | MCP server — RAG document retrieval (PGVector + ONNX embeddings + hyDE) |
| `chat_host/` | 8080 | MCP client — chat backend with JWT auth, university DB, LLM tools |
| `frontend/` | 5173 | React + Vite + TypeScript frontend (dev server) |

## Startup order

1. `docker-compose up -d` (PostgreSQL 18, port 5432)
2. `mcp_document` first — chat_host connects to it via MCP streamable HTTP at startup
3. `chat_host` second
4. `frontend` last (run `npm run dev` in `frontend/`)

## Build & run

```bash
# Backend modules (each independently)
./mvnw spring-boot:run -f mcp_document/pom.xml
./mvnw spring-boot:run -f chat_host/pom.xml

# Frontend
cd frontend && npm run dev
```

## Key config quirks

- **Java 25 with preview features**: mcp_document enables `--enable-preview` compiler arg; chat_host does not
- **DB connection**: mcp_document reads `POSTGRES_DOC_DB/USER/PASSWORD` from its `.env` → `localhost:5432`; chat_host reads `POSTGRES_CHAT_DB/USER/PASSWORD` → `localhost:5433` (different ports, different databases). **Note**: docker-compose.yml only exposes port 5432; chat_host expects a second PostgreSQL instance on 5433 (not configured in docker-compose).
- **LLM provider**: Both use Groq's OpenAI-compatible API (`base-url: https://api.groq.com/openai`) via `GROQ_API_KEY`
  - mcp_document model: `llama-3.1-8b-instant`
  - chat_host model: `openai/gpt-oss-120b`
- **ONNX GPU**: mcp_document excludes CPU `onnxruntime` and explicitly adds `onnxruntime_gpu:1.20.0`; requires GPU device 0
- **PostgreSQL driver**: NOT declared in mcp_document's pom.xml (pulled transitively by PGVector starter - verify it resolves)
- **DDL mode**: chat_host uses `ddl-auto: create-drop` + `data.sql` for seed data; DB is wiped on restart
- **Seed users** (chat_host `DataInit.java`): `admin/admin` (ADMIN), `tammuahe2004@gmail.com/test` (USER), `nigga@gmail.com/test` (USER)
- **JWT**: 24h expiry, HMAC-SHA, secret from `JWT_SECRET` env var

## Code conventions

- **Commits**: Conventional Commits (`feat:`, `chore:`, `refactor:`) with scopes `mcp_document`, `chat_host`, or `frontend`
- **No CI, no linting, no formatter config** — only default Spring Boot context-load tests exist
- **No test framework beyond JUnit 5** (Spring Boot starter test)
- **Logging**: Both modules log `edu.tlu` and `org.springframework.ai` at DEBUG
- **Frontend**: ESLint + TypeScript + React 19, run `npm run lint` to check

## MCP wiring

- chat_host declares an MCP client connection named `mcp_document` → `http://localhost:8081` (streamable HTTP, 30s timeout)
- mcp_document exposes `getDocument` as an `@McpTool` in `DocumentService.java`
- chat_host's `ToolService` has 17 `@Tool` methods + the MCP-proxied `getDocument`

## Untracked / generated

- `.env` files are gitignored (each module has its own)
- ONNX model files in `mcp_document/src/main/resources/onnx/` are tracked in git
- `target/` is gitignored per module
- `application.yml` files are gitignored (but exist in source dirs for reference)
- `frontend/node_modules/`, `frontend/dist/` are gitignored

## Frontend notes

- React 19, Vite 8, TypeScript ~6.0
- Entry: `frontend/src/main.tsx` → `App.tsx`
- Run lint: `npm run lint` (ESLint + TypeScript ESLint)
- Build: `npm run build` (tsc + vite build)