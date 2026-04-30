import { useState, useEffect } from 'react'
import './App.css'
import apiClient from '../AuthContext/apiClient'
import type { AdventurerType, CustomJwtPayload } from './AdventurerType'
import { jwtDecode } from 'jwt-decode'
import Header from './Header'

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
      fetchData()
    } catch (error) {
      console.log(error)
    }
  }

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

  useEffect(() => {
    fetchData()
  }, [])

  if (!adventurers) {
    return <div>Loading...</div>
  }

  return (
    <>
      <Header />
      <div className='content'>
        <h1>Adventure World</h1>
        {adventurers.map((adventurer) => (
          <div className='card'>
            {isAdmin &&
              <div className='adminButtons'>
                <button onClick={() => deleteAdventurer(adventurer.id)}>Supprimer</button>
                <a href={`/update-adventurer?id=${adventurer.id}`}>Modifier</a>
              </div>
            }
            <a href={`/aventurier?id=${adventurer.id}`}>
              <h2>{adventurer.nom}</h2>
              <p>{adventurer.description}</p>
            </a>
          </div>
        ))}
      </div>

    </>
  )
}

export default App
