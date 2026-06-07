import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../api/axiosConfig'
import { BookOpen } from 'lucide-react'

export default function Register() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ username: '', password: '', email: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await api.post('/public/signup', form)
      navigate('/login', { state: { registered: true } })
    } catch (err) {
      setError('Registration failed. Username may already be taken.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-center">
      <div className="card" style={{ width: '100%', maxWidth: '400px' }}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{ display: 'inline-flex', alignItems: 'center', justifyContent: 'center',
            width: '48px', height: '48px', borderRadius: '50%',
            background: 'var(--accent-bg)', marginBottom: '1rem' }}>
            <BookOpen size={22} color="var(--accent)" />
          </div>
          <h1 style={{ fontFamily: 'var(--font-serif)', fontSize: '24px', marginBottom: '4px' }}>
            Start your journal
          </h1>
          <p style={{ color: 'var(--ink-muted)', fontSize: '14px' }}>Create your account</p>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label className="label">Username</label>
            <input className="input" type="text" placeholder="yourname"
              value={form.username} required
              onChange={e => setForm(f => ({ ...f, username: e.target.value }))} />
          </div>
          <div className="field">
            <label className="label">Email (optional)</label>
            <input className="input" type="email" placeholder="you@email.com"
              value={form.email}
              onChange={e => setForm(f => ({ ...f, email: e.target.value }))} />
          </div>
          <div className="field">
            <label className="label">Password</label>
            <input className="input" type="password" placeholder="••••••••"
              value={form.password} required minLength={6}
              onChange={e => setForm(f => ({ ...f, password: e.target.value }))} />
          </div>

          {error && (
            <p style={{ color: 'var(--danger)', fontSize: '13px', marginBottom: '1rem', textAlign: 'center' }}>
              {error}
            </p>
          )}

          <button type="submit" className="btn btn-primary" style={{ width: '100%', justifyContent: 'center', padding: '0.7rem' }} disabled={loading}>
            {loading ? <span className="spinner" /> : 'Create Account'}
          </button>
        </form>

        <p style={{ textAlign: 'center', marginTop: '1.5rem', fontSize: '14px', color: 'var(--ink-muted)' }}>
          Already have an account?{' '}
          <Link to="/login" style={{ color: 'var(--accent)', fontWeight: 500 }}>Sign in</Link>
        </p>
      </div>
    </div>
  )
}