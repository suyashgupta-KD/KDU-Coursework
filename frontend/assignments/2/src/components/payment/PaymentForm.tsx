import styles from "./PaymentForm.module.scss";
export type PaymentDetails = {
  cardNumber: string;
  expiry: string;
  cvv: string;
  nameOnCard: string;
};

interface PaymentFormProps {
  value: PaymentDetails;
  onChange: (field: keyof PaymentDetails, value: string) => void;
}

export function PaymentForm({ value, onChange }: PaymentFormProps) {
  return (
    <section className={styles.creditCard}>
      <h1>Credit Card Details</h1>
      <div className={styles.input}>
        <input
          placeholder="Card Number"
          value={value.cardNumber}
          onChange={(event) => onChange("cardNumber", event.target.value)}
          className={styles.i1}
        />
        <input
          placeholder="MM/YY"
          value={value.expiry}
          onChange={(event) => onChange("expiry", event.target.value)}
          className={styles.i2}
        />
        <input
          placeholder="CVV"
          value={value.cvv}
          onChange={(event) => onChange("cvv", event.target.value)}
          className={styles.i3}
        />
        <input
          placeholder="Name On Card"
          value={value.nameOnCard}
          onChange={(event) => onChange("nameOnCard", event.target.value)}
          className={styles.i4}
        />
      </div>
    </section>
  );
}
