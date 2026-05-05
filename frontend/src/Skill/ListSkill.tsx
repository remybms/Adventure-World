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
            gap: "15px",
            padding: "50px"
        }}>
            {skills.map((skill) => (
                <div
                    key={skill.id}
                    style={{
                        backgroundColor: "#d9d9d9", // Gris du wireframe
                        border: "1px solid #777",
                        padding: "30px",
                        borderRadius: "10px",
                        display: "flex",
                        alignItems: "center",
                        boxShadow: "0 1px 3px rgba(0,0,0,0.1)"
                    }}
                >
                    <span style={{
                        textTransform: "uppercase",
                        fontWeight: "600",
                        letterSpacing: "2px",
                        color: "#000",
                        fontFamily: "sans-serif"
                    }}>
                        {skill.nom}
                    </span>

                </div>
            ))}
        </div>
    );
}