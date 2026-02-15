import type { RootState } from "../../app/store";

export const selectBookingTimeline = (state: RootState) =>
  state.bookingTimeline.timeline;
