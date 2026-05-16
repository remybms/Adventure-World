import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar'
import Header from './Header'
import './AppLayout.css'
import { jwtDecode } from 'jwt-decode'
import type { CustomJwtPayload } from '../../Types'

function getUserInfo() {
  const token = localStorage.getItem('token')
  if (!token) return { username: 'Username', role: 'USER_ROLE' }
  try {
    const payload = jwtDecode<CustomJwtPayload>(token)
    return {
      username: payload.sub ?? 'Username',
      role:     payload.scope.split(" ")[0] ?? 'USER_ROLE',
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
