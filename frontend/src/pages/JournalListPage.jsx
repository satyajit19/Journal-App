import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { deleteJournal, fetchJournals } from "../api/journals";
import JournalCard from "../components/JournalCard";
import { EmptyState, ErrorState, LoadingState } from "../components/PageState";

export default function JournalListPage() {
  const [journals, setJournals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadJournals = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const data = await fetchJournals();
      setJournals(Array.isArray(data) ? data : []);
    } catch (requestError) {
      if (requestError.response?.status === 404) {
        setJournals([]);
      } else {
        setError(requestError.response?.data || "Unable to load journal entries.");
      }
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadJournals();
  }, [loadJournals]);

  async function handleDelete(journalId) {
    const confirmed = window.confirm("Delete this journal entry?");
    if (!confirmed) {
      return;
    }
    try {
      await deleteJournal(journalId);
      setJournals((current) => current.filter((entry) => entry.myid !== journalId));
    } catch (requestError) {
      setError(requestError.response?.data || "Delete failed.");
    }
  }

  if (loading) {
    return <LoadingState message="Loading journals..." />;
  }

  if (error) {
    return <ErrorState message={error} onRetry={loadJournals} />;
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1 className="page-title">Your journals</h1>
          <p className="page-subtitle">Create, review, and edit your personal notes.</p>
        </div>
        <Link to="/journals/new" className="btn">
          New entry
        </Link>
      </div>

      {!journals.length ? (
        <EmptyState
          title="No journal entries yet"
          message="The backend returns 404 when there are no entries. This UI normalizes that into an empty state."
          action={
            <Link to="/journals/new" className="btn">
              Write first entry
            </Link>
          }
        />
      ) : (
        <div className="card-grid">
          {journals.map((journal) => (
            <JournalCard key={journal.myid} journal={journal} onDelete={handleDelete} />
          ))}
        </div>
      )}
    </>
  );
}
