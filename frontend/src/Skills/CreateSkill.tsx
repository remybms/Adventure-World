import { useState } from "react";
import Input from "../components/ui/Input";
import './CreateCompetence.css'
import Button from "../components/ui/Button";
import apiClient from "../../AuthContext/apiClient";
import { useNavigate } from "react-router-dom";

export default function CreateSkill() {

    const [name, setName] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [minimumLevel, setMinimumLevel] = useState<number>(1)
    const [minimumPhysics, setMinimumPhysics] = useState<number>(1)
    const [minimumPerception, setMinimumPerception] = useState<number>(1)
    const [minimumMental, setMinimumMental] = useState<number>(1)
    const [requiredClass, setRequiredClass] = useState<string>()

    const navigate = useNavigate()

    const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try{
            const response = await apiClient.post("/api/v1/competence", {
                nom: name,
                description: description,
                classeRequise: requiredClass,
                niveauMinimum: minimumLevel,
                physiqueMinimum: minimumPhysics,
                mentalMinimum: minimumMental,
                perceptionMinimum: minimumPerception
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
            <h1>Create a competence !</h1>
            <form className="form" onSubmit={sendData}>
                <Input
                    label="Competence name"
                    type="text"
                    placeholder="competence name"
                    onDark
                    onChange={(e) => setName(e.target.value)}
                    required />
                <Input
                    label="description"
                    type="text"
                    placeholder="description"
                    onDark
                    onChange={(e) => setDescription(e.target.value)}
                    required />
                <Input
                    label="Minimum Level"
                    type="number"
                    placeholder="minimum level, option not required"
                    min={1}
                    max={100}
                    onDark
                    onChange={(e) => setMinimumLevel(Number(e.target.value))} />
                <Input
                    label="Minimum Physics"
                    type="number"
                    placeholder="minimum physics, option not required"
                    min={1}
                    max={50}
                    onDark
                    onChange={(e) => setMinimumPhysics(Number(e.target.value))} />
                <Input
                    label="Minimum Perception"
                    type="number"
                    placeholder="minimum perception, option not required"
                    min={1}
                    max={50}
                    onDark
                    onChange={(e) => setMinimumPerception(Number(e.target.value))} />
                <Input
                    label="Minimum Mental"
                    type="number"
                    placeholder="minimum mental, option not required"
                    min={1}
                    max={50}
                    onDark
                    onChange={(e) => setMinimumMental(Number(e.target.value))} />
                <div className="input-wrapper input-wrapper--dark">
                    <label htmlFor="role" className="input-label">Required class</label>
                    <select
                        id="role"
                        className="input-field select-field"
                        onChange={(e) => setRequiredClass(e.target.value)}
                    >
                        <option value="">Choose a class, option not required</option>
                        <option value="GUERRIER">Warrior</option>
                        <option value="ECLAIREUR">Scout</option>
                        <option value="MAITRE_D_ARMES">Weapons Master</option>
                        <option value="ARCANISTE">Arcanist</option>
                        <option value="GARDIEN">Guardian</option>
                        <option value="PREDICATEUR">Preacher</option>
                    </select>
                </div>
                <Button type="submit" size="lg" className="submitButton">Create a competence !</Button>
            </form>
        </>
    )
}