import { apiGet } from "./client";
import type { Product, ProductsResponse } from "../types/product";

export async function getProducts(): Promise<Product[]> {
  const data = await apiGet<ProductsResponse>("/products");
  return data.products;
}

export async function getProductById(id: number): Promise<Product> {
  return apiGet<Product>(`/products/${id}`);
}
