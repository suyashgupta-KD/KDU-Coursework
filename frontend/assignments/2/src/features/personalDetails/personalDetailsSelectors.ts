import type { RootState } from "../../app/store";

export const selectPersonalDetails = (state: RootState) =>
  state.personalDetails.details;
