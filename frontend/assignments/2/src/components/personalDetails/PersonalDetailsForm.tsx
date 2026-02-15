import type { PersonalDetails } from "../../types/PersonalDetails";
import styles from "./PersonalDetailsForm.module.scss";
interface PersonalDetailsFormProps {
  value: PersonalDetails;
  onChange: (field: keyof PersonalDetails, value: string) => void;
}

export function PersonalDetailsForm({
  value,
  onChange,
}: PersonalDetailsFormProps) {
  return (
    <section className={styles.personalDetails}>
      <h3>Personal Details</h3>
      <input
        placeholder="Email"
        value={value.email}
        onChange={(event) => onChange("email", event.target.value)}
        className={styles.i1}
      />
      <input
        placeholder="Phone"
        value={value.phone}
        onChange={(event) => onChange("phone", event.target.value)}
        className={styles.i2}
      />
      <input
        placeholder="Address"
        value={value.address}
        onChange={(event) => onChange("address", event.target.value)}
        className={styles.i3}
      />
      <input
        placeholder="Pincode"
        value={value.pincode ? String(value.pincode) : ""}
        onChange={(event) => onChange("pincode", event.target.value)}
        className={styles.i4}
      />
    </section>
  );
}
