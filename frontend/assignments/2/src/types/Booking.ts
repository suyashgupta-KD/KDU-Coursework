import type { CleaningType, Extras, Frequency } from "./BookingConfig";

export type Booking = {
  typeId: CleaningType["id"];
  frequencyId: Frequency["id"];
  bedrooms: number;
  bathrooms: number;
  hours: number;
  date: string;
  slotId: string;
  email: string;
  phone: string;
  address: string;
  pincode: number;
  extras: Extras["id"][];
  notes?: string;
  totalPrice: number;
};
