import { useLocation, useNavigate } from "react-router-dom";
import StatusCard from "../components/StatusCard";

/**
 * Displays the result of a precheck submission, using data passed via
 * router state from LandingPage. If a user lands here directly (a refresh,
 * or navigating straight to /result) that state is empty, so a fallback
 * view is shown instead.
 */
const ResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const data = location.state;

  if (!data || !data.status) {
    return (
      // Prevent loading if data is completely missing
      <div className="vh-100 bg-success bg-gradient d-flex align-items-center justify-content-center p-3">
        <div className="container" style={{ maxWidth: '400px' }}>
          <div className="card shadow-lg border-0 rounded-4">
            <div className="card-body p-4">

              <h2 className="text-center mb-4 fw-bold text-dark">
                Application Result
              </h2>

              <p className="alert alert-danger" role="alert">
                No application data found
              </p>

              <button className="btn btn-warning btn-lg fw-bold shadow-sm mt-2" onClick={() => navigate("/")}>
                Back to Form
              </button>

            </div>
          </div>
        </div>
      </div>
    );
  }

  const status = data.status;

  return (
    <div className="vh-100 bg-success bg-gradient d-flex align-items-center justify-content-center p-3">
      <div className="container" style={{ maxwidth: '400px' }}>
        <div className="card shadow-lg border-0 rounded-4">
          <div className="card-body p-4">

            <h2 className="text-center mb-4 fw-bold text-dark">
              Application Result
            </h2>

            <StatusCard status={status} />

            <button className="btn btn-warning btn-lg w-100 fw-bold shadow-sm mt-2" onClick={() => navigate("/")}>
              Back to Form
            </button>

          </div>
        </div>
      </div>
    </div>
  );
}

export default ResultPage;