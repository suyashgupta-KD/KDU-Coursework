export const ROUTES = {
  HOME: "/",
  PRODUCT_DETAILS: "/product/:id",
  CART: "/cart",
} as const;

export const buildProductDetailsPath = (id: number) => `/product/${id}`;
