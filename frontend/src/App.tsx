import { useState, useEffect } from 'react'
import './App.css'
import apiClient from '../AuthContext/apiClient'
import type { AdventurerType } from './Types'
import { useNavigate } from 'react-router-dom'

function App() {

  const [adventurers, setAdventurers] = useState<AdventurerType[]>()

  const navigate = useNavigate()

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
        <div className='grid'>
          {adventurers.map((adventurer) => (
            <div className='card'
              onMouseEnter={(e) => e.currentTarget.style.transform = "scale(1.01)"}
              onMouseLeave={(e) => e.currentTarget.style.transform = "scale(1)"}
              onClick={() => navigate(`/adventurers/detail?id=${adventurer.id}`)}>
              <h2 className='name'>{adventurer.nom}</h2>
            </div>
          ))}
        </div>

      </div>

    </>
  )
}

export default App
