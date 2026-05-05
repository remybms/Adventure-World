import { useState, useEffect } from 'react'
import './App.css'
import apiClient from '../AuthContext/apiClient'
import type { AdventurerType } from './Types'

function App() {

  const [adventurers, setAdventurers] = useState<AdventurerType[]>()



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
      <div className='content'>
        <h1>Welcome to Adventure World !</h1>

        <div className='grid'>
          {adventurers.map((adventurer) => (
          <div className='card'>
            <a href={`/aventurier?id=${adventurer.id}`}>
              <h2>{adventurer.nom}</h2>
            </a>
          </div>
        ))}
        </div>
        
      </div>

    </>
  )
}

export default App
