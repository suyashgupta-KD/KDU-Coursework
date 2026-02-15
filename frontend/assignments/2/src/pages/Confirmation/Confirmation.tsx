import { useLocation } from "react-router-dom";
import { ROUTES } from "../../routes/routePaths";
import styles from "./Confirmation.module.scss";

type ConfirmationState = {
  bookingId: string;
  createdAt: string;
  typeId: string;
  frequencyId: string;
  hours: number;
  date: string;
  bedrooms: number;
  bathrooms: number;
  extras: string[];
  totalPrice: number;
};

export function Confirmation() {
  const location = useLocation();
  const state = location.state as ConfirmationState;
  const tableRows: Array<{ label: string; value: string }> = [
    { label: "Booking Id", value: state.bookingId },
    { label: "Created At", value: state.createdAt },
    { label: "Type Id", value: state.typeId },
    { label: "Frequency Id", value: state.frequencyId },
    { label: "Hours", value: String(state.hours) },
    { label: "Date", value: state.date },
    { label: "Bedrooms", value: String(state.bedrooms) },
    { label: "Bathrooms", value: String(state.bathrooms) },
    { label: "Extras", value: state.extras.join(", ") },
    { label: "Total Price", value: `$${state.totalPrice}` },
  ];

  return (
    <main className={styles.page}>
      <section className={styles.card}>
        <h2>BOOKING CONFIRMED</h2>
        <table className={styles.table}>
          <tbody>
            {tableRows.map((row) => (
              <tr key={row.label}>
                <th>{row.label}</th>
                <td>{row.value}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      <button
        className={styles.backButton}
        type="button"
        onClick={() => {
          window.location.href = ROUTES.BOOKING;
        }}
      >
        Back To Booking
      </button>
    </main>
  );
}
