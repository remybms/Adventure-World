import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../../../AuthContext/AuthContext'
import Button from '../ui/Button'
import Icon from '../ui/Icons'
import logoLight from '../../assets/aw-logo_light.svg'
import './Sidebar.css'

interface SidebarProps {
  username: string
  role:     string
}

export default function Sidebar({ username }: SidebarProps) {
  const navigate = useNavigate()
  const { logout } = useAuth()

  const handleSignOut = () => {
    logout()
    navigate('/login')
  }

  return (
    <aside className="sidebar">

      <div className="sidebar-logo">
        <img src={logoLight} alt="App logo" />
      </div>

      <nav className="sidebar-nav">
        <p className="sidebar-section-label">Content</p>

        <NavLink to="/adventurers" className="sidebar-link">
          {({ isActive }) => (
            <>
              <span className="sidebar-icon">
                <Icon name={isActive ? 'swordFilled' : 'swordEmpty'} />
              </span>
              Adventurers
            </>
          )}
        </NavLink>

        <NavLink to="/skills" className="sidebar-link">
          {({ isActive }) => (
            <>
              <span className="sidebar-icon">
                <Icon name={isActive ? 'skillFilled' : 'skillEmpty'} />
              </span>
              Skills
            </>
          )}
        </NavLink>

        <p className="sidebar-section-label sidebar-section-label--spaced">Admin view</p>

        <NavLink to="/params" className="sidebar-link">
          {({ isActive }) => (
            <>
              <span className="sidebar-icon">
                <Icon name={isActive ? 'parametersFilled' : 'parametersEmpty'} />
              </span>
              Parameters
            </>
          )}
        </NavLink>
      </nav>

      <div className="sidebar-footer">
        <div className="sidebar-user">
          <div className="sidebar-avatar" aria-hidden="true">
            <Icon name="personFilled" size={44} />
          </div>
          <div className="sidebar-user-info">
            <span className="sidebar-username">{username}</span>
            {/* <span className="sidebar-role">{role}</span> */}
            <span className="sidebar-role">user_role</span>
          </div>
        </div>

        <Button variant="ruby" size="sm" fullWidth onClick={handleSignOut}>
          Sign Out
        </Button>
      </div>

    </aside>
  )
}
