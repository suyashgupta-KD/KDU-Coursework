import styles from "./AddUser.module.css";

export function AddUser() {
  return (
    <div className={styles.form}>
      <div className={styles.fields}>
        <label className={styles.field}>
          <span>First name</span>
          <input className={styles.input} type="text" placeholder="First Name" />
        </label>
        <label className={styles.field}>
          <span>Last name</span>
          <input className={styles.input} type="text" placeholder="Last Name" />
        </label>
        <label className={styles.field}>
          <span>Email</span>
          <input className={styles.input} type="text" placeholder="Email" />
        </label>
        <label className={styles.field}>
          <span>Age</span>
          <input className={styles.input} type="text" placeholder="Age" />
        </label>
      </div>
      <button className={styles.button}>Add user</button>
    </div>
  );
}
