/**
 * Submits applicant data to the backend precheck endpoint.
 * Returns null if the response body isn't valid JSON so callers can check for that.
 *
 * Note: this doesn't check res.ok, so a non-2xx response (e.g. a validation
 * error from GlobalExceptionHandler) still gets parsed and returned like a normal result.
 */
export const submitPrecheck = async (creditScore, income) => {
  const res = await fetch(
    `${process.env.REACT_APP_API_URL}/api/precheck`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ creditScore, income })
    }
  );

  const data = await res.json().catch(() => null);
  return data;
};