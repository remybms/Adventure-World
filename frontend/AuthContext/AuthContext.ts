import { createContext, useContext } from "react";
import type { CustomJwtPayload } from "../src/Types";

interface AuthContextType {
  token: string | null;
  user: CustomJwtPayload | null;
  login: (newToken: string) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }

  return context;
};
