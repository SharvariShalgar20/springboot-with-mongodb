import { useState, useEffect } from 'react'
import { useNavigate, useParams, Link } from 'react-router-dom'
import api from '../api/axiosConfig'
import { ArrowLeft, Save } from 'lucide-react'

export default function EditEntry() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [form, setForm] = useState({ title: '', content: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    api.get(`/journal/id/${id}`)
      .then(res => {
        setForm({ title: res.data.title || '', content: res.data.content || '' })
      })
      .catch(() => setError('Could not load entry.'))
      .finally(() => setLoading(false))
  }, [id])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSaving(true)
    setError('')
    try {
      await api.put(`/journal/id/${id}`, form)
      navigate('/dashboard')
    } catch {
      setError('Failed to update entry.')
    } finally {
      setSaving(false)
    }
  }

  if (loading) return (
    <div className="page-center">
      <span className="spinner" style={{ width: '32px', height: '32px' }} />
    </div>
  )

  return (
    <div style={{ maxWidth: '720px', margin: '0 auto', padding: '2rem 1.5rem' }}>
      <Link to="/dashboard" style={{ display: 'inline-flex', alignItems: 'center', gap: '5px',
        color: 'var(--ink-muted)', fontSize: '14px', marginBottom: '1.5rem' }}>
        <ArrowLeft size={14} /> Back to journal
      </Link>

      <h1 style={{ fontFamily: 'var(--font-serif)', fontSize: '26px', marginBottom: '1.5rem' }}>
        Edit Entry
      </h1>

      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label className="label">Title</label>
            <input className="input" type="text" value={form.title} required
              style={{ fontSize: '16px' }}
              onChange={e => setForm(f => ({ ...f, title: e.target.value }))} />
          </div>

          <div className="field">
            <label className="label">Content</label>
            <textarea className="input" value={form.content} rows={12}
              style={{ resize: 'vertical', lineHeight: '1.7', fontFamily: 'var(--font-serif)' }}
              onChange={e => setForm(f => ({ ...f, content: e.target.value }))} />
          </div>

          {error && (
            <p style={{ color: 'var(--danger)', fontSize: '13px', marginBottom: '1rem' }}>{error}</p>
          )}

          <div style={{ display: 'flex', gap: '8px' }}>
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? <span className="spinner" /> : <><Save size={14} /> Update Entry</>}
            </button>
            <Link to="/dashboard" className="btn btn-ghost">Cancel</Link>
          </div>
        </form>
      </div>
    </div>
  )
}