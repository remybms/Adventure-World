import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Import du hook de navigation
import apiClient from "../../AuthContext/apiClient";
import type { SkillType } from "../Types";

export default function ListSkill() {
    const [skills, setSkills] = useState<SkillType[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<unknown>();
    
    const navigate = useNavigate(); // Initialisation du navigateur

    useEffect(() => {
        const fetchData = async () => {
            try {
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
        return <div style={{ fontFamily: "'Cinzel', serif", padding: "50px" }}>Loading skill catalog...</div>;
    }

    if (error) {
        return <div style={{ padding: "50px", color: "red" }}>Error: {String(error)}</div>;
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
                    onClick={() => navigate(`/skills/detail?id=${skill.id}`)} // Redirection ici
                    style={{
                        backgroundColor: "#F4EDE4",
                        border: "2px solid #D9A441",
                        padding: "25px 40px",
                        borderRadius: "8px",
                        height: "183px",
                        display: "flex",
                        alignItems: "center",
                        cursor: "pointer", // Curseur main pour indiquer le clic
                        transition: "transform 0.1s ease-in-out",
                    }}
                    // Petit effet visuel au clic (optionnel)
                    onMouseEnter={(e) => e.currentTarget.style.transform = "scale(1.01)"}
                    onMouseLeave={(e) => e.currentTarget.style.transform = "scale(1)"}
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