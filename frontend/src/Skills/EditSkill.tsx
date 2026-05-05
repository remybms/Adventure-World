import { useEffect, useState } from "react";
import Input from "../components/ui/Input";
import './CreateCompetence.css'
import Button from "../components/ui/Button";
import apiClient from "../../AuthContext/apiClient";
import { useNavigate, useSearchParams } from "react-router-dom";
import { type SkillType } from "../Types";

export default function EditSkill() {

    const [name, setName] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [minimumLevel, setMinimumLevel] = useState<number>(1)
    const [minimumPhysics, setMinimumPhysics] = useState<number>(1)
    const [minimumPerception, setMinimumPerception] = useState<number>(1)
    const [minimumMental, setMinimumMental] = useState<number>(1)
    const [requiredClass, setRequiredClass] = useState<string>()
    const [error, setError] = useState<unknown>()

    const [skill, setSkill] = useState<SkillType>()


    const navigate = useNavigate()

    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

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

    useEffect(() => {
        const getCompetence = async () => {
            try{
                const response = await apiClient.get(`/api/v1/competences/${id}`)
                const data = await response.data
                setName(data.nom)
                setDescription(data.description)
                setMinimumLevel(data.niveauMinimum)
                setMinimumMental(data.mentalMinimum)
                setMinimumPerception(data.perceptionMinimum)
                setMinimumPhysics(data.physiqueMinimum)
                setRequiredClass(data.classeRequise)
                setSkill(data)
            } catch (e) {
                setError(e)
            }
        }

        getCompetence()
    }, [id])

    if (!skill) {
        return <div>Loading...</div>
    }

    if (error) {
        return (
            <div>{String(error)}</div>
        )
    }

    return (
        <>
            <h1>Edit a skill !</h1>
            <form className="form" onSubmit={sendData}>
                <Input
                    label="Competence name"
                    type="text"
                    placeholder="competence name"
                    onDark
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required />
                <Input
                    label="description"
                    type="text"
                    placeholder="description"
                    onDark
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required />
                <Input
                    label="Minimum Level"
                    type="number"
                    placeholder="minimum level, option not required"
                    min={1}
                    max={100}
                    onDark
                    value={minimumLevel}
                    onChange={(e) => setMinimumLevel(Number(e.target.value))} />
                <Input
                    label="Minimum Physics"
                    type="number"
                    placeholder="minimum physics, option not required"
                    min={1}
                    max={50}
                    value={minimumPhysics}
                    onDark
                    onChange={(e) => setMinimumPhysics(Number(e.target.value))} />
                <Input
                    label="Minimum Perception"
                    type="number"
                    placeholder="minimum perception, option not required"
                    min={1}
                    max={50}
                    value={minimumPerception}
                    onDark
                    onChange={(e) => setMinimumPerception(Number(e.target.value))} />
                <Input
                    label="Minimum Mental"
                    type="number"
                    placeholder="minimum mental, option not required"
                    min={1}
                    max={50}
                    value={minimumMental}
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