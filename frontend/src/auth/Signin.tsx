import logoDark from '../assets/aw-logo_dark.svg'
import { useState, useEffect } from 'react'
import apiClient from '../../AuthContext/apiClient'
import { useAuth } from '../../AuthContext/AuthContext'
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

    if (password !== confirmPassword) {
      setError("The passwords do not match.")
      return
    }

    setLoading(true)
    try {
      await apiClient.post("/auth/register", { username, password, role })
      // After registration, automatically log in
      const loginResponse = await apiClient.post("/auth/login", { username, password })
      if (loginResponse.status !== 200) throw new Error(`Login status : ${loginResponse.status}`)
      login(loginResponse.data.token)
      navigate('/adventurers', { replace: true })
    } catch {
      setError("An error occurred. Please try again.")
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

        <h1 className="login-title">Create an account</h1>
        <p  className="login-subtitle">Join the arena</p>

        <form onSubmit={sendData} className="login-form" noValidate>
          <Input
            label="Username"
            type="text"
            placeholder="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            onDark
            required
          />

          <Input
            label="Password"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onDark
            required
          />

          <Input
            label="Confirm Password"
            type="password"
            placeholder="••••••••"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            onDark
            required
          />

          <div className="input-wrapper input-wrapper--dark">
            <label htmlFor="role" className="input-label">Role</label>
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
            {loading ? 'Creating...' : 'Create Account'}
          </Button>

          <div className="btm-link">
            <p>You already have an account ?</p>
            <a className="login-register-link" onClick={() => navigate('/login')}>
                Log In here
            </a>
          </div>

        </form>
      </div>
    </div>
  )
}
