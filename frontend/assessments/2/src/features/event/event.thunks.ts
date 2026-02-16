import { createAsyncThunk } from "@reduxjs/toolkit";
import type {
  Event,
  EventGetResponse,
  EventPostResponse,
} from "../../types/event";

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? "").replace(
  /\/$/,
  "",
);

export const addNewEvent = createAsyncThunk<
  EventPostResponse,
  Event,
  { rejectValue: string }
>("event/addNewEvent", async (newEvent, thunkAPI) => {
  try {
    const response = await fetch(`${API_BASE_URL}/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newEvent),
    });

    const data = (await response.json()) as EventPostResponse;

    if (!response.ok) {
      return thunkAPI.rejectWithValue(data.message || "Failed to register.");
    }

    return data;
  } catch {
    return thunkAPI.rejectWithValue("error");
  }
});

export const fetchRegistrationStatus = createAsyncThunk<
  EventGetResponse,
  string,
  { rejectValue: string }
>("event/fetchRegistrationStatus", async (regId, thunkAPI) => {
  try {
    const response = await fetch(`${API_BASE_URL}/registrationStatus/${regId}`);
    const data = (await response.json()) as EventGetResponse;

    if (!response.ok) {
      return thunkAPI.rejectWithValue("Failed to fetch registration status.");
    }

    return data;
  } catch {
    return thunkAPI.rejectWithValue("Network error while checking status.");
  }
});
