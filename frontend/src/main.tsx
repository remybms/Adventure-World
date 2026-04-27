import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from 'react-router-dom'
import { PrivateRoute } from '../AuthContext/PrivateRoutes'
import Adventurer from './Adventurer/Adventurer.tsx'
import CreateAdventurer from './Adventurer/CreateAdventurer.tsx'
import Login from './User/Login.tsx'

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path='/' element={<App />} />
      <Route path='/aventurier' element={<Adventurer />} />
      <Route
        path="/create-adventurer"
        element={
          <PrivateRoute requiredRole="ADMIN">
            <CreateAdventurer />
          </PrivateRoute>
        }
      />
      <Route path='/login' element={<Login />}/>

    </>

  )
)

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)