import { jwtDecode } from "jwt-decode";
import type { CustomJwtPayload } from "./AdventurerType";
import { useNavigate } from "react-router-dom";
import "./App.css"

export default function Header() {

    const token = localStorage.getItem("token")
    const isAdmin = token ? jwtDecode<CustomJwtPayload>(token).scope.includes("ADMIN") : false;

    const navigate = useNavigate()

    const disconnect = () => {
        localStorage.removeItem("token")
        navigate('/')
        window.location.reload()
    }

    return (
        <div className="header">
            {isAdmin && <a href="/create-adventurer">Créer un nouvel aventurier</a>}
            {token != null ? <button onClick={() => disconnect()}>Se déconnecter</button> : <a href="/login">Se connecter</a> }
        </div>
    )
}