import { useNavigate, useLocation } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import type { CustomJwtPayload } from '../../Types'
import Button from '../ui/Button'
import './Header.css'
import Icon from '../ui/Icons'

// Associe chaque path à un titre lisible
const PAGE_TITLES: Record<string, string> = {
  '/adventurers':        'Adventurers',
  '/create-adventurer':  'Create Adventurer',
  '/update-adventurer':  'Update Adventurer',
  '/skills':             'Skills',
  '/params':             'Parameters',
}

// Détermine la route de création selon la page actuelle
const getCreateRoute = (pathname: string): string => {
  if (pathname.includes('/skills')) return '/create-skill'
  if (pathname.includes('/adventurers')) return '/create-adventurer'
  return '/create-adventurer'
}

export default function Header() {
  const navigate  = useNavigate()
  const location  = useLocation()

  const token   = localStorage.getItem('token')
  const isAdmin = token
    ? jwtDecode<CustomJwtPayload>(token).scope.includes('ADMIN')
    : false

  const pageTitle = PAGE_TITLES[location.pathname] ?? 'Arena'
  const createRoute = getCreateRoute(location.pathname)

  return (
    <header className="app-header">
      <h1 className="app-header__title">{pageTitle}</h1>

      <div className="app-header__actions">
        {isAdmin && (
          <Button
            variant="gold"
            size="md"
            onClick={() => navigate(createRoute)}
            title="Create new item"
          >
          <Icon name="addWhite" size={20} />
            Create
          </Button>
        )}

        <Button
          variant="ruby"
          size="md"
          onClick={() => navigate('/adventurers')} 
        >
          Search
        </Button>
      </div>
    </header>
  )
}
