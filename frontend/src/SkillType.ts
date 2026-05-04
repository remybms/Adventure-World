import type { JwtPayload } from "jwt-decode"

export interface SkillType {
    id: string;
    nom: string;
    description: string;
    classeRequise: string;
    niveauMinimum: number;
    competencesRequises: string[];
}

export interface CustomJwtPayload extends JwtPayload {
    scope: string;
}