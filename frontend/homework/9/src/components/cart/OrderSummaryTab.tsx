import styles from "./OrderSummaryTab.module.scss";

type OrderSummaryTabProps = {
  totalItems: number;
  totalPrice: number;
  onContinueShopping: () => void;
};

export function OrderSummaryTab({
  totalItems,
  totalPrice,
  onContinueShopping,
}: OrderSummaryTabProps) {
  return (
    <aside className={styles.card}>
      <h2 className={styles.title}>Order Summary</h2>

      <div className={styles.row}>
        <span>Total Items:</span>
        <span>{totalItems}</span>
      </div>

      <div className={styles.divider} />

      <div className={styles.totalRow}>
        <span>Total Price:</span>
        <span>${totalPrice.toFixed(2)}</span>
      </div>

      <button
        type="button"
        className={styles.continueButton}
        onClick={onContinueShopping}
      >
        Continue Shopping
      </button>
    </aside>
  );
}
