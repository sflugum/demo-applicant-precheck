import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import ResultPage from "./pages/ResultPage";
import 'bootstrap/dist/css/bootstrap.min.css'
import "./styles/App.css";

/**
 * Root component. Two routes: the intake form and the result page.
 */
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/result" element={<ResultPage />} />
      </Routes>
    </Router>
  );
}

export default App;