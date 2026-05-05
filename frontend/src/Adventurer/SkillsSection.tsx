import { useState, useEffect, useCallback } from "react";
import apiClient from "../../AuthContext/apiClient";

// ─── Types ───────────────────────────────────────────────────────────────────

interface Competence {
  id: string;
  nom: string;
  description?: string;
  classeRequise?: string;
  niveauMinimum?: number;
  physiqueMinimum?: number;
  mentalMinimum?: number;
  perceptionMinimum?: number;
  competencesRequises?: string[];
}

interface CompetencesDisponiblesResult {
  disponibles: Competence[];
  bloquees: Competence[];
}

interface SkillsSectionProps {
  aventurierId: string;
}

// ─── Sub-components ──────────────────────────────────────────────────────────

function SkillBadge({ competence }: { competence: Competence }) {
  return (
    <div className="skill-badge" title={competence.description}>
      <span className="skill-name">{competence.nom}</span>
      {competence.classeRequise && (
        <span className="skill-class">{competence.classeRequise}</span>
      )}
    </div>
  );
}

function EligibleBadge({ competence }: { competence: Competence }) {
  return (
    <div className="skill-badge eligible" title={competence.description}>
      <span className="skill-name">{competence.nom}</span>
      {competence.niveauMinimum && (
        <span className="skill-class">Niv. {competence.niveauMinimum}</span>
      )}
    </div>
  );
}

function BlockedBadge({ competence }: { competence: Competence }) {
  const [expanded, setExpanded] = useState(false);

  const missing: string[] = [];
  if (competence.niveauMinimum) missing.push(`Niv. ${competence.niveauMinimum}`);
  if (competence.physiqueMinimum) missing.push(`Phy. ${competence.physiqueMinimum}`);
  if (competence.mentalMinimum) missing.push(`Men. ${competence.mentalMinimum}`);
  if (competence.perceptionMinimum) missing.push(`Per. ${competence.perceptionMinimum}`);

  return (
    <div
      className="skill-badge blocked"
      onClick={() => setExpanded((v) => !v)}
      title="Cliquer pour voir les prérequis"
    >
      <span className="skill-name">{competence.nom}</span>
      <span className="skill-lock">🔒</span>
      {expanded && missing.length > 0 && (
        <div className="blocked-reasons">
          {missing.map((m, i) => (
            <span key={i} className="reason-tag">{m}</span>
          ))}
        </div>
      )}
    </div>
  );
}

function SkillBlock({
  title,
  color,
  children,
  isEmpty,
}: {
  title: string;
  color: string;
  children: React.ReactNode;
  isEmpty: boolean;
}) {
  return (
    <div className={`skill-block skill-block--${color}`}>
      <div className="skill-block-header">
        <span className="skill-block-title">{title}</span>
      </div>
      <div className="skill-block-content">
        {isEmpty
          ? <span className="skill-empty">No skills</span>
          : children}
      </div>
    </div>
  );
}

// ─── Main component ───────────────────────────────────────────────────────────

export default function SkillsSection({ aventurierId }: SkillsSectionProps) {
  const [acquises, setAcquises] = useState<Competence[]>([]);
  const [disponibles, setDisponibles] = useState<Competence[]>([]);
  const [bloquees, setBloquees] = useState<Competence[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchAll = useCallback(() => {
    setLoading(true);
    setError(null);

    Promise.all([
      apiClient.get<Competence[]>(`/api/v1/aventuriers/${aventurierId}/competences`),
      apiClient.get<CompetencesDisponiblesResult>(`/api/v1/aventuriers/${aventurierId}/competences/disponibles`),
    ])
      .then(([acqRes, dispoRes]) => {
        setAcquises(acqRes.data ?? []);
        setDisponibles(dispoRes.data?.disponibles ?? []);
        setBloquees(dispoRes.data?.bloquees ?? []);
      })
      .catch(() => setError("Impossible de charger les compétences."))
      .finally(() => setLoading(false));
  }, [aventurierId]);

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  return (
    <>
      <style>{`
        .skills-section { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 16px; font-family: 'Cinzel', 'Palatino Linotype', serif; }
        .skill-block { border-radius: 8px; border: 2px solid #D9A441; background: #fdf6e3; min-height: 130px; display: flex; flex-direction: column; overflow: hidden; position: relative; }
        .skill-block-content { padding: 10px 12px; display: flex; flex-wrap: wrap; gap: 8px; flex: 1; align-content: flex-start; }
        .skill-empty { font-size: 12px; color: #aaa; font-style: italic; width: 100%; text-align: center; padding: 16px 0; }
        .skill-badge { position: relative; display: inline-flex; align-items: center; gap: 5px; padding: 4px 10px; border-radius: 20px; background: #fff8e6; border: 1px solid #c8a84b; font-size: 12px; color: #4a3a10; cursor: default; transition: box-shadow 0.15s; max-width: 100%; }
        .skill-badge:hover { box-shadow: 0 2px 8px rgba(200,168,75,0.25); }
      
        @media (max-width: 600px) { .skills-section { grid-template-columns: 1fr; } }
      `}</style>

      <div className="skills-section">
        {loading && <div className="skills-loading">Loading skills…</div>}
        {error && <div className="skills-error">{error}</div>}

        {!loading && (
          <>
            <SkillBlock title="Aquired Skills" color="gold" isEmpty={acquises.length === 0}>
              {acquises.map((c) => <SkillBadge key={c.id} competence={c} />)}
            </SkillBlock>

            <SkillBlock title="Eligible Skills" color="gold" isEmpty={disponibles.length === 0}>
              {disponibles.map((c) => <EligibleBadge key={c.id} competence={c} />)}
            </SkillBlock>

            <SkillBlock title="Blocked Skills" color="gold" isEmpty={bloquees.length === 0}>
              {bloquees.map((c) => <BlockedBadge key={c.id} competence={c} />)}
            </SkillBlock>
          </>
        )}
      </div>
    </>
  );
}