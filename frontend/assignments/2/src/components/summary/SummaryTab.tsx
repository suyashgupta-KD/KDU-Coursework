import dayjs from "dayjs";
import { useSelector } from "react-redux";
import { selectBookingTimeline } from "../../features/bookingTimeline/bookingTimelineSelectors";
import { selectPersonalDetails } from "../../features/personalDetails/personalDetailsSelectors";

interface SummaryTabProps {
  cleaningType: string;
  frequency: string;
  totalPrice: number;
}

export function SummaryTab({ cleaningType, frequency, totalPrice }: SummaryTabProps) {
  const timeline = useSelector(selectBookingTimeline);
  const personal = useSelector(selectPersonalDetails);

  const formattedDate = timeline.date
    ? dayjs(timeline.date).format("ddd, MMM D YYYY")
    : "Not selected";

  const formattedAddress = personal.address
    ? `${personal.address}${personal.pincode ? `, ${personal.pincode}` : ""}`
    : "Not added";

  return (
    <section>
      <h3>Booking Summary</h3>
      <p>Cleaning Type: {cleaningType}</p>
      <p>Date: {formattedDate}</p>
      <p>Hours: {timeline.hours || "Not selected"}</p>
      <p>Frequency: {frequency || "Not selected"}</p>
      <p>Address: {formattedAddress}</p>
      <p>Total cost: ${totalPrice}</p>
    </section>
  );
}
