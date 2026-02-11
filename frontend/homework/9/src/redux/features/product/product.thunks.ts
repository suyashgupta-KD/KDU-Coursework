import { createAsyncThunk } from "@reduxjs/toolkit";
import { apiGet } from "../../../api/client";
import type { Product, ProductsResponse } from "../../../types/product";

export const fetchAllProducts = createAsyncThunk<
  Product[],
  void,
  { rejectValue: string }
>("products/fetchAll", async (_, thunkApi) => {
  try {
    const data = await apiGet<ProductsResponse>("/products");
    return data.products;
  } catch {
    return thunkApi.rejectWithValue("Failed to load products.");
  }
});

export const searchProducts = createAsyncThunk<
  Product[],
  string,
  { rejectValue: string }
>("products/search", async (query, thunkApi) => {
  const trimmed = query.trim();

  if (!trimmed) {
    return [];
  }

  try {
    const data = await apiGet<ProductsResponse>(
      `/products/search?q=${encodeURIComponent(trimmed)}`,
    );
    return data.products;
  } catch {
    return thunkApi.rejectWithValue("Failed to search products.");
  }
});

export const fetchProductById = createAsyncThunk<
  Product,
  number,
  { rejectValue: string }
>("products/fetchById", async (id, thunkApi) => {
  try {
    return await apiGet<Product>(`/products/${id}`);
  } catch {
    return thunkApi.rejectWithValue("Failed to load product details.");
  }
});
