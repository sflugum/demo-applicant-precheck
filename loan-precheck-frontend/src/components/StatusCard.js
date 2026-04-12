function StatusCard({ status }) {
  const normalizedStatus =
    typeof status === "string" ? status.toUpperCase() : "ERROR";

  const displayStatus =
    normalizedStatus === "APPROVED"
      ? "APPROVED"
      : normalizedStatus === "REVIEW"
      ? "REVIEW"
      : "REVIEW";

  return (
    <div className="status-card">
      <p className="status-label">Application Status</p>

      <p className={`status-value ${displayStatus.toLowerCase()}`}>
        {displayStatus}
      </p>
    </div>
  );
}

export default StatusCard;