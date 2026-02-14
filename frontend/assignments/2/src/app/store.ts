import { configureStore } from "@reduxjs/toolkit";
import { bookingConfigApi } from "../features/bookingConfig/bookingConfigApi";
import { setupListeners } from "@reduxjs/toolkit/query";
import bookingTimelineReducer from "../features/bookingTimeline/bookingTimelineSlice";
export const store = configureStore({
  reducer: {
    [bookingConfigApi.reducerPath]: bookingConfigApi.reducer,
    bookingTimeline: bookingTimelineReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(bookingConfigApi.middleware),
});

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
