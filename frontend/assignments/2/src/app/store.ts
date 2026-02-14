import { configureStore } from "@reduxjs/toolkit";
import { bookingConfigApi } from "../features/bookingConfig/bookingConfigApi";
import { setupListeners } from "@reduxjs/toolkit/query";
import bookingTimelineReducer from "../features/bookingTimeline/bookingTimelineSlice";
import personalDetailsReducer from "../features/personalDetails/personalDetailsSlice";
export const store = configureStore({
  reducer: {
    [bookingConfigApi.reducerPath]: bookingConfigApi.reducer,
    bookingTimeline: bookingTimelineReducer,
    personalDetails: personalDetailsReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(bookingConfigApi.middleware),
});

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
