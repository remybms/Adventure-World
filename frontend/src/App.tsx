import { useState, useEffect } from 'react'
import './App.css'
import apiClient from '../AuthContext/apiClient'
import type AdventurerType from './AdventurerType'

function App() {

  const [adventurers, setAdventurers] = useState<AdventurerType[]>()

  useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await apiClient.get(`/api/v1/aventuriers`)
                if (response.status < 200 || response.status > 299) {
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
      {adventurers.map((adventurer) => (<a href={`/aventurier?id=${adventurer.id}`} className='card'>
            <h2>{adventurer.nom}</h2>
            <p>{adventurer.description}</p>
          </a>
      ))}
    </>
  )
}

export default App
