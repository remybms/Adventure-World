import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import type { ReactNode } from "react";


export function PrivateRoute({ children, requiredRole }: { children: ReactNode, requiredRole: string }){
    const { token, user } = useAuth()

    if (!token) return <Navigate to="login" replace/>

    if (requiredRole && requiredRole != user?.scope){
        return <Navigate to="/forbidden" replace />
    }

    return children
}