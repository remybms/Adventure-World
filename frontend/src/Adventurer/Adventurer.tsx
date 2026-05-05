import { useEffect, useState } from "react"
import { useSearchParams } from "react-router-dom"
import apiClient from "../../AuthContext/apiClient"
import type { AdventurerType } from "../Types"
import SkillsSection from "./SkillsSection"

export default function Adventurer() {

    const [searchedAdventurer, setSearchedAdventurer] = useState<AdventurerType>()
    const [error, setError] = useState<unknown>()

    const [searchParams] = useSearchParams()
    const id = searchParams.get("id")

    useEffect(() => {
        if (!id) return

        apiClient.get(`/api/v1/aventuriers/${id}`)
            .then((response) => setSearchedAdventurer(response.data))
            .catch((err) => setError(err))
    }, [id])

    if (!id) return <div>ID manquant !</div>
    if (error) return <div>{String(error)}</div>
    if (!searchedAdventurer) return <div>Loading...</div>

    return (
        <div style={{ padding: "24px", display: "flex", flexDirection: "column", gap: "16px" }}>

            <h1>{searchedAdventurer.nom} - {searchedAdventurer.classe}</h1>

            {searchedAdventurer.description && <p>{searchedAdventurer.description}</p>}

            <div>Points de physique : {searchedAdventurer.physique}</div>
            <div>Points de mental : {searchedAdventurer.mental}</div>
            <div>Points de perception : {searchedAdventurer.perception}</div>
            <div>Niveau de l'aventurier : {searchedAdventurer.niveau}</div>

            <SkillsSection aventurierId={id} />

        </div>
    )
}