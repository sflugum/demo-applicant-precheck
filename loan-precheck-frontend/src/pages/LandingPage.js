import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { submitPrecheck } from "../services/api";
import FormInput from "../components/FormInput";

function LandingPage() {
  const [creditScore, setCreditScore] = useState("");
  const [income, setIncome] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");

    submitPrecheck(Number(creditScore), Number(income))
      .then(data => {
        if (!data || !data.status) {
          setError("Invalid response from server");
          return;
        }

        navigate("/result", { state: data });
      })
      .catch(() => setError("Failed to submit"));
  };

  return (
    <div className="app-container">
      <div className="card">
        <h1 className="title">Applicant Precheck</h1>

        {error && <p className="error">{error}</p>}

        <form onSubmit={handleSubmit} className="form">

          <FormInput
            id="creditScore"
            label="Credit Score"
            value={creditScore}
            onChange={(e) => setCreditScore(e.target.value)}
          />

          <FormInput
            id="income"
            label="Income"
            value={income}
            onChange={(e) => setIncome(e.target.value)}
          />

          <button type="submit" className="submit-btn">
            Submit Applicant
          </button>

        </form>
      </div>
    </div>
  );
}

export default LandingPage;