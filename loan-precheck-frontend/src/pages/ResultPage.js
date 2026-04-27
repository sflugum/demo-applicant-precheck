import { useLocation, useNavigate } from "react-router-dom";
import StatusCard from "../components/StatusCard";

const ResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const data = location.state;

  if (!data || !data.status) {
    return (
      <div className="vh-100 bg-success bg-gradient d-flex align-items-center justify-content-center p-3">
        <div className="container" style={{ maxwidth: '400px' }}>
          <div className="card shadow-lg border-0 rounded-4">
            <div className="card=body p-4">

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
          <div className="card=body p-4">

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