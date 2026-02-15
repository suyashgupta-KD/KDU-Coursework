import { useSelector } from "react-redux";
import { selectBookingTimeline } from "../../features/bookingTimeline/bookingTimelineSelectors";
import { selectPersonalDetails } from "../../features/personalDetails/personalDetailsSelectors";

interface SummaryTabProps {
  cleaningType: string;
  frequency: string;
}

export function SummaryTab({ cleaningType, frequency }: SummaryTabProps) {
  const timeline = useSelector(selectBookingTimeline);
  const personal = useSelector(selectPersonalDetails);

  return (
    <section>
      <h3>Booking Summary</h3>
      <p>Cleaning Type: {cleaningType || "Not selected"}</p>
      <p>Date: {timeline.date || "Not selected"}</p>
      <p>Hours: {timeline.hours || "Not selected"}</p>
      <p>Frequency: {frequency || "Not selected"}</p>
      <p>Address: {personal.address || "Not added"}</p>
    </section>
  );
}
