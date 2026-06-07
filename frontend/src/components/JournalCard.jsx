import { Link } from 'react-router-dom'
import { Trash2, Pencil } from 'lucide-react'

// MongoDB ObjectId can come as object or string — normalize it
function getId(entry) {
  if (!entry.id) return null
  if (typeof entry.id === 'string') return entry.id
  // ObjectId returned as object: { timestamp, date, ... } — use $oid if present
  if (entry.id.$oid) return entry.id.$oid
  // fallback: stringify won't work, try _id
  if (entry._id) return typeof entry._id === 'string' ? entry._id : entry._id.$oid
  return String(entry.id)
}

export default function JournalCard({ entry, onDelete }) {
  const id = getId(entry)
  const preview = entry.content?.slice(0, 120) + (entry.content?.length > 120 ? '…' : '')

  return (
    <div className="card" style={{
      display: 'flex', flexDirection: 'column', gap: '0.75rem',
      transition: 'box-shadow 0.15s',
    }}
    onMouseEnter={e => e.currentTarget.style.boxShadow = 'var(--shadow-md)'}
    onMouseLeave={e => e.currentTarget.style.boxShadow = 'var(--shadow-sm)'}
    >
      <h3 style={{
        fontFamily: 'var(--font-serif)', fontSize: '17px',
        fontWeight: 600, color: 'var(--ink)', lineHeight: 1.3
      }}>
        {entry.title}
      </h3>

      {preview && (
        <p style={{ fontSize: '14px', color: 'var(--ink-muted)', lineHeight: 1.6 }}>
          {preview}
        </p>
      )}

      <div style={{ display: 'flex', gap: '8px', marginTop: 'auto', paddingTop: '0.5rem', borderTop: '1px solid var(--border)' }}>
        <Link to={`/edit/${id}`} className="btn btn-ghost" style={{ fontSize: '13px', padding: '0.3rem 0.7rem' }}>
          <Pencil size={13} />
          Edit
        </Link>
        <button onClick={() => onDelete(id)} className="btn btn-danger" style={{ fontSize: '13px', padding: '0.3rem 0.7rem' }}>
          <Trash2 size={13} />
          Delete
        </button>
      </div>
    </div>
  )
}