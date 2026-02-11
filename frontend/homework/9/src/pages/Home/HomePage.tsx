import { useCallback, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { buildProductDetailsPath } from "../../routes/routePaths";
import { LoadingState } from "../../components/common/loading/LoadingState";
import { ErrorState } from "../../components/common/error/ErrorState";
import { ProductCard } from "../../components/product/ProductCard";
import {
  selectProductError,
  selectProductLoading,
  selectProducts,
  selectSearchLoading,
  selectSearchQuery,
} from "../../redux/features/product/product.selectors";
import { fetchAllProducts } from "../../redux/features/product/product.thunks";
import type { AppDispatch } from "../../redux/store";

import styles from "./HomePage.module.scss";

export function HomePage() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const products = useSelector(selectProducts);
  const loading = useSelector(selectProductLoading);
  const searchLoading = useSelector(selectSearchLoading);
  const error = useSelector(selectProductError);
  const searchQuery = useSelector(selectSearchQuery);

  useEffect(() => {
    void dispatch(fetchAllProducts());
  }, [dispatch]);

  const handleCardClick = useCallback(
    (id: number) => {
      navigate(buildProductDetailsPath(id));
    },
    [navigate],
  );

  if (loading || searchLoading) {
    return <LoadingState />;
  }

  if (error) {
    return <ErrorState message={error} onRetry={() => void dispatch(fetchAllProducts())} />;
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
