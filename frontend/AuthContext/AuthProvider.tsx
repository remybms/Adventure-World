import { jwtDecode } from "jwt-decode";
import { useState } from "react";
import type { ReactNode } from "react";
import { AuthContext } from "./AuthContext";


export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState(
    () => localStorage.getItem('token')
  );

  const user = token ? jwtDecode(token) : null;

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

