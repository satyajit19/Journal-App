import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function NavBar() {
  const { username, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/login");
  }

  return (
    <header className="topbar">
      <div className="brand">Journal App</div>
      <nav className="nav-links">
        <NavLink
          to="/journals"
          className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
        >
          Journals
        </NavLink>
        <NavLink
          to="/settings"
          className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
        >
          Settings
        </NavLink>
      </nav>
      <div className="nav-meta">
        <span className="pill">{username || "Authenticated user"}</span>
        <button type="button" className="btn ghost" onClick={handleLogout}>
          Logout
        </button>
      </div>
    </header>
  );
}
