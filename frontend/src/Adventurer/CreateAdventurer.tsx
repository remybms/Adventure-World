import { useState } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate } from "react-router-dom"
import Input from "../components/ui/Input"
import Button from "../components/ui/Button"
import "./CreateAdventurer.css"

export default function CreateAdventurer() {

    const [name, setName] = useState<string>()
    const [classe, setClasse] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [perception, setPerception] = useState<string>()
    const [physique, setPhysique] = useState<string>()
    const [mental, setMental] = useState<string>()

    const navigate = useNavigate()

    const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try {
            const response = await apiClient.post("/api/v1/aventuriers", {
                nom: name,
                classe: classe,
                description: description,
                perception: perception,
                physique: physique,
                mental: mental,
                niveau: 1
            })
            const data = await response.data
            console.log(data)
            navigate("/")
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <>
            <form onSubmit={sendData} className="formAdventurer">
                <Input type="text" placeholder="Adventurer name" label="Adventurer name" onDark onChange={(e) => setName(e.target.value)} required />
                <div className="input-wrapper input-wrapper--dark">
                    <label htmlFor="class-select" className="input-label">Class</label>
                    <select name="class" id="class-select" className="input-field select-field" onChange={(e) => setClasse(e.target.value)} required>
                        <option value="">--Select an option--</option>
                        <option value="GUERRIER">Warrior</option>
                        <option value="ECLAIREUR">Scout</option>
                        <option value="MAITRE_D_ARMES">Weapons Master</option>
                        <option value="ARCANISTE">Arcanist</option>
                        <option value="GARDIEN">Guardian</option>
                        <option value="PREDICATEUR">Preacher</option>
                    </select>
                </div>
                <Input type="text" placeholder="Description" label="Description" onDark onChange={(e) => setDescription(e.target.value)} required />
                <Input type="number" min="1" max="50" id="perception" onDark placeholder="Adventurer perception" label="Perception" onChange={(e) => setPerception(e.target.value)} required />
                <Input type="number" min="1" max="50" placeholder="Adventurer Physical" onDark label="Physical" onChange={(e) => setPhysique(e.target.value)} required />
                <Input type="number" min="1" max="50" placeholder="Adventurer mental" onDark label="Mental" onChange={(e) => setMental(e.target.value)} required />
                <Button type="submit" className="submitButton" variant="ruby">Envoyer</Button>
            </form>
        </>
    )
}