import { useNavigate, useLocation } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import type { CustomJwtPayload } from '../../Types'
import Button from '../ui/Button'
import './Header.css'

// Associe chaque path à un titre lisible
const PAGE_TITLES: Record<string, string> = {
  '/adventurers':        'Adventurers',
  '/create-adventurer':  'Créer un aventurier',
  '/update-adventurer':  'Modifier un aventurier',
  '/skills':             'Skills',
  '/params':             'Paramètres',
}

export default function Header() {
  const navigate  = useNavigate()
  const location  = useLocation()

  const token   = localStorage.getItem('token')
  const isAdmin = token
    ? jwtDecode<CustomJwtPayload>(token).scope.includes('ADMIN')
    : false

  const pageTitle = PAGE_TITLES[location.pathname] ?? 'Arena'

  return (
    <header className="app-header">
      <h1 className="app-header__title">{pageTitle}</h1>

      <div className="app-header__actions">
        {isAdmin && (
          <Button
            variant="gold"
            size="md"
            onClick={() => navigate('/create-adventurer')}
          >
            + Créer
          </Button>
        )}

        <Button
          variant="ruby"
          size="md"
          onClick={() => navigate('/adventurers')} 
        >
          Rechercher
        </Button>
      </div>
    </header>
  )
}
