import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs";
import { useDispatch, useSelector } from "react-redux";
import type { AppDispatch } from "../../../app/store";
import { selectBookingTimeline } from "../../../features/bookingTimeline/bookingTimelineSelectors";
import { setBookingTimeline } from "../../../features/bookingTimeline/bookingTimelineSlice";

export default function DateSelector() {
  const dispatch = useDispatch<AppDispatch>();
  const timeline = useSelector(selectBookingTimeline);
  const selectedDate = timeline.date ? dayjs(timeline.date) : null;

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DemoContainer components={["DatePicker"]}>
        <DatePicker
          value={selectedDate}
          onChange={(newValue) =>
            dispatch(
              setBookingTimeline({
                ...timeline,
                date: newValue ? newValue.toISOString() : "",
              }),
            )
          }
        />
      </DemoContainer>
    </LocalizationProvider>
  );
}
