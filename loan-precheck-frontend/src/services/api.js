export const submitPrecheck = async (creditScore, income) => {
  const res = await fetch("http://localhost:8080/precheck", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ creditScore, income })
  });

  const data = await res.json().catch(() => null);

  // ALWAYS return backend response as-is
  return data;
};