import type { RootState } from "../../store";

export const selectProducts = (state: RootState) => state.products.products;
export const selectAllProducts = (state: RootState) =>
  state.products.allProducts;
export const selectSearchQuery = (state: RootState) =>
  state.products.searchQuery;
export const selectSelectedProduct = (state: RootState) =>
  state.products.selectedProduct;
export const selectProductLoading = (state: RootState) =>
  state.products.loading;
export const selectSearchLoading = (state: RootState) =>
  state.products.searchLoading;
export const selectProductError = (state: RootState) => state.products.error;
