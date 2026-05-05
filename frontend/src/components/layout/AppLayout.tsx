import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar'
import Header from './Header'
import './AppLayout.css'

function getUserInfo() {
  const token = localStorage.getItem('token')
  if (!token) return { username: 'Username', role: 'USER_ROLE' }
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return {
      username: payload.username ?? payload.sub ?? 'Username',
      role:     payload.role ?? 'USER_ROLE',
    }
  } catch {
    return { username: 'Username', role: 'USER_ROLE' }
  }
}

export default function AppLayout() {
  const { username, role } = getUserInfo()

  return (
    <div className="app-layout">
      <Sidebar username={username} role={role} />

      <div className="app-layout__right">
        <Header />
        <main className="app-main">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
