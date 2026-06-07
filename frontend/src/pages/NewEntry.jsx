import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../api/axiosConfig'
import { ArrowLeft, Save } from 'lucide-react'

export default function NewEntry() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ title: '', content: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.title.trim()) { setError('Title is required.'); return }
    setLoading(true)
    setError('')
    try {
      await api.post('/journal', form)
      navigate('/dashboard')
    } catch {
      setError('Failed to save entry. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ maxWidth: '720px', margin: '0 auto', padding: '2rem 1.5rem' }}>
      {/* Back */}
      <Link to="/dashboard" style={{ display: 'inline-flex', alignItems: 'center', gap: '5px',
        color: 'var(--ink-muted)', fontSize: '14px', marginBottom: '1.5rem' }}>
        <ArrowLeft size={14} /> Back to journal
      </Link>

      <h1 style={{ fontFamily: 'var(--font-serif)', fontSize: '26px', marginBottom: '1.5rem' }}>
        New Entry
      </h1>

      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label className="label">Title</label>
            <input className="input" type="text" placeholder="What's on your mind today?"
              value={form.title} required
              style={{ fontSize: '16px' }}
              onChange={e => setForm(f => ({ ...f, title: e.target.value }))} />
          </div>

          <div className="field">
            <label className="label">Content</label>
            <textarea className="input" placeholder="Write your thoughts here..."
              value={form.content} rows={12}
              style={{ resize: 'vertical', lineHeight: '1.7', fontFamily: 'var(--font-serif)' }}
              onChange={e => setForm(f => ({ ...f, content: e.target.value }))} />
          </div>

          {error && (
            <p style={{ color: 'var(--danger)', fontSize: '13px', marginBottom: '1rem' }}>{error}</p>
          )}

          <div style={{ display: 'flex', gap: '8px' }}>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? <span className="spinner" /> : <><Save size={14} /> Save Entry</>}
            </button>
            <Link to="/dashboard" className="btn btn-ghost">Cancel</Link>
          </div>
        </form>
      </div>
    </div>
  )
}