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

  // Explicitly handles 400 (Validation)and 429 (Rate Limiter) status codes.
  if (!res.ok) {
      // Determines if the backend sent a JSON validation map or a plain text rate-limit warning.
      const isJson = res.headers.get("content-type")?.includes("application/json");
      const errorData = isJson ? await res.json() : await res.text();

      // Throws he parsed payload to the frontend component's catch block.
      throw errorData;
  }

  // Successful 200 OK response processing.
    return await res.json().catch(() => null);
};