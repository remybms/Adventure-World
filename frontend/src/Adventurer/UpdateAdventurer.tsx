import { useState, useEffect } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate, useSearchParams } from "react-router-dom"
import type { AdventurerType } from "../Types"
import Input from "../components/ui/Input"
import Button from "../components/ui/Button"
import "./CreateAdventurer.css"

export default function UpdateAdventurer() {

    const [name, setName] = useState<string>()
    const [adventurerClass, setClass] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [perception, setPerception] = useState<number>()
    const [physical, setPhysical] = useState<number>()
    const [mental, setMental] = useState<number>()
    const [level, setLevel] = useState<number>()
    const [searchedAdventurer, setSearchedAdventurer] = useState<AdventurerType>()
    const [error, setError] = useState<unknown>()

    const navigate = useNavigate()

    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

    const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try {
            const response = await apiClient.put(`/api/v1/aventuriers/${id}`, {
                nom: name,
                classe: adventurerClass,
                description: description,
                perception: perception,
                physique: physical,
                mental: mental,
                niveau: level
            })
            const data = await response.data
            console.log(data)
            navigate("/")
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await apiClient.get(`/api/v1/aventuriers/${id}`)
                if (response.status != 200) {
                    throw new Error(`Response status: ${response.status}`);
                }
                const data = await response.data
                setName(data.nom)
                setClass(data.classe)
                setDescription(data.description)
                setMental(data.mental)
                setPerception(data.perception)
                setPhysical(data.physique)
                setLevel(data.niveau)
                setSearchedAdventurer(data)
            } catch (error) {
                setError(error)
            }
        }

        fetchData()
    }, [id])

    if (!searchedAdventurer) {
        return <div>Loading...</div>
    }

    if (error) {
        return (
            <div>{String(error)}</div>
        )
    }

    return (
        <>
            <h1>Modification de l'aventurier : {name}</h1>
            <form onSubmit={sendData} className="formAdventurer">
                <Input type="text" placeholder="Adventurer name" label="Adventurer name" value={name} onDark onChange={(e) => setName(e.target.value)} required />
                <div className="input-wrapper input-wrapper--dark">
                    <label htmlFor="class-select" className="input-label">Class</label>
                    <select name="class" id="class-select" className="input-field select-field" value={adventurerClass} onChange={(e) => setClass(e.target.value)} required>
                        <option value="">--Select an option--</option>
                        <option value="GUERRIER">Warrior</option>
                        <option value="ECLAIREUR">Scout</option>
                        <option value="MAITRE_D_ARMES">Weapons Master</option>
                        <option value="ARCANISTE">Arcanist</option>
                        <option value="GARDIEN">Guardian</option>
                        <option value="PREDICATEUR">Preacher</option>
                    </select>
                </div>
                <Input type="text" placeholder="Description" label="Description" value={description} onDark onChange={(e) => setDescription(e.target.value)} required />
                <Input type="number" min="1" max="50" id="perception" value={perception} onDark placeholder="Adventurer perception" label="Perception" onChange={(e) => setPerception(Number(e.target.value))} required />
                <Input type="number" min="1" max="50" placeholder="Adventurer Physical" value={physical} onDark label="Physical" onChange={(e) => setPhysical(Number(e.target.value))} required />
                <Input type="number" min="1" max="50" placeholder="Adventurer mental" value={physical} onDark label="Mental" onChange={(e) => setMental(Number(e.target.value))} required />
                <Button type="submit" className="submitButton" variant="ruby">Envoyer</Button>
            </form>
        </>
    )
}