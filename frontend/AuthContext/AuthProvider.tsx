import { jwtDecode } from "jwt-decode";
import { useState } from "react";
import type { ReactNode } from "react";
import { AuthContext } from "./AuthContext";
import type { CustomJwtPayload } from "../src/Types";


export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState(
    () => localStorage.getItem('token')
  );

  let user = null;
  if (token) {
    try {
      user = jwtDecode<CustomJwtPayload>(token);
    } catch (error) {
      // Token invalide, le supprimer
      console.log(error)
      localStorage.removeItem('token');
      setToken(null);
    }
  }

  const login = (newToken: string) => {
    localStorage.setItem('token', newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
  };
  
  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

