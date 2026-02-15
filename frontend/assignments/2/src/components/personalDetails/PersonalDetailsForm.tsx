import type { PersonalDetails } from "../../types/PersonalDetails";

interface PersonalDetailsFormProps {
  value: PersonalDetails;
  onChange: (field: keyof PersonalDetails, value: string) => void;
}

export function PersonalDetailsForm({
  value,
  onChange,
}: PersonalDetailsFormProps) {
  return (
    <section>
      <h3>Personal Details</h3>
      <input
        placeholder="Email"
        value={value.email}
        onChange={(event) => onChange("email", event.target.value)}
      />
      <input
        placeholder="Phone"
        value={value.phone}
        onChange={(event) => onChange("phone", event.target.value)}
      />
      <input
        placeholder="Address"
        value={value.address}
        onChange={(event) => onChange("address", event.target.value)}
      />
      <input
        placeholder="Pincode"
        value={value.pincode ? String(value.pincode) : ""}
        onChange={(event) => onChange("pincode", event.target.value)}
      />
    </section>
  );
}
