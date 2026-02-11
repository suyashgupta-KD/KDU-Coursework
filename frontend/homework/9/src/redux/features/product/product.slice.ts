import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Product } from "../../../types/product";
import {
  fetchAllProducts,
  fetchProductById,
  searchProducts,
} from "./product.thunks";

type ProductState = {
  allProducts: Product[];
  products: Product[];
  searchQuery: string;
  selectedProduct: Product | null;
  loading: boolean;
  searchLoading: boolean;
  error: string | null;
};

const initialState: ProductState = {
  allProducts: [],
  products: [],
  searchQuery: "",
  selectedProduct: null,
  loading: false,
  searchLoading: false,
  error: null,
};

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {
    setSearchQuery(state, action: PayloadAction<string>) {
      state.searchQuery = action.payload;
    },
    clearSearch(state) {
      state.searchQuery = "";
      state.searchLoading = false;
      state.error = null;
      state.products = state.allProducts;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.allProducts = action.payload;
        state.products = action.payload;
      })
      .addCase(fetchAllProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Failed to load products.";
      })
      .addCase(searchProducts.pending, (state) => {
        state.searchLoading = true;
        state.error = null;
      })
      .addCase(searchProducts.fulfilled, (state, action) => {
        state.searchLoading = false;
        state.products = action.payload;
      })
      .addCase(searchProducts.rejected, (state, action) => {
        state.searchLoading = false;
        state.error = action.payload ?? "Failed to search products.";
      })
      .addCase(fetchProductById.pending, (state) => {
        state.loading = true;
        state.error = null;
        state.selectedProduct = null;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.loading = false;
        state.selectedProduct = action.payload;
      })
      .addCase(fetchProductById.rejected, (state, action) => {
        state.loading = false;
        state.selectedProduct = null;
        state.error = action.payload ?? "Failed to load product details.";
      });
  },
});

export const { setSearchQuery, clearSearch } = productSlice.actions;
export default productSlice.reducer;
