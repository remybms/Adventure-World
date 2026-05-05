import DeleteEmptyIcon      from '../../assets/icons/delete-empty.svg?react'
import DeleteFilledIcon     from '../../assets/icons/delete-filled.svg?react'
import ParametersEmptyIcon  from '../../assets/icons/parameters-empty.svg?react'
import ParametersFilledIcon from '../../assets/icons/parameters-filled.svg?react'
import PersonEmptyIcon      from '../../assets/icons/person-empty.svg?react'
import PersonFilledIcon     from '../../assets/icons/person-filled.svg?react'
import SkillEmptyIcon       from '../../assets/icons/skill-empty.svg?react'
import SkillFilledIcon      from '../../assets/icons/skill-filled.svg?react'
import SwordEmptyIcon       from '../../assets/icons/sword-empty.svg?react'
import SwordFilledIcon      from '../../assets/icons/sword-filled.svg?react'

const ICONS = {
  deleteEmpty:      DeleteEmptyIcon,
  deleteFilled:     DeleteFilledIcon,
  parametersEmpty:  ParametersEmptyIcon,
  parametersFilled: ParametersFilledIcon,
  personEmpty:      PersonEmptyIcon,
  personFilled:     PersonFilledIcon,
  skillEmpty:       SkillEmptyIcon,
  skillFilled:      SkillFilledIcon,
  swordEmpty:       SwordEmptyIcon,
  swordFilled:      SwordFilledIcon,
} as const

export type IconName = keyof typeof ICONS

interface IconProps {
  name:       IconName
  size?:      number
  className?: string
}

export default function Icon({ name, size = 20, className = '' }: IconProps) {
  const Svg = ICONS[name]
  return <Svg width={size} height={size} className={className} />
}