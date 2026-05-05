import { useEffect, useState } from "react";
import apiClient from "../../AuthContext/apiClient";
import type { SkillType } from "../SkillType";

export default function ListSkill() {
    const [skills, setSkills] = useState<SkillType[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<unknown>();

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Utilisation de l'endpoint exact défini dans l'OpenAPI
                const response = await apiClient.get("/api/v1/competences");
                
                if (response.status !== 200) {
                    throw new Error(`Response status: ${response.status}`);
                }
                
                setSkills(response.data);
            } catch (err) {
                setError(err);
                console.error("Fetch error:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

   if (loading) {
        return <div>Loading skill catalog...</div>;
    }

    if (error) {
        return <div>Error: {String(error)}</div>;
    }

    return (
        <>
            <h1>Catalogue des Compétences</h1>
            <div style={{ display: "flex", flexWrap: "wrap", gap: "20px" }}>
                {skills.map((skill) => (
                    <div key={skill.id} style={{ border: "1px solid #ccc", padding: "15px", width: "300px", borderRadius: "8px" }}>
                        <h3>{skill.nom}</h3>
                    </div>
                ))}
            </div>
        </>
    );
}