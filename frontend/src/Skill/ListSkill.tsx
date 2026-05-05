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
        <div style={{
            display: "flex",
            flexDirection: "column",
            gap: "30px",
            padding: "50px"
        }}>
            {skills.map((skill) => (
                <div
                    key={skill.id}
                    style={{
                        backgroundColor: "#F4EDE4",
                        border: "2px solid #D9A441",
                        padding: "25px 40px",
                        borderRadius: "8px",
                        height: "183px",
                        display: "flex",
                        alignItems: "center",
                    }}
                >
                    <span style={{
                        fontFamily: "'Cinzel', serif",
                        textTransform: "uppercase",
                        fontWeight: "700",
                        color: "#414141",
                        fontSize: "25px"
                    }}>
                        {skill.nom}
                    </span>
                </div>
            ))}
        </div>
    );
}