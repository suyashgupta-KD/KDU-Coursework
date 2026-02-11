import styles from "./QuantityBox.module.scss";

type QuantityBoxProps = {
  quantity: number;
  onIncrease: () => void;
  onDecrease: () => void;
  disableIncrease?: boolean;
  disableDecrease?: boolean;
};

export function QuantityBox({
  quantity,
  onIncrease,
  onDecrease,
  disableIncrease = false,
  disableDecrease = false,
}: QuantityBoxProps) {
  return (
    <div className={styles.container}>
      <button
        type="button"
        className={styles.button}
        onClick={onDecrease}
        disabled={disableDecrease}
        aria-label="Decrease quantity"
      >
        -
      </button>
      <span className={styles.value}>{quantity}</span>
      <button
        type="button"
        className={styles.button}
        onClick={onIncrease}
        disabled={disableIncrease}
        aria-label="Increase quantity"
      >
        +
      </button>
    </div>
  );
}
