export type CleaningType = {
  id: "STANDARD" | "DEEP" | "MOVE_IN_OUT";
  label: string;
  basePrice: number;
};

export type Frequency = {
  id: "ONE_TIME" | "WEEKLY" | "BI_WEEKLY" | "MONTHLY";
  label: string;
  multiplier: number;
};

export type RoomPricing = {
  id: "BEDROOM" | "BATHROOM";
  label: string;
  price: number;
};

export type Extras = {
  id: "OVEN" | "WINDOWS" | "FRIDGE" | "IRONING";
  label: string;
  price: number;
};

export type TimeSlot = {
  id: string;
  label: string;
  available: boolean;
};

export type BookingConfig = {
  cleaningTypes: CleaningType[];
  frequencies: Frequency[];
  roomPricing: RoomPricing[];
  extras: Extras[];
  timeSlots: TimeSlot[];
  additionalRequirements?: string;
};
