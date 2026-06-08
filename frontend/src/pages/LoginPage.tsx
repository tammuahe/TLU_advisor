import { useState, type FormEvent } from "react"
import { useAuth } from "@/context/useAuth"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function LoginPage() {
  const { login, register } = useAuth()
  const [mode, setMode] = useState<"signin" | "register">("signin")
  const [error, setError] = useState("")
  const [loading, setLoading] = useState(false)

  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [firstName, setFirstName] = useState("")
  const [lastName, setLastName] = useState("")
  const [studentCode, setStudentCode] = useState("")
  const [programId, setProgramId] = useState("")

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setError("")
    setLoading(true)

    try {
      if (mode === "signin") {
        await login(email, password)
      } else {
        await register({
          email,
          password,
          firstName,
          lastName,
          studentCode,
          programId: parseInt(programId, 10),
        })
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : "An error occurred")
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-muted p-4">
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="font-['Noto_Serif'] text-2xl">
            TLU Advisor
          </CardTitle>
          <CardDescription>AI academic guidance</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            {mode === "register" && (
              <>
                <div className="grid grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="firstName">First name</Label>
                    <Input
                      id="firstName"
                      value={firstName}
                      onChange={(e) => setFirstName(e.target.value)}
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="lastName">Last name</Label>
                    <Input
                      id="lastName"
                      value={lastName}
                      onChange={(e) => setLastName(e.target.value)}
                      required
                    />
                  </div>
                </div>
                <div className="space-y-2">
                  <Label htmlFor="studentCode">Student code</Label>
                  <Input
                    id="studentCode"
                    value={studentCode}
                    onChange={(e) => setStudentCode(e.target.value)}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="programId">Program ID</Label>
                  <Input
                    id="programId"
                    type="number"
                    value={programId}
                    onChange={(e) => setProgramId(e.target.value)}
                    required
                  />
                </div>
              </>
            )}
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            {error && <p className="text-sm text-destructive">{error}</p>}
            <Button type="submit" className="w-full" disabled={loading}>
              {loading
                ? "Please wait..."
                : mode === "signin"
                  ? "Sign In"
                  : "Register"}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="justify-center">
          <button
            type="button"
            className="text-sm text-muted-foreground hover:text-foreground"
            onClick={() => {
              setMode(mode === "signin" ? "register" : "signin")
              setError("")
            }}
          >
            {mode === "signin"
              ? "No account? Register"
              : "Already have an account? Sign in"}
          </button>
        </CardFooter>
      </Card>
    </div>
  )
}
