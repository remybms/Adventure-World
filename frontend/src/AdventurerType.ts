import type { JwtPayload } from "jwt-decode"

export interface AdventurerType {
    id: string,
    nom: string,
    classe: string,
    description: string,
    perception: number,
    physique: number,
    mental: number,
    niveau: number
}

export interface CustomJwtPayload extends JwtPayload {
  scope: string
}