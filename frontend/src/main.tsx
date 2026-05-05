import './styles/Variables.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider, Navigate } from 'react-router-dom'
import { AuthProvider } from '../AuthContext/AuthProvider.tsx'
import { PrivateRoute } from '../AuthContext/PrivateRoutes'
import AppLayout from './components/layout/AppLayout.tsx'

// no sidebar
import Login from './auth/Login.tsx'
import Signin from './auth/Signin.tsx'

// protected pages with sidebar + header
import App from './App.tsx'
import Adventurer from './Adventurer/Adventurer.tsx'
import CreateAdventurer from './Adventurer/CreateAdventurer.tsx'
import UpdateAdventurer from './Adventurer/UpdateAdventurer.tsx'
import SkillDetail from './Skills/SkillDetail.tsx'
import CreateSkill from './Skills/CreateSkill.tsx'
import ListSkill from './Skill/ListSkill.tsx'

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      {/* default*/}
      <Route path='/' element={<Navigate to="/login" replace />} />

      <Route path='/login'    element={<Login />} />
      <Route path='/register' element={<Signin />} />
      <Route path='/adventurers/detail' element={<Adventurer />} />
      <Route path='/skills' element={<ListSkill />} />
      <Route path='/skills/detail' element={<SkillDetail />} />
      <Route path='create-skill' element={
        <PrivateRoute requiredRole='ADMIN'>
          <CreateSkill />
        </PrivateRoute>
      } />

      {/* sidebar + dynamic header */}
      <Route element={<PrivateRoute><AppLayout /></PrivateRoute>}>
        <Route path='/adventurers' element={<App />} />
        <Route
          path='/create-adventurer'
          element={
            <PrivateRoute requiredRole="ADMIN">
              <CreateAdventurer />
            </PrivateRoute>
          }
        />
        <Route
          path='/update-adventurer'
          element={
            <PrivateRoute requiredRole="ADMIN">
              <UpdateAdventurer />
            </PrivateRoute>
          }
        />
      </Route>
    </>
  )
)

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </StrictMode>,
)
