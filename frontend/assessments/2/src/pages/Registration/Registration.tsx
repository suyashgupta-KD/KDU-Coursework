import { EventRegistration } from "../../components/registrationForm/EventRegistration";
import styles from './Registration.module.scss'
export function Registration() {
  return (
    <div className={styles.form}>
      <EventRegistration />
    </div>
  );
}
