export const ROUTES = {
  REGISTER: "/",
  STATUS: "/status/:id",
} as const;

export const buildStatusPagePath = (id: string) => `/status/${id}`;
