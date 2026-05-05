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

export interface CompetenceType {
  id: string,
  nom: string,
  description: string,
  classeRequise : string | null,
  niveauMinimum : number | null,
  competencesRequises : Array<string> | null
}

export interface AventuriersOnCompetence{
  possesseurs: Array<AdventurerType>,
  eligibles: Array<AdventurerType>
}

export interface CustomJwtPayload extends JwtPayload {
  scope: string
}