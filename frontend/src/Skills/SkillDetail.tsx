import { useNavigate, useSearchParams } from "react-router-dom";
import apiClient from "../../AuthContext/apiClient"
import { useEffect, useState } from "react";
import { type AdventurersOnSkill, type SkillType } from "../Types";
import "./detail.css"
import Button from "../components/ui/Button";

export default function SkillDetail() {

    const [skill, setSkill] = useState<SkillType>()
    const [adventurers, setAdventurers] = useState<AdventurersOnSkill>()
    const [error, setError] = useState<unknown>()


    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

    const navigate = useNavigate()

    const deleteSkill = async () => {
        try{
            const response = await apiClient.delete(`/api/v1/competences/${id}`)
            if (response.status != 204){
                throw new Error(`Response status: ${response.status}`)
            }
            const data = await response.data
            console.log(data)
            navigate("/skills")
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        const fetchCompetence = async () => {
            try {
                const response = await apiClient.get(`/api/v1/competences/${id}`)
                if (response.status != 200) {
                    throw new Error(`Response status: ${response.status}`);
                }
                const data = await response.data
                setSkill(data)
            } catch (e) {
                setError(e)
            }
        }

        const fetchAventuriers = async () => {
            try {
                const response = await apiClient.get(`/api/v1/competences/${id}/aventuriers`)
                if (response.status != 200) {
                    throw new Error(`Response status: ${response.status}`);
                }
                const data = await response.data
                setAdventurers(data)
            } catch (e) {
                setError(e)
            }
        }

        fetchCompetence()
        fetchAventuriers()
    }, [id])

    if (!adventurers || !skill) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>{String(error)}</div>
    }

    return (
        <div className="competence">
            <div className="head">
                <h1>Skill : {skill.nom}</h1>
                <div>
                    <Button variant="gold" className="button" onClick={() => navigate(`/update-skill?id=${skill.id}`)}>Edit skill</Button>
                    <Button variant="ruby" className="button" onClick={() => deleteSkill()}>Delete skill</Button>
                </div>
            </div>
            <div className="description">
                <span className="labelCompetence">Description</span>
                {skill.description}
            </div>
            <div className="prerequisites">
                <span className="labelCompetence">Prerequisites</span>
                {skill.classeRequise && <p>Required class : {skill.classeRequise}</p>}
                {skill.niveauMinimum && <p>Minimum level : {skill.niveauMinimum}</p>}
                {skill.competencesRequises && skill.competencesRequises.length > 0 && (<p>Required skills: {skill.competencesRequises.join(', ')}</p>)}
            </div>
            <div className="possesseurs">
                <span className="labelAventurier">Owners</span>
                {adventurers.possesseurs.map((adventurer) => (
                    <div>{adventurer.nom}</div>
                ))}
            </div>
            <div className="eligibles">
                <span className="labelAventurier">Eligible</span>
                {adventurers.eligibles.map((adventurer) => (
                    <div>{adventurer.nom}</div>
                ))}
            </div>
            <Button variant="gold" className="createButton"  onClick={() => navigate("/create-skill")}>Create a skill</Button>

        </div>
    )
}