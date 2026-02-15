import dayjs from "dayjs";
import { useSelector } from "react-redux";
import { selectBookingTimeline } from "../../features/bookingTimeline/bookingTimelineSelectors";
import { selectPersonalDetails } from "../../features/personalDetails/personalDetailsSelectors";
import styles from "./SummaryTab.module.scss";

interface SummaryTabProps {
  cleaningType: string;
  frequency: string;
  totalPrice: number;
}

export function SummaryTab({
  cleaningType,
  frequency,
  totalPrice,
}: SummaryTabProps) {
  const timeline = useSelector(selectBookingTimeline);
  const personal = useSelector(selectPersonalDetails);

  const formattedDate = timeline.date
    ? dayjs(timeline.date).format("ddd, MMM D YYYY")
    : "Not selected";

  const formattedAddress = personal.address
    ? `${personal.address}${personal.pincode ? `, ${personal.pincode}` : ""}`
    : "Not added";

  return (
    <div className={styles.tab}>
      <div className={styles.summary}>
        <h3 className={styles.heading}>Booking Summary</h3>
        <p>Cleaning Type: {cleaningType || "Not selected"}</p>
        <p>Date: {formattedDate}</p>
        <p>Hours: {timeline.hours || "Not selected"}</p>
        <p>Frequency: {frequency || "Not selected"}</p>
        <p>Address: {formattedAddress}</p>
      </div>
      <div className={styles.price}>
        <p>Total cost: ${totalPrice}</p>
      </div>
    </div>
  );
}
