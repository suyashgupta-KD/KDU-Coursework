import styles from "./StarRating.module.scss";

interface StarRatingProps {
  value: number;
}

export function StarRating({ value }: StarRatingProps) {
  return (
    <span
      className={styles.rating}
      aria-label={`Rating ${value.toFixed(1)} out of 5`}
    >
      <span className={styles.star}>★</span>
      <span className={styles.value}>{value.toFixed(1)}</span>
    </span>
  );
}
