export const ROUTES = {
  HOME: "/",
  PRODUCT_DETAILS: "/product/:id",
} as const;

export const buildProductDetailsPath = (id: number) => `/product/${id}`;
