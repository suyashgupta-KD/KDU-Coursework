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
    <section>
      <h3>Credit Card Details</h3>
      <input
        placeholder="Card Number"
        value={value.cardNumber}
        onChange={(event) => onChange("cardNumber", event.target.value)}
      />
      <input
        placeholder="MM/YY"
        value={value.expiry}
        onChange={(event) => onChange("expiry", event.target.value)}
      />
      <input
        placeholder="CVV"
        value={value.cvv}
        onChange={(event) => onChange("cvv", event.target.value)}
      />
      <input
        placeholder="Name On Card"
        value={value.nameOnCard}
        onChange={(event) => onChange("nameOnCard", event.target.value)}
      />
    </section>
  );
}
