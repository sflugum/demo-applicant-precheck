/**
 * Reusable numeric input used for both credit score and income on the landing page.
 * Shows an inline warning for negative values, but this is a UI hint only,
 * real validation happens on the backend.
 */
const FormInput=({ id, label, value, onChange, placeholder }) => {
  return (
    <div className="mb-3">
      <label htmlFor={id} className="form-label">{label}</label>
      <input
        id={id}
        className="form-control"
        type="number"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required
        aria-describedby={`${id}-help`} // WCAG accessibility
      />
      {value < 0 && (
        <div id={`${id}-help`} className="text-danger small">
          Value cannot be negative.
          </div>
      )}
    </div>
  );
}

export default FormInput;