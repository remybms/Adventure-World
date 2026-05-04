import { useState } from "react"
import apiClient from "../../AuthContext/apiClient"
import { useNavigate } from "react-router-dom"

export default function Signin() {

    const [email, setEmail] = useState<string>()
    const [password, setPassword] = useState<string>()

    const navigate = useNavigate()

    const sendData = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try {
            const response = await apiClient.post("/auth/register", {
                email: email,
                password: password,
                role: "VIEWER"
            })
            if (response.status != 201) {
                throw new Error(`Response status : ${response.status}`)
            }
            const data = await response.data
            console.log(data)
            navigate('/login')
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <form onSubmit={sendData}>
            <input type="mail" placeholder="exemple@gmail.com" onChange={(e) => { setEmail(e.target.value) }} required/>
            <input type="password" placeholder="password" onChange={(e) => { setPassword(e.target.value) }} required/>
            <button type="submit">Sign In</button>
        </form>
    )
}