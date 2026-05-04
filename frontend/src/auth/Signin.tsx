import logoDark from '../assets/aw-logo_dark.svg'
import { useState } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate } from "react-router-dom"
import Button from '../components/ui/Button'
import Input  from '../components/ui/Input'
import './Login.css'

type Role = 'VIEWER' | 'EDITOR' | 'ADMIN'

export default function SignIn() {
  const [username,        setUsername]        = useState<string>('')
  const [password,        setPassword]        = useState<string>('')
  const [confirmPassword, setConfirmPassword] = useState<string>('')
  const [role,            setRole]            = useState<Role>('VIEWER')
  const [error,           setError]           = useState<string>('')
  const [loading,         setLoading]         = useState<boolean>(false)

  const navigate = useNavigate()

  const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setError('')

    if (password !== confirmPassword) {
      setError("Les mots de passe ne correspondent pas.")
      return
    }

    setLoading(true)
    try {
      await apiClient.post("/auth/register", { username, password, role })
      // After registration, automatically log in
      const loginResponse = await apiClient.post("/auth/login", { username, password })
      if (loginResponse.status !== 200) throw new Error(`Login status : ${loginResponse.status}`)
      localStorage.setItem("token", loginResponse.data.token)
      navigate('/')
    } catch {
      setError("Une erreur est survenue. Veuillez réessayer.")
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-page">

      <div className="login-logo">
        <img src={logoDark} alt="App Logo" />
      </div>

      <div className="login-card">

        <div className="login-avatar" aria-hidden="true" />

        <h1 className="login-title">Créer un compte</h1>
        <p  className="login-subtitle">Rejoins l'arène</p>

        <form onSubmit={sendData} className="login-form" noValidate>
          <Input
            label="Username"
            type="text"
            placeholder="bobdu92"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            onDark
            required
          />

          <Input
            label="Mot de passe"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onDark
            required
          />

          <Input
            label="Confirmer le mot de passe"
            type="password"
            placeholder="••••••••"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            onDark
            required
          />

          <div className="input-wrapper input-wrapper--dark">
            <label htmlFor="role" className="input-label">Rôle</label>
            <select
              id="role"
              className="input-field select-field"
              value={role}
              onChange={(e) => setRole(e.target.value as Role)}
            >
              <option value="VIEWER">VIEWER</option>
              <option value="EDITOR">EDITOR</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>

          {error && <p className="login-error" role="alert">{error}</p>}

          <Button
            type="submit"
            variant="gold"
            size="lg"
            fullWidth
            disabled={loading}
          >
            {loading ? 'Création...' : 'Créer un compte'}
          </Button>
        </form>
      </div>
    </div>
  )
}
