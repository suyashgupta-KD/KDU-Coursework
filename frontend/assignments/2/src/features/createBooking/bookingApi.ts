import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { Booking } from "../../types/Booking";

const BASE_URL = import.meta.env.VITE_API_BASE_URL;

type BookingResponse = {
  bookingId: string;
  createdAt: string;
};

type WrappedBookingResponse = {
  body?: unknown;
};

export const bookingApi = createApi({
  reducerPath: "bookingApi",
  baseQuery: fetchBaseQuery({ baseUrl: BASE_URL }),
  endpoints: (builder) => ({
    createBooking: builder.mutation<BookingResponse, Booking>({
      query: (body) => ({
        url: "/booking",
        method: "POST",
        body,
      }),
      transformResponse: (
        response: BookingResponse | WrappedBookingResponse,
      ): BookingResponse => {
        const wrapped = response as WrappedBookingResponse;
        if (typeof wrapped.body === "string") {
          return JSON.parse(wrapped.body) as BookingResponse;
        }
        return response as BookingResponse;
      },
    }),
  }),
});

export const { useCreateBookingMutation } = bookingApi;
