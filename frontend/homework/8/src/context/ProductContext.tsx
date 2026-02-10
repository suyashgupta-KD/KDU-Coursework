import {
  createContext,
  useCallback,
  useContext,
  useState,
  type ReactNode,
} from "react";
import { apiGet } from "../api/client";
import type { Product, ProductsResponse } from "../types/product";

type ProductContextValue = {
  products: Product[];
  searchQuery: string;
  selectedProduct: Product | null;
  loading: boolean;
  searchLoading: boolean;
  error: string | null;
  fetchAllProducts: () => Promise<void>;
  searchProducts: (query: string) => Promise<void>;
  fetchProductById: (id: number) => Promise<void>;
  setSearchQuery: (query: string) => void;
  clearSearch: () => void;
};

const ProductContext = createContext<ProductContextValue | undefined>(
  undefined,
);

export function ProductProvider({ children }: { children: ReactNode }) {
  const [products, setProducts] = useState<Product[]>([]);
  const [searchQuery, setSearchQueryState] = useState<string>("");
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [searchLoading, setSearchLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchAllProducts = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await apiGet<ProductsResponse>("/products");
      setProducts(data.products);
    } catch {
      setError("Failed to load products.");
    } finally {
      setLoading(false);
    }
  }, []);

  const searchProducts = useCallback(
    async (query: string) => {
      const trimmed = query.trim();
      if (!trimmed) {
        await fetchAllProducts();
        return;
      }

      try {
        setSearchLoading(true);
        setError(null);
        const data = await apiGet<ProductsResponse>(
          `/products/search?q=${encodeURIComponent(trimmed)}`,
        );
        setProducts(data.products);
      } catch {
        setProducts([]);
        setError("Failed to search products.");
      } finally {
        setSearchLoading(false);
      }
    },
    [fetchAllProducts],
  );

  const fetchProductById = useCallback(async (id: number) => {
    try {
      setLoading(true);
      setError(null);
      const data = await apiGet<Product>(`/products/${id}`);
      setSelectedProduct(data);
    } catch {
      setSelectedProduct(null);
      setError("Failed to load product details.");
    } finally {
      setLoading(false);
    }
  }, []);

  const setSearchQuery = useCallback((query: string) => {
    setSearchQueryState(query);
    if (query.trim()) {
      setSearchLoading(true);
    }
  }, []);

  const clearSearch = useCallback(() => {
    setSearchQueryState("");
    setSearchLoading(false);
    fetchAllProducts();
  }, [fetchAllProducts]);

  return (
    <ProductContext.Provider
      value={{
        products,
        searchQuery,
        selectedProduct,
        loading,
        searchLoading,
        error,
        fetchAllProducts,
        searchProducts,
        fetchProductById,
        setSearchQuery,
        clearSearch,
      }}
    >
      {children}
    </ProductContext.Provider>
  );
}

export function useProductContext() {
  const ctx = useContext(ProductContext);
  if (!ctx) {
    throw new Error("useProductContext must be used within ProductProvider");
  }
  return ctx;
}
