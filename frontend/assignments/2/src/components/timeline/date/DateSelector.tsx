import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs";
import { useDispatch, useSelector } from "react-redux";
import type { AppDispatch } from "../../../app/store";
import { selectBookingTimeline } from "../../../features/bookingTimeline/bookingTimelineSelectors";
import { setBookingDate } from "../../../features/bookingTimeline/bookingTimelineSlice";

export default function DateSelector() {
  const dispatch = useDispatch<AppDispatch>();
  const date = useSelector(selectBookingTimeline).date;

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        value={date ? dayjs(date) : null}
        onChange={(newValue) =>
          dispatch(setBookingDate(newValue ? newValue.toISOString() : ""))
        }
        slotProps={{ textField: { fullWidth: true } }}
      />
    </LocalizationProvider>
  );
}
