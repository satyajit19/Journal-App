export function LoadingState({ message = "Loading..." }) {
  return (
    <div className="card status-card">
      <h3>{message}</h3>
    </div>
  );
}

export function ErrorState({ message, onRetry }) {
  return (
    <div className="card status-card">
      <h3>Something went wrong</h3>
      <p className="muted">{message}</p>
      {onRetry ? (
        <button type="button" className="btn" onClick={onRetry}>
          Retry
        </button>
      ) : null}
    </div>
  );
}

export function EmptyState({ title, message, action }) {
  return (
    <div className="card empty-state">
      <h3>{title}</h3>
      <p className="muted">{message}</p>
      {action}
    </div>
  );
}
