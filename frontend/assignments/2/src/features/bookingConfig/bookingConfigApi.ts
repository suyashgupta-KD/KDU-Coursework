import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { BookingConfig } from "../../types/BookingConfig";
const BASE_URL = import.meta.env.VITE_API_BASE_URL;
export const bookingConfigApi = createApi({
  reducerPath: "bookingConfigApi",
  baseQuery: fetchBaseQuery({
    baseUrl: BASE_URL,
  }),
  tagTypes: ["BookingConfig"],
  endpoints: (builder) => ({
    getBookingConfig: builder.query<BookingConfig, void>({
      query: () => "/config",
      providesTags: ["BookingConfig"],
    }),
  }),
});

export const { useGetBookingConfigQuery } = bookingConfigApi;
