import { useLocation, useNavigate } from "react-router-dom";
import { Navbar } from "../../components/navbar/Navbar";
import { ROUTES } from "../../routes/routePaths";

type ConfirmationState = {
  bookingId?: string;
  createdAt?: string;
};

export function Confirmation() {
  const navigate = useNavigate();
  const location = useLocation();
  const state = (location.state ?? {}) as ConfirmationState;

  return (
    <main style={{ padding: "16px", maxWidth: "700px", margin: "0 auto" }}>
      <Navbar />

      <section>
        <h2>BOOKING CONFIRMED</h2>
        <p>Booking ID: {state.bookingId || "Not available"}</p>
        <p>Created At: {state.createdAt || "Not available"}</p>
      </section>

      <button type="button" onClick={() => navigate(ROUTES.BOOKING)}>
        Back To Booking
      </button>
    </main>
  );
}
