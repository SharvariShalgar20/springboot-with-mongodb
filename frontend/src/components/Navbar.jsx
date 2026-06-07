import { Link, useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { BookOpen, LogOut, PenLine, User } from 'lucide-react'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const isActive = (path) => location.pathname === path

  return (
    <nav style={{
      background: 'var(--paper-card)',
      borderBottom: '1px solid var(--border)',
      height: '60px',
      display: 'flex',
      alignItems: 'center',
      padding: '0 2rem',
      gap: '1.5rem',
      position: 'sticky',
      top: 0,
      zIndex: 100,
    }}>
      {/* Brand */}
      <Link to="/dashboard" style={{
        display: 'flex', alignItems: 'center', gap: '8px',
        fontFamily: 'var(--font-serif)', fontSize: '18px',
        fontWeight: 600, color: 'var(--accent)', flexShrink: 0
      }}>
        <BookOpen size={20} />
        Journal
      </Link>

      <div style={{ flex: 1 }} />

      {user && (
        <>
          <Link to="/dashboard" className="btn btn-ghost" style={{
            borderColor: isActive('/dashboard') ? 'var(--accent-light)' : undefined,
            color: isActive('/dashboard') ? 'var(--accent)' : undefined,
            padding: '0.4rem 0.75rem', fontSize: '13px'
          }}>
            My Entries
          </Link>

          <Link to="/new-entry" className="btn btn-primary" style={{ padding: '0.4rem 0.75rem', fontSize: '13px' }}>
            <PenLine size={14} />
            New Entry
          </Link>

          <span style={{ color: 'var(--ink-muted)', fontSize: '13px', display: 'flex', alignItems: 'center', gap: '5px' }}>
            <User size={14} />
            {user.username}
          </span>

          <button onClick={handleLogout} className="btn btn-ghost" style={{ padding: '0.4rem 0.75rem', fontSize: '13px' }}>
            <LogOut size={14} />
            Logout
          </button>
        </>
      )}
    </nav>
  )
}