import { createSlice } from "@reduxjs/toolkit";
import type { EventGetResponse, EventPostResponse } from "../../types/event";
import { addNewEvent, fetchRegistrationStatus } from "./event.thunks";

type EventState = {
  registerResponse: EventPostResponse | null;
  statusResponse: EventGetResponse | null;
  loading: boolean;
  error: string | null;
};

const initialState: EventState = {
  registerResponse: null,
  statusResponse: null,
  loading: false,
  error: null,
};

const eventSlice = createSlice({
  name: "event",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(addNewEvent.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(addNewEvent.fulfilled, (state, action) => {
        state.loading = false;
        state.registerResponse = action.payload;
      })
      .addCase(addNewEvent.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Failed to register event.";
      })
      .addCase(fetchRegistrationStatus.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchRegistrationStatus.fulfilled, (state, action) => {
        state.loading = false;
        state.statusResponse = action.payload;
      })
      .addCase(fetchRegistrationStatus.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Failed to load registration status.";
      });
  },
});

export default eventSlice.reducer;
