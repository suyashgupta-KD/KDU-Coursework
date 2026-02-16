import type { EventGetResponse } from "../../types/event";
import styles from "./RegistrationStatus.module.scss";

type RegistrationStatusProps = {
  data: EventGetResponse | null;
  isLoading: boolean;
  error: string | null;
};

export function RegistrationStatus({
  data,
  isLoading,
  error,
}: RegistrationStatusProps) {
  if (isLoading) {
    return (
      <div className={styles.form}>
        <p>Checking registration status...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.form}>
        <p>{error}</p>
      </div>
    );
  }

  if (!data) {
    return (
      <div className={styles.form}>
        <p>No registration details found.</p>
      </div>
    );
  }

  return (
    <div className={styles.form}>
      <h2>Registration Status</h2>
      <p>Registration ID: {data.regId}</p>
      <p>Name: {data.name}</p>
      <p>Email: {data.email}</p>
      <p>Event: {data.event}</p>
      <p>Status: {data.status}</p>
      {data.rejectReason && <p>Reject Reason: {data.rejectReason}</p>}
    </div>
  );
}
