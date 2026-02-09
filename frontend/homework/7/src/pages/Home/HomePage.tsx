import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getProducts } from "../../api/products";
import type { Product } from "../../types/product";
import { LoadingState } from "../../components/common/LoadingState";
import { ErrorState } from "../../components/common/ErrorState";
import { ProductCard } from "../../components/product/ProductCard";

import styles from "./HomePage.module.scss";

export function HomePage() {
  const navigate = useNavigate();

  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchProducts = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);

      const data = await getProducts();
      setProducts(data);
    } catch {
      setError("Failed to load products. Please try again.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  const handleCardClick = useCallback(
    (id: number) => {
      navigate(`/product/${id}`);
    },
    [navigate],
  );

  if (loading) {
    return <LoadingState />;
  }

  if (error) {
    return <ErrorState message={error} onRetry={fetchProducts} />;
  }

  return (
    <div>
      <div className={styles.grid}>
        {products.map((p) => (
          <ProductCard key={p.id} product={p} onClick={handleCardClick} />
        ))}
      </div>
    </div>
  );
}
