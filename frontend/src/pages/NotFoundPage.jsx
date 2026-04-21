import { Link } from "react-router-dom";

export default function NotFoundPage() {
  return (
    <div className="page">
      <div className="card status-card">
        <h1>Page not found</h1>
        <p className="muted">The route does not exist in the frontend application.</p>
        <Link to="/" className="btn">
          Go home
        </Link>
      </div>
    </div>
  );
}
