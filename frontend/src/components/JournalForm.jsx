import { useEffect, useState } from "react";

const defaultValues = {
  title: "",
  content: "",
};

export default function JournalForm({ initialValues, submitLabel, onSubmit, loading }) {
  const [values, setValues] = useState(defaultValues);

  useEffect(() => {
    setValues({
      title: initialValues?.title || "",
      content: initialValues?.content || "",
    });
  }, [initialValues]);

  function handleChange(event) {
    const { name, value } = event.target;
    setValues((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    await onSubmit(values);
  }

  return (
    <form className="form card" onSubmit={handleSubmit}>
      <div className="field">
        <label htmlFor="title">Title</label>
        <input
          id="title"
          name="title"
          value={values.title}
          onChange={handleChange}
          placeholder="What happened today?"
          required
        />
      </div>

      <div className="field">
        <label htmlFor="content">Content</label>
        <textarea
          id="content"
          name="content"
          value={values.content}
          onChange={handleChange}
          placeholder="Write your journal entry..."
          required
        />
      </div>

      <button type="submit" className="btn" disabled={loading}>
        {loading ? "Saving..." : submitLabel}
      </button>
    </form>
  );
}
