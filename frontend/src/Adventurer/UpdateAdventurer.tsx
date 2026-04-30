import { useState, useEffect } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate, useSearchParams } from "react-router-dom"
import type { AdventurerType } from "../AdventurerType"

export default function UpdateAdventurer() {

    const [name, setName] = useState<string>()
    const [classe, setClasse] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [perception, setPerception] = useState<number>()
    const [physique, setPhysique] = useState<number>()
    const [mental, setMental] = useState<number>()
    const [niveau, setNiveau] = useState<number>()
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
                classe: classe,
                description: description,
                perception: perception,
                physique: physique,
                mental: mental,
                niveau: niveau
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
                setClasse(data.classe)
                setDescription(data.description)
                setMental(data.mental)
                setPerception(data.perception)
                setPhysique(data.physique)
                setNiveau(data.niveau)
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
            <form onSubmit={sendData}>
                <input type="text" placeholder="Nom de l'aventurier" value={name} onChange={(e) => setName(e.target.value)} required />
                <select name="classe" id="classe-select" value={classe} onChange={(e) => setClasse(e.target.value)} required >
                    <option value="">--Veuillez choisir une option--</option>
                    <option value="MAITRE_D_ARMES">Maitre d'Armes</option>
                    <option value="ECLAIREUR">Éclaireur</option>
                    <option value="ARCANISTE">Arcaniste</option>
                    <option value="GARDIEN">Gardien</option>
                    <option value="PREDICATEUR">Prédicateur</option>
                    <option value="GUERRIER">Guerrier</option>
                </select>
                <input type="text" placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} required />
                <label>Perception de l'aventurier :</label>
                <input type="number" min="1" max="50" id="perception" value={perception} onChange={(e) => setPerception(Number(e.target.value))} required />
                <label>Physique de l'aventurier :</label>
                <input type="number" min="1" max="50" placeholder="Physique de l'aventurier" value={physique} onChange={(e) => setPhysique(Number(e.target.value))} required />
                <label>Mental de l'aventurier :</label>
                <input type="number" min="1" max="50" placeholder="Mental de l'aventurier" value={mental} onChange={(e) => setMental(Number(e.target.value))} required />
                <button type="submit">Envoyer</button>
            </form>
        </>
    )
}