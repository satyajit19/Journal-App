import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { createJournal, fetchJournalById, updateJournal } from "../api/journals";
import JournalForm from "../components/JournalForm";
import { ErrorState, LoadingState } from "../components/PageState";

export default function JournalEditorPage({ mode }) {
  const { journalId } = useParams();
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState({ title: "", content: "" });
  const [loading, setLoading] = useState(mode === "edit");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadJournal() {
      if (mode !== "edit") {
        return;
      }
      setLoading(true);
      setError("");
      try {
        const data = await fetchJournalById(journalId);
        if (!data) {
          setError("Journal entry not found.");
          return;
        }
        setInitialValues({
          title: data.title || "",
          content: data.content || "",
        });
      } catch (requestError) {
        setError(requestError.response?.data || "Unable to load the journal entry.");
      } finally {
        setLoading(false);
      }
    }

    loadJournal();
  }, [journalId, mode]);

  async function handleSubmit(values) {
    setSaving(true);
    setError("");
    try {
      if (mode === "edit") {
        await updateJournal(journalId, values);
        navigate(`/journals/${journalId}`);
      } else {
        const created = await createJournal(values);
        navigate(created?.myid ? `/journals/${created.myid}` : "/journals");
      }
    } catch (requestError) {
      setError(requestError.response?.data || "Unable to save the journal entry.");
    } finally {
      setSaving(false);
    }
  }

  if (loading) {
    return <LoadingState message="Loading editor..." />;
  }

  if (error && mode === "edit" && !initialValues.title && !initialValues.content) {
    return <ErrorState message={error} />;
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1 className="page-title">
            {mode === "edit" ? "Edit journal entry" : "Create journal entry"}
          </h1>
          <p className="page-subtitle">The backend only needs `title` and `content` on write.</p>
        </div>
        <Link to="/journals" className="btn ghost">
          Back
        </Link>
      </div>

      {error ? <div className="banner error">{error}</div> : null}

      <JournalForm
        initialValues={initialValues}
        submitLabel={mode === "edit" ? "Update entry" : "Create entry"}
        onSubmit={handleSubmit}
        loading={saving}
      />
    </>
  );
}
