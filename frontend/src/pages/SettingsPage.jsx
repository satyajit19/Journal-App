import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteCurrentUser, fetchGreeting, updateCurrentUser } from "../api/user";
import { useAuth } from "../context/AuthContext";

export default function SettingsPage() {
  const { username, logout } = useAuth();
  const navigate = useNavigate();
  const [greeting, setGreeting] = useState("");
  const [profileForm, setProfileForm] = useState({ username: username || "", password: "" });
  const [loadingGreeting, setLoadingGreeting] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    async function loadGreeting() {
      setLoadingGreeting(true);
      try {
        const response = await fetchGreeting();
        setGreeting(response);
      } catch (requestError) {
        setGreeting("");
        setError(requestError.response?.data || "Unable to load greeting.");
      } finally {
        setLoadingGreeting(false);
      }
    }

    loadGreeting();
  }, []);

  function handleChange(event) {
    const { name, value } = event.target;
    setProfileForm((current) => ({ ...current, [name]: value }));
  }

  async function handleProfileUpdate(event) {
    event.preventDefault();
    setSaving(true);
    setError("");
    setMessage("");
    try {
      await updateCurrentUser(profileForm);
      setMessage(
        "Profile updated in the backend. Since the username or password may have changed, log in again.",
      );
      window.setTimeout(() => {
        logout();
        navigate("/login");
      }, 1000);
    } catch (requestError) {
      setError(requestError.response?.data || "Unable to update your account.");
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete() {
    const confirmed = window.confirm("Delete your account and all linked journal ownership?");
    if (!confirmed) {
      return;
    }
    setError("");
    setMessage("");
    try {
      await deleteCurrentUser();
      logout();
      navigate("/signup");
    } catch (requestError) {
      setError(requestError.response?.data || "Unable to delete your account.");
    }
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1 className="page-title">Settings</h1>
          <p className="page-subtitle">Covers the authenticated `/user` endpoints exposed by the backend.</p>
        </div>
      </div>

      {error ? <div className="banner error">{error}</div> : null}
      {message ? <div className="banner success">{message}</div> : null}

      <div className="settings-grid">
        <section className="card">
          <h2>Greeting</h2>
          <p className="muted">
            {loadingGreeting ? "Loading backend greeting..." : greeting || "No greeting available."}
          </p>
        </section>

        <section className="card">
          <h2>Update account</h2>
          <form className="form" onSubmit={handleProfileUpdate}>
            <div className="field">
              <label htmlFor="settings-username">Username</label>
              <input
                id="settings-username"
                name="username"
                value={profileForm.username}
                onChange={handleChange}
                required
              />
            </div>

            <div className="field">
              <label htmlFor="settings-password">New password</label>
              <input
                id="settings-password"
                name="password"
                type="password"
                value={profileForm.password}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" className="btn" disabled={saving}>
              {saving ? "Saving..." : "Update profile"}
            </button>
          </form>
        </section>

        <section className="card">
          <h2>Delete account</h2>
          <p className="muted">
            This calls `DELETE /user`. The backend deletes the user by username.
          </p>
          <button type="button" className="btn danger" onClick={handleDelete}>
            Delete account
          </button>
        </section>
      </div>
    </>
  );
}
