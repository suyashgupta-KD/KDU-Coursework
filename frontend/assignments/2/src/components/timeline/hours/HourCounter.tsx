import styles from "./HourCounter.module.scss";

interface HourCounterProps {
  hours: number;
  increase: () => void;
  decrease: () => void;
}

export function HourCounter({ hours, increase, decrease }: HourCounterProps) {
  return (
    <div className={styles.counter}>
      <button type="button" onClick={decrease} className={styles.button}>
        -
      </button>
      <span className={styles.value}>{hours}</span>
      <button type="button" onClick={increase} className={styles.button}>
        +
      </button>
    </div>
  );
}
