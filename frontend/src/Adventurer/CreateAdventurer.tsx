import { useState } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate } from "react-router-dom"

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
        try{
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
        } catch (e){
            console.log(e)
        }
    }

    return (
        <>
            <h1>Création d'un nouvel aventurier</h1>
            <form onSubmit={sendData}>
                <input type="text" placeholder="Nom de l'aventurier" onChange={(e) => setName(e.target.value)} required />
                <select name="classe" id="classe-select" onChange={(e) => setClasse(e.target.value)} required>
                    <option value="">--Veuillez choisir une option--</option>
                    <option value="MAITRE_D_ARMES">Maitre d'Armes</option>
                    <option value="ECLAIREUR">Éclaireur</option>
                    <option value="ARCANISTE">Arcaniste</option>
                    <option value="GARDIEN">Gardien</option>
                    <option value="PREDICATEUR">Prédicateur</option>
                    <option value="GUERRIER">Guerrier</option>
                </select>
                <input type="text" placeholder="Description" onChange={(e) => setDescription(e.target.value)} required />
                <label>Perception de l'aventurier :</label>
                <input type="number" min="0" max="100" id="perception" onChange={(e) => setPerception(e.target.value)} required />
                <label>Physique de l'aventurier :</label>
                <input type="number" min="0" max="100" placeholder="Physique de l'aventurier" onChange={(e) => setPhysique(e.target.value)} required />
                <label>Mental de l'aventurier :</label>
                <input type="number" min="0" max="100" placeholder="Mental de l'aventurier" onChange={(e) => setMental(e.target.value)} required />
                <button type="submit">Envoyer</button>
            </form>
        </>
    )
}