import { Link } from "react-router-dom";
import { objectIdToString } from "../lib/journal";

function formatDate(rawDate) {
  if (!rawDate) {
    return "No timestamp";
  }
  const parsed = new Date(rawDate);
  return Number.isNaN(parsed.getTime()) ? rawDate : parsed.toLocaleString();
}

export default function JournalCard({ journal, onDelete }) {
  const journalId = objectIdToString(journal.myid);

  return (
    <article className="card journal-card">
      <div className="row">
        <span className="pill">{formatDate(journal.date)}</span>
      </div>
      <div>
        <h3>{journal.title || "Untitled journal"}</h3>
        <p className="muted">
          {(journal.content || "").slice(0, 140)}
          {journal.content?.length > 140 ? "..." : ""}
        </p>
      </div>
      <div className="row">
        <Link to={`/journals/${journalId}`} className="btn">
          View
        </Link>
        <Link to={`/journals/${journalId}/edit`} className="btn secondary">
          Edit
        </Link>
        <button type="button" className="btn ghost" onClick={() => onDelete(journalId)}>
          Delete
        </button>
      </div>
    </article>
  );
}
