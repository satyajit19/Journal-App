import { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function SignupPage() {
  const { isAuthenticated, signup, authLoading } = useAuth();
  const [form, setForm] = useState({
    username: "",
    password: "",
    email: "",
    sentimentAnalysis: false,
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  if (isAuthenticated) {
    return <Navigate to="/journals" replace />;
  }

  function handleChange(event) {
    const { name, value, type, checked } = event.target;
    setForm((current) => ({
      ...current,
      [name]: type === "checkbox" ? checked : value,
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setMessage("");
    const result = await signup(form);
    if (!result.ok) {
      setError(result.message);
      return;
    }
    setMessage("Account created. You can now log in.");
    window.setTimeout(() => navigate("/login"), 800);
  }

  return (
    <div className="auth-shell">
      <div className="auth-card">
        <h1>Create account</h1>
        <p>The backend stores users with the `USER` role automatically.</p>

        {error ? <div className="banner error">{error}</div> : null}
        {message ? <div className="banner success">{message}</div> : null}

        <form className="form" onSubmit={handleSubmit}>
          <div className="field">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              name="username"
              value={form.username}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              name="email"
              type="email"
              value={form.email}
              onChange={handleChange}
            />
          </div>

          <div className="field">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              name="password"
              type="password"
              value={form.password}
              onChange={handleChange}
              required
            />
          </div>

          <label className="row">
            <input
              type="checkbox"
              name="sentimentAnalysis"
              checked={form.sentimentAnalysis}
              onChange={handleChange}
            />
            Enable sentiment analysis
          </label>

          <button type="submit" className="btn" disabled={authLoading}>
            {authLoading ? "Creating account..." : "Signup"}
          </button>
        </form>

        <p>
          Already registered? <Link to="/login">Back to login</Link>
        </p>
      </div>
    </div>
  );
}
