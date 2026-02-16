export type Event = {
  regId: string;
  name: string;
  email: string;
  event: string;
  message?: string;
};

export type EventPostResponse = {
  message: string;
  regId: string;
  status: string;
};

export type EventGetResponse = {
  regId: string;
  name: string;
  email: string;
  event: string;
  rejectReason: string | null;
  createdAt: number;
  updatedAt: number;
  status: string;
};
