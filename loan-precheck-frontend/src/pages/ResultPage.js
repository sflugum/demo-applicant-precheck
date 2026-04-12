import { useLocation, useNavigate } from "react-router-dom";
import StatusCard from "../components/StatusCard";

function ResultPage() {
  const location = useLocation();
  const navigate = useNavigate();

  const data = location.state;

  if (!data || !data.status) {
    return (
      <div className="app-container">
        <div className="card">
          <h1 className="title">Application Result</h1>
          <p className="error">No application data found</p>

          <button className="submit-btn" onClick={() => navigate("/")}>
            Back to Form
          </button>
        </div>
      </div>
    );
  }

  const status = data.status;

  return (
    <div className="app-container">
      <div className="card">
        <h1 className="title">Application Result</h1>

        <StatusCard status={status} />

        <button
          className="submit-btn"
          onClick={() => navigate("/")}
        >
          Back to Form
        </button>
      </div>
    </div>
  );
}

export default ResultPage;