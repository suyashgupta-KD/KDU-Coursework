import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { Booking } from "../../types/Booking";

const BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const bookingApi = createApi({
  reducerPath: "bookingApi",
  baseQuery: fetchBaseQuery({ baseUrl: BASE_URL }),
  endpoints: (builder) => ({
    createBooking: builder.mutation<
      { bookingId: string; createdAt: string },
      Booking
    >({
      query: (body) => ({
        url: "/booking",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const { useCreateBookingMutation } = bookingApi;
