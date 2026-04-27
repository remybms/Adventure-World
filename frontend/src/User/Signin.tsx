import { useState } from "react"
import apiClient from "../../AuthContext/apiClient"

export default function Signin() {

    const [username, setUsername] = useState<string>()
    const [password, setPassword] = useState<string>()

    const sendData = async () => {
        try {
            const response = await apiClient.post("/auth/register", {
                username: username,
                password: password,
                role: "VIEWER"
            })
            if (response.status != 201) {
                throw new Error(`Response status : ${response.status}`)
            }
            const data = await response.data
            console.log(data)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <form onSubmit={sendData}>
            <input type="text" placeholder="Nom d'utilisateur" onChange={(e) => { setUsername(e.target.value) }} required/>
            <input type="password" placeholder="Mot de passe" onChange={(e) => { setPassword(e.target.value) }} required/>
            <button type="submit">S'inscrire</button>
        </form>
    )
}