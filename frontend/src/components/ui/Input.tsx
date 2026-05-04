import './Input.css'

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?:   string
  error?:   string
  /** Thème adapté au fond coloré (label + bordure clairs) */
  onDark?: boolean
}

export default function Input({
  label,
  error,
  onDark  = false,
  id,
  className = '',
  ...props
}: InputProps) {
  const inputId = id ?? label?.toLowerCase().replace(/\s+/g, '-')

  const wrapperClass = [
    'input-wrapper',
    onDark ? 'input-wrapper--dark' : '',
  ].filter(Boolean).join(' ')

  const inputClass = [
    'input-field',
    error ? 'input-field--error' : '',
    className,
  ].filter(Boolean).join(' ')

  return (
    <div className={wrapperClass}>
      {label && (
        <label htmlFor={inputId} className="input-label">
          {label}
        </label>
      )}
      <input id={inputId} className={inputClass} {...props} />
      {error && <span className="input-error">{error}</span>}
    </div>
  )
}
