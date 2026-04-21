import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { deleteJournal, fetchJournalById } from "../api/journals";
import { ErrorState, LoadingState } from "../components/PageState";

function formatDate(rawDate) {
  if (!rawDate) {
    return "No timestamp";
  }
  const parsed = new Date(rawDate);
  return Number.isNaN(parsed.getTime()) ? rawDate : parsed.toLocaleString();
}

export default function JournalDetailPage() {
  const { journalId } = useParams();
  const navigate = useNavigate();
  const [journal, setJournal] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadJournal = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const data = await fetchJournalById(journalId);
      if (!data) {
        setError("Journal entry not found.");
      } else {
        setJournal(data);
      }
    } catch (requestError) {
      setError(requestError.response?.data || "Unable to load the journal entry.");
    } finally {
      setLoading(false);
    }
  }, [journalId]);

  useEffect(() => {
    loadJournal();
  }, [loadJournal]);

  async function handleDelete() {
    const confirmed = window.confirm("Delete this journal entry?");
    if (!confirmed) {
      return;
    }
    try {
      await deleteJournal(journalId);
      navigate("/journals");
    } catch (requestError) {
      setError(requestError.response?.data || "Delete failed.");
    }
  }

  if (loading) {
    return <LoadingState message="Loading journal..." />;
  }

  if (error || !journal) {
    return <ErrorState message={error || "Journal entry not found."} onRetry={loadJournal} />;
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1 className="page-title">{journal.title || "Untitled journal"}</h1>
          <p className="page-subtitle">Created {formatDate(journal.date)}</p>
        </div>
        <div className="row">
          <Link to={`/journals/${journalId}/edit`} className="btn secondary">
            Edit
          </Link>
          <button type="button" className="btn danger" onClick={handleDelete}>
            Delete
          </button>
        </div>
      </div>

      <article className="card detail-card">
        <div className="content-block">{journal.content || "No content."}</div>
        <div className="row">
          <Link to="/journals" className="btn ghost">
            Back to journals
          </Link>
        </div>
      </article>
    </>
  );
}
