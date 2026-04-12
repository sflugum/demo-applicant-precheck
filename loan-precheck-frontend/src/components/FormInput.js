function FormInput({ id, label, value, onChange }) {
  return (
    <div className="form-group">
      <label htmlFor={id}>{label}</label>
      <input
        id={id}
        type="number"
        value={value}
        onChange={onChange}
        required
      />
    </div>
  );
}

export default FormInput;