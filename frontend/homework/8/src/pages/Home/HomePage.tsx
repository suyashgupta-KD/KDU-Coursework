import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useProductContext } from "../../context/ProductContext";
import { buildProductDetailsPath } from "../../routes/routePaths";
import { LoadingState } from "../../components/common/loading/LoadingState";
import { ErrorState } from "../../components/common/error/ErrorState";
import { ProductCard } from "../../components/product/ProductCard";

import styles from "./HomePage.module.scss";

export function HomePage() {
  const navigate = useNavigate();
  const {
    products,
    loading,
    searchLoading,
    error,
    searchQuery,
    fetchAllProducts,
  } = useProductContext();
  const [hasLoadedOnce, setHasLoadedOnce] = useState(false);

  useEffect(() => {
    fetchAllProducts().finally(() => setHasLoadedOnce(true));
  }, [fetchAllProducts]);

  const handleCardClick = useCallback(
    (id: number) => {
      navigate(buildProductDetailsPath(id));
    },
    [navigate],
  );

  if (!hasLoadedOnce && loading) {
    return <LoadingState />;
  }

  if (searchLoading) {
    return <LoadingState />;
  }

  if (error) {
    return (
      <ErrorState message={error} onRetry={() => void fetchAllProducts()} />
    );
  }

  if (!searchLoading && searchQuery.trim() && products.length === 0) {
    return <div>No results found.</div>;
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
