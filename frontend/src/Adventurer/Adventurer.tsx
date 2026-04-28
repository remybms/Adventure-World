import { useEffect, useState } from "react"
import apiClient from "../../AuthContext/apiClient"
import type AdventurerType from "../AdventurerType"
import { useSearchParams } from "react-router-dom";


export default function Adventurer() {

    const [searchedAdventurer, setSearchedAdventurer] = useState<AdventurerType>()
    const [error, setError] = useState<unknown>()

    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await apiClient.get(`/api/v1/aventuriers/${id}`)
                if (response.status != 200) {
                    throw new Error(`Response status: ${response.status}`);
                }
                const data = await response.data
                setSearchedAdventurer(data)
            } catch (error) {
                setError(error)
            }
        }

        fetchData()
    }, [id])

    if(!id){
        return <div>ID manquant !</div>
    }

    if (!searchedAdventurer) {
        return <div>Loading...</div>
    }

    if (error){
        return <div>{String(error)}</div>
    }

    return (
        <>
            <h1>{searchedAdventurer.nom} - {searchedAdventurer.classe}</h1>
            {searchedAdventurer.description}
            <div>Points de physique : {searchedAdventurer.physique}</div>
            <div>Points de mental : {searchedAdventurer.mental}</div>
            <div>Points de perception : {searchedAdventurer.perception}</div>
            <div>Niveau de l'aventurier : {searchedAdventurer.niveau}</div>
        </>
    )
}