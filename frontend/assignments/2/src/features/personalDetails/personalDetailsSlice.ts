import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import type { PersonalDetails } from "../../types/PersonalDetails";

interface PersonalDetailsState {
  details: PersonalDetails;
}

const initialState: PersonalDetailsState = {
  details: {
    email: "",
    phone: "",
    address: "",
    pincode: 0,
  },
};

const personalDetailsSlice = createSlice({
  name: "personalDetails",
  initialState,
  reducers: {
    setPersonalDetails: (state, action: PayloadAction<PersonalDetails>) => {
      state.details = action.payload;
    },
  },
});

export const { setPersonalDetails } = personalDetailsSlice.actions;
export default personalDetailsSlice.reducer;
