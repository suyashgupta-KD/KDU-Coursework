import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import type { BookingTimeline } from "../../types/BookingTimeline";

interface BookingTimelineState {
  timeline: BookingTimeline;
}

const initialState: BookingTimelineState = {
  timeline: {
    hours: 0,
    date: "",
  },
};

const bookingTimelineSlice = createSlice({
  name: "bookingTimeline",
  initialState,
  reducers: {
    setBookingTimeline: (state, action: PayloadAction<BookingTimeline>) => {
      state.timeline = action.payload;
    },
    setBookingDate: (state, action: PayloadAction<string>) => {
      state.timeline.date = action.payload;
    },
  },
});

export const { setBookingTimeline, setBookingDate } = bookingTimelineSlice.actions;

export default bookingTimelineSlice.reducer;
