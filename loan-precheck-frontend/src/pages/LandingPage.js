import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { submitPrecheck } from "../services/api";
import FormInput from "../components/FormInput";

/**
 * Landing page with the applicant intake form. Submits credit score and
 * income to the backend, then navigates to the result page with the response.
 */
const LandingPage = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [creditScore, setCreditScore] = useState("");
  const [income, setIncome] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");
    setIsLoading(true); 

    // Catches network or server errors during application submission
    submitPrecheck(Number(creditScore), Number(income))
      .then(data => {
        setIsLoading(false);
        if (!data || !data.status) {
          setError("Invalid response from server");
          return;
        }

        // Passed via router state instead of a URL param since this data
        // doesn't need to persist across a refresh or be shareable as a link.
        navigate("/result", { state: data });
      })
      .catch((err) => {
        setIsLoading(false);

        // 1. Handles Spring Boot JSON validation map (e.g. {"creditScore": "Credit score must..."})
        if (err && typeof err === 'object') {
          setError(Object.values(err).join(" | "));
        }
        // 2. Handles Rate Limiting plain text string
        else if (err && typeof err === 'string') {
          setError(err);
        }
        // 3. Fallback for complete network failure
        else {
          setError("Failed to submit");
        }
      });
  };

  return (
    <div className="vh-100 bg-success bg-gradient d-flex align-items-center justify-content-center p-3">
      <div className="container" style={{ maxWidth: '400px' }}>
        <div className="card shadow-lg border-0 rounded-4">
          <div className="card-body p-4">

            <h2 className="text-center mb-4 fw-bold text-dark">
              Pre-Approval Inquiry
            </h2>

            {error && (
              <div className="alert alert-danger py-2 small text-center" role="alert">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="d-grid gap-3">

              <FormInput
                id="creditScore"
                label="Credit Score"
                value={creditScore}
                onChange={(e) => setCreditScore(e.target.value)}
                placeholder="e.g. 750"
              />

              <FormInput
                id="income"
                label="Annual Income"
                value={income}
                onChange={(e) => setIncome(e.target.value)}
                placeholder="e.g. 60000"
              />

              <button type="submit"
                className="btn btn-warning btn-lg fw-bold shadow-sm mt-2"
                disabled={isLoading}
              >
                {isLoading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                    Processing
                  </>
                ) : (
                  "Submit"
                )}
              </button>

              <div className="text-center mt-2">
                <small className="text-muted italic">
                  Note: Initial request may take a moment.
                </small>
              </div>

            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LandingPage;