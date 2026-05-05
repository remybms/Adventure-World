import logoDark from '../assets/aw-logo_dark.svg'
import { useState, useEffect } from 'react'
import apiClient from "../../AuthContext/apiClient"
import { useAuth } from '../../AuthContext/AuthContext'
import { useNavigate } from 'react-router-dom'
import Button from '../components/ui/Button'
import Input  from '../components/ui/Input'
import './Login.css'

export default function Login() {
  const [username,    setUsername]    = useState<string>('')
  const [password, setPassword] = useState<string>('')
  const [error,    setError]    = useState<string>('')
  const [loading,  setLoading]  = useState<boolean>(false)

  const { token, login } = useAuth()
  const navigate = useNavigate()

  useEffect(() => {
    if (token) {
      navigate('/adventurers', { replace: true })
    }
  }, [token, navigate])

  const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const response = await apiClient.post("/auth/login", { username, password })
      if (response.status !== 200) throw new Error(`Response status : ${response.status}`)
      login(response.data.token)
      navigate('/adventurers', { replace: true })
    } catch {
      setError("Identifiants invalides. Veuillez réessayer.")
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

        <h1 className="login-title">Log In</h1>
        <p  className="login-subtitle">Access the arena</p>

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

          {error && <p className="login-error" role="alert">{error}</p>}

          <Button
            type="submit"
            variant="gold"
            size="lg"
            fullWidth
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Log In'}
          </Button>
        </form>
      </div>
    </div>
  )
}
