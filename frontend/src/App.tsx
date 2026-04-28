import { useState, useEffect } from 'react'
import './App.css'
import apiClient from '../AuthContext/apiClient'
import type AdventurerType from './AdventurerType'
import { jwtDecode, type JwtPayload } from 'jwt-decode'

interface CustomJwtPayload extends JwtPayload {
  scope: string
}

function App() {

  const [adventurers, setAdventurers] = useState<AdventurerType[]>()

  const token = localStorage.getItem("token")
  const isAdmin = token ? jwtDecode<CustomJwtPayload>(token).scope.includes("ADMIN") : false;

  const deleteAdventurer = async (idAdventurer: string) => {
    try {
      const response = await apiClient.delete(`/api/v1/aventuriers/${idAdventurer}`)
      if (response.status != 204) {
        throw new Error(`Response status: ${response.status}`);
      }
      const data = await response.data
      console.log(data)
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get(`/api/v1/aventuriers`)
        if (response.status != 200) {
          throw new Error(`Response status: ${response.status}`);
        }
        const data = await response.data
        setAdventurers(data)
      } catch (error) {
        console.log(error)
      }
    }
    fetchData()
  }, [])

  if (!adventurers) {
    return <div>Loading...</div>
  }

  return (
    <>
      <h1>Adventure World</h1>
      {adventurers.map((adventurer) => (
        <a href={`/aventurier?id=${adventurer.id}`} className='card'>
          {isAdmin && (
            <>
              <button onClick={() => deleteAdventurer(adventurer.id)}>Supprimer</button>
              <a href={`/update-adventurer?id=${adventurer.id}`}>Modifier</a>
            </>
          )}
          <h2>{adventurer.nom}</h2>
          <p>{adventurer.description}</p>
        </a>
      ))}
    </>
  )
}

export default App
