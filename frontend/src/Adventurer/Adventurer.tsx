import { useEffect, useState } from "react"
import { useNavigate, useSearchParams } from "react-router-dom"
import apiClient from "../../AuthContext/apiClient"
import type { CustomJwtPayload, AdventurerType } from "../Types"
import SkillsSection from "./SkillsSection"
import "./DetailAdventurer.css"
import Button from "../components/ui/Button"
import { jwtDecode } from "jwt-decode"
import Icon from "../components/ui/Icons"

export default function Adventurer() {

    const [searchedAdventurer, setSearchedAdventurer] = useState<AdventurerType>()
    const [error, setError] = useState<unknown>()

    const token = localStorage.getItem("token")
    const isAdmin = token ? jwtDecode<CustomJwtPayload>(token).scope.includes("ADMIN") : false

    const [searchParams] = useSearchParams()
    const id = searchParams.get("id")

    const navigate = useNavigate()

    const deleteAdventurer = async (idAdventurer: string) => {
        try {
            const response = await apiClient.delete(`/api/v1/aventuriers/${idAdventurer}`)
            if (response.status != 204) {
                throw new Error(`Error status : ${response.status}`)
            }
            const data = await response.data
            console.log(data)
            navigate("/adventurers")
        } catch (error) {
            console.log(error)
        }
    }

    const addLevel = async () => {
        const newLevel = (searchedAdventurer?.niveau ?? 0) + 1;
        try {
            const response = await apiClient.put(`/api/v1/aventuriers/${id}`, {
                nom: searchedAdventurer?.nom,
                classe: searchedAdventurer?.classe,
                description: searchedAdventurer?.description,
                perception: searchedAdventurer?.perception,
                physique: searchedAdventurer?.physique,
                mental: searchedAdventurer?.mental,
                niveau: newLevel
            })
            const data = await response.data
            console.log(data)
            fetchData()
        } catch (e) {
            console.log(e)
        }
    }

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


    useEffect(() => {
        fetchData()
    }, [id])

    if (!id) return <div>ID manquant !</div>
    if (error) return <div>{String(error)}</div>
    if (!searchedAdventurer) return <div>Loading...</div>

    return (
        <div className="pageContent">

            <div className="head">
                <h1>Adventurer : {searchedAdventurer.nom}, {searchedAdventurer.classe}</h1>
                {isAdmin && <div className="adminButtons">
                    <Button size="lg" variant="gold" onClick={() => navigate(`/update-adventurer?id=${id}`)}>Edit Adventurer</Button>
                    <Button size="lg" variant="ruby" onClick={() => deleteAdventurer(id)}><Icon name="deleteWhite" />Delete Adventurer</Button>
                    <Button size="lg" variant="gold" onClick={() => addLevel()} className="level">+1 Level</Button>
                </div>}
            </div>

            <div className="description">
                <span className="label">Description</span>
                <p>{searchedAdventurer.description}</p>
            </div>
            <div className="details">
                <span className="label">Details</span>
                <div>Physical points : {searchedAdventurer.physique}</div>
                <div>Mental points : {searchedAdventurer.mental}</div>
                <div>Perception points : {searchedAdventurer.perception}</div>
                <div>Adventurer level : {searchedAdventurer.niveau}</div>
            </div>


            <SkillsSection adventurerId={id} />

        </div>
    )
}