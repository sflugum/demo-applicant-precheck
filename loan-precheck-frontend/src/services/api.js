export const submitPrecheck = async (creditScore, income) => {
  const res = await fetch(
    `${process.env.REACT_APP_API_URL}/precheck`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ creditScore, income })
    }
  );

  const data = await res.json().catch(() => null);
  return data;
};