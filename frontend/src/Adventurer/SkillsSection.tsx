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

interface AventurierStats {
  niveau: number;
  physique: number;
  mental: number;
  perception: number;
  classe: string;
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
    </div>
  );
}

function EligibleBadge({ competence }: { competence: Competence }) {
  return (
    <div className="skill-badge eligible" title={competence.description}>
      <span className="skill-name">{competence.nom}</span>
    </div>
  );
}

function BlockedBadge({ competence, stats }: { competence: Competence; stats: AventurierStats | null }) {
  const [expanded, setExpanded] = useState(false);

  const isBlocked = (current: number | string | undefined, required: number | string | undefined) => {
    if (!required || !stats) return false;
    if (typeof required === 'number' && typeof current === 'number') {
      return current < required;
    }
    return current !== required;
  };

  const requirements = [
    { label: `Classe: ${competence.classeRequise}`, val: competence.classeRequise, current: stats?.classe, isCrit: isBlocked(stats?.classe, competence.classeRequise) },
    { label: `Niveau: ${competence.niveauMinimum}`, val: competence.niveauMinimum, current: stats?.niveau, isCrit: isBlocked(stats?.niveau, competence.niveauMinimum) },
    { label: `Physique: ${competence.physiqueMinimum}`, val: competence.physiqueMinimum, current: stats?.physique, isCrit: isBlocked(stats?.physique, competence.physiqueMinimum) },
    { label: `Mental: ${competence.mentalMinimum}`, val: competence.mentalMinimum, current: stats?.mental, isCrit: isBlocked(stats?.mental, competence.mentalMinimum) },
    { label: `Perception: ${competence.perceptionMinimum}`, val: competence.perceptionMinimum, current: stats?.perception, isCrit: isBlocked(stats?.perception, competence.perceptionMinimum) },
  ];

  return (
    <div
      className={`skill-badge blocked ${expanded ? 'active' : ''}`}
      onClick={() => setExpanded((v) => !v)}
    >
      <span className="skill-name">{competence.nom}</span>
      <span className="skill-lock">🔒</span>

      {expanded && (
        <div className="requirements-popup">
          <div className="popup-arrow"></div>
          <div className="popup-header">Prérequis requis :</div>
          <div className="popup-list">
            {requirements.filter(r => r.val !== undefined && r.val !== null).map((req, i) => (
              <div key={i} className={`req-item ${req.isCrit ? 'is-blocking' : 'is-ok'}`}>
                <span className="req-icon">{req.isCrit ? '✕' : '✓'}</span>
                <span className="req-label">{req.label}</span>
                <span className="req-current">({req.current ?? '?'})</span>
              </div>
            ))}
          </div>
          {competence.description && <div className="popup-desc">{competence.description}</div>}
        </div>
      )}
    </div>
  );
}

function SkillBlock({
  title,
  labelClass, // On passe la classe de label (labelCompetence ou labelAventurier)
  children,
  isEmpty,
}: {
  title: string;
  labelClass: string;
  children: React.ReactNode;
  isEmpty: boolean;
}) {
  return (
    <div className="skill-block">
      <span className={labelClass}>{title}</span>
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
  const [stats, setStats] = useState<AventurierStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchAll = useCallback(() => {
    setLoading(true);
    setError(null);

    Promise.all([
      apiClient.get<Competence[]>(`/api/v1/aventuriers/${aventurierId}/competences`),
      apiClient.get<CompetencesDisponiblesResult>(`/api/v1/aventuriers/${aventurierId}/competences/disponibles`),
      apiClient.get<AventurierStats>(`/api/v1/aventuriers/${aventurierId}`)
    ])
      .then(([acqRes, dispoRes, statsRes]) => {
        setAcquises(acqRes.data ?? []);
        setDisponibles(dispoRes.data?.disponibles ?? []);
        setBloquees(dispoRes.data?.bloquees ?? []);
        setStats(statsRes.data);
      })
      .catch(() => setError("Unable to load skills."))
      .finally(() => setLoading(false));
  }, [aventurierId]);

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  return (
    <>
      <style>{`
        .skills-section { 
            display: grid; 
            grid-template-columns: 1fr 1fr 1fr; 
            gap: 24px; 
            font-family: 'Cinzel', 'Palatino Linotype', serif; 
            margin: 2rem;
        }

        .skill-block { 
            position: relative;
            border-radius: var(--radius-md); 
            border: 0.2rem solid var(--color-gold); 
            background: #fdf6e3; 
            min-height: 100px; 
            display: flex; 
            flex-direction: column; 
            padding: 1.5rem 1rem 1rem 1rem; /* Padding haut plus grand pour laisser de la place au label */
            overflow: visible; 
        }

        .skill-block-content { 
            display: flex; 
            flex-wrap: wrap; 
            gap: 8px; 
            flex: 1; 
            align-content: flex-start; 
        }

        /* Utilisation de tes classes exactes */
        .labelCompetence {
            position: absolute;
            top: -17px;
            left: 30px;
            font-size: large;
            background-color: var(--color-gold-dark);
            color: white;
            padding: 0.2rem 0.5rem;
            border-radius: var(--radius-sm);
            z-index: 2;
        }

        .labelAventurier {
            position: absolute;
            top: -17px;
            left: 30px;
            font-size: large;
            background-color: var(--color-ruby-dark);
            color: white;
            padding: 0.2rem 0.5rem;
            border-radius: var(--radius-sm);
            z-index: 2;
        }

        .skill-badge { 
            position: relative; 
            display: inline-flex; 
            align-items: center; 
            gap: 5px; 
            padding: 4px 10px; 
            border-radius: 20px; 
            background: #fff8e6; 
            border: 1px solid #c8a84b; 
            font-size: 12px; 
            color: #4a3a10; 
            cursor: pointer; 
            transition: all 0.2s; 
        }

        .skill-badge:hover { 
            box-shadow: 0 2px 8px rgba(200,168,75,0.25); 
            transform: translateY(-1px); 
        }
        
        .skill-badge.blocked { border-color: #a3a3a3; background: #ececec; color: #666; }
        .skill-badge.blocked.active { z-index: 10; border-color: #ff4d4f; }

        .requirements-popup {
          position: absolute;
          top: calc(100% + 10px);
          left: 50%;
          transform: translateX(-50%);
          width: 220px;
          background: #2c2c2e;
          border: 1px solid #ff4d4f;
          border-radius: 8px;
          padding: 12px;
          color: white;
          box-shadow: 0 10px 25px rgba(0,0,0,0.3);
          z-index: 1000;
          cursor: default;
        }

        .popup-arrow {
          position: absolute;
          top: -6px;
          left: 50%;
          transform: translateX(-50%) rotate(45deg);
          width: 10px;
          height: 10px;
          background: #2c2c2e;
          border-left: 1px solid #ff4d4f;
          border-top: 1px solid #ff4d4f;
        }

        .popup-header { font-size: 11px; text-transform: uppercase; color: #D9A441; margin-bottom: 8px; border-bottom: 1px solid #444; padding-bottom: 4px; }
        .req-item { display: flex; align-items: center; gap: 6px; font-size: 12px; margin-bottom: 4px; }
        .is-blocking { color: #ff7875; } 
        .is-ok { color: #95de64; opacity: 0.6; }
        
        .skill-empty { font-size: 12px; color: #aaa; font-style: italic; width: 100%; text-align: center; padding: 16px 0; }

        @media (max-width: 900px) { .skills-section { grid-template-columns: 1fr; gap: 32px; } }
      `}</style>

      <div className="skills-section">
        {loading && <div className="skills-loading">Chargement...</div>}
        {error && <div className="skills-error">{error}</div>}

        {!loading && (
          <>
            <SkillBlock title="Aquired skills" labelClass="labelCompetence" isEmpty={acquises.length === 0}>
              {acquises.map((c) => <SkillBadge key={c.id} competence={c} />)}
            </SkillBlock>

            <SkillBlock title="Eligible skills" labelClass="labelCompetence" isEmpty={disponibles.length === 0}>
              {disponibles.map((c) => <EligibleBadge key={c.id} competence={c} />)}
            </SkillBlock>

            <SkillBlock title="Blocked skills" labelClass="labelAventurier" isEmpty={bloquees.length === 0}>
              {bloquees.map((c) => (
                <BlockedBadge key={c.id} competence={c} stats={stats} />
              ))}
            </SkillBlock>
          </>
        )}
      </div>
    </>
  );
}