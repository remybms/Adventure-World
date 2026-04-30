import type { JwtPayload } from "jwt-decode";
import { createContext, useContext } from "react";

interface AuthContextType {
  token: string | null;
  user: CustomJwtPayload | null;
  login: (newToken: string) => void;
  logout: () => void;
}

type CustomJwtPayload = JwtPayload & {
  scope?: string;
};

export const AuthContext = createContext<AuthContextType | null>(null);

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }

  return context;
};
