import './styles/Variables.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from 'react-router-dom'
import { PrivateRoute } from '../AuthContext/PrivateRoutes'
import Adventurer from './Adventurer/Adventurer.tsx'
import CreateAdventurer from './Adventurer/CreateAdventurer.tsx'
import Login from './auth/Login.tsx'
import Signin from './auth/Signin.tsx'
import { AuthProvider } from '../AuthContext/AuthProvider.tsx'
import UpdateAdventurer from './Adventurer/UpdateAdventurer.tsx'
import ListSkill from './Skill/ListSkill.tsx'

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path='/' element={<App />} />
      <Route path='/aventurier' element={<Adventurer />} />
      <Route path='/skills' element={<ListSkill />} />
      <Route
        path="/create-adventurer"
        element={
          <PrivateRoute requiredRole="ADMIN">
            <CreateAdventurer />
          </PrivateRoute>
        }
      />
      <Route
        path="/update-adventurer"
        element={
          <PrivateRoute requiredRole="ADMIN">
            <UpdateAdventurer />
          </PrivateRoute>
        }
      />
      <Route path='/login' element={<Login />} />
      <Route path='/register' element={<Signin />} />
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