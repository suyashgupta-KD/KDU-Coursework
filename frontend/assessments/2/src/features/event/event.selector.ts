import type { RootState } from "../../app/store";

export const selectStatusResponse = (state: RootState) =>
  state.event.statusResponse;

export const selectLoading = (state: RootState) => state.event.loading;

export const selectEventError = (state: RootState) => state.event.error;
