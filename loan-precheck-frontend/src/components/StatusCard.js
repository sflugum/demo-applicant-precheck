const StatusCard=({ status }) => {
  const normalizedStatus =
    typeof status === "string" ? status.toUpperCase() : "REVIEW";


    const isApproved = normalizedStatus === "APPROVED";
    const alertClass = isApproved ? "alert-success" : "alert-warning";
    const badgeClass = isApproved ? "bg-success" : "bg-warning text-dark";
  
  return (
    <div className={`alert ${alertClass} shadow-sm mt-4 text-center`} role="alert">
     <h6 className="text-uppercase fw-bold mb-2">Application Status</h6>

     <div className={`badge ${badgeClass} p-2 px-4 fs-5`}>
      {normalizedStatus}
     </div>

     <p className="mt-2 mb-0 small text-muted">
      {isApproved
        ? "Congratulations! You are pre-approved."
        : "A member of our team will contact you within 24 business hours."
        }
     </p>
    </div>
  );
}

export default StatusCard;