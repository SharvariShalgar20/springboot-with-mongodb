import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import api from '../api/axiosConfig'
import JournalCard from '../components/JournalCard'
import { PenLine, BookOpen } from 'lucide-react'
import { useAuth } from '../context/AuthContext'

function getId(entry) {
  if (!entry.id) return null
  if (typeof entry.id === 'string') return entry.id
  if (entry.id.$oid) return entry.id.$oid
  if (entry._id) return typeof entry._id === 'string' ? entry._id : entry._id.$oid
  return String(entry.id)
}

export default function Dashboard() {
  const { user } = useAuth()
  const [entries, setEntries] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    api.get('/journal')
      .then(res => setEntries(res.status === 204 ? [] : res.data))
      .catch(err => { if (err.response?.status !== 204) setError('Could not load your entries.') })
      .finally(() => setLoading(false))
  }, [])

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this entry?')) return
    try {
      await api.delete(`/journal/id/${id}`)
      setEntries(prev => prev.filter(e => getId(e) !== id))
    } catch {
      alert('Could not delete entry.')
    }
  }

  return (
    <div style={{ maxWidth: '960px', margin: '0 auto', padding: '2rem 1.5rem' }}>
      <div style={{ marginBottom: '2rem', display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between', flexWrap: 'wrap', gap: '1rem' }}>
        <div>
          <h1 style={{ fontFamily: 'var(--font-serif)', fontSize: '28px', fontWeight: 600, marginBottom: '4px' }}>
            My Journal
          </h1>
          <p style={{ color: 'var(--ink-muted)', fontSize: '14px' }}>Welcome back, {user?.username}</p>
        </div>
        <Link to="/new-entry" className="btn btn-primary">
          <PenLine size={15} />
          New Entry
        </Link>
      </div>

      {loading && (
        <div style={{ display: 'flex', justifyContent: 'center', padding: '3rem' }}>
          <span className="spinner" style={{ width: '28px', height: '28px' }} />
        </div>
      )}

      {error && <p style={{ color: 'var(--danger)', textAlign: 'center' }}>{error}</p>}

      {!loading && !error && entries.length === 0 && (
        <div style={{ textAlign: 'center', padding: '4rem 2rem' }}>
          <BookOpen size={40} color="var(--ink-faint)" style={{ marginBottom: '1rem' }} />
          <p style={{ color: 'var(--ink-muted)', marginBottom: '1.5rem' }}>Your journal is empty. Start writing!</p>
          <Link to="/new-entry" className="btn btn-primary">Write your first entry</Link>
        </div>
      )}

      {!loading && entries.length > 0 && (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: '1rem' }}>
          {entries.map((entry, i) => (
            <JournalCard key={getId(entry) || i} entry={entry} onDelete={handleDelete} />
          ))}
        </div>
      )}
    </div>
  )
}