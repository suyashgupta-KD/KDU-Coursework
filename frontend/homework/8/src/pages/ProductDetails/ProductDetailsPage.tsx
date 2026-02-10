import { useCallback, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { useProductContext } from "../../context/ProductContext";
import { ROUTES } from "../../routes/routePaths";

import { LoadingState } from "../../components/common/loading/LoadingState";
import { ErrorState } from "../../components/common/error/ErrorState";
import { ProductGallery } from "../../components/product/ProductGallery";
import { ProductPrice } from "../../components/product/ProductPrice";
import { StarRating } from "../../components/common/rating/StarRating";

import styles from "./ProductDetailsPage.module.scss";

export function ProductDetailsPage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const { selectedProduct, loading, error, fetchProductById } =
    useProductContext();

  const productId = Number(id);
  const isValidId = Number.isFinite(productId) && productId > 0;

  useEffect(() => {
    if (isValidId) {
      fetchProductById(productId);
    }
  }, [isValidId, productId, fetchProductById]);

  const handleBack = useCallback(() => {
    navigate(ROUTES.HOME);
  }, [navigate]);

  if (!isValidId) {
    return <ErrorState message="Invalid product ID." />;
  }

  if (loading) {
    return <LoadingState />;
  }

  if (error) {
    return (
      <ErrorState
        message={error}
        onRetry={() => void fetchProductById(productId)}
      />
    );
  }

  if (!selectedProduct) {
    return <ErrorState message="Product not found." />;
  }

  return (
    <div className={styles.page}>
      <button type="button" className={styles.backButton} onClick={handleBack}>
        Back
      </button>

      <div className={styles.layout}>
        <div className={styles.left}>
          <ProductGallery
            title={selectedProduct.title}
            images={selectedProduct.images}
          />
        </div>

        <div className={styles.right}>
          <h1 className={styles.title}>{selectedProduct.title}</h1>

          <div className={styles.subRow}>
            <span className={styles.brand}>{selectedProduct.brand}</span>
            <span className={styles.category}>{selectedProduct.category}</span>
          </div>

          <div className={styles.ratingRow}>
            <StarRating value={selectedProduct.rating} />
            <span className={styles.ratingText}>
              {selectedProduct.rating.toFixed(1)} / 5
            </span>
          </div>

          <div className={styles.priceBlock}>
            <ProductPrice
              price={selectedProduct.price}
              discountPercentage={selectedProduct.discountPercentage}
            />
            {selectedProduct.discountPercentage > 0 && (
              <span className={styles.discountText}>
                {selectedProduct.discountPercentage.toFixed(2)}% off
              </span>
            )}
          </div>

          <div className={styles.metaGrid}>
            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Stock</div>
              <div className={styles.metaValue}>{selectedProduct.stock}</div>
            </div>

            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Category</div>
              <div className={styles.metaValue}>{selectedProduct.category}</div>
            </div>

            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Brand</div>
              <div className={styles.metaValue}>{selectedProduct.brand}</div>
            </div>
          </div>

          <section className={styles.section}>
            <h2 className={styles.sectionTitle}>Description</h2>
            <p className={styles.description}>{selectedProduct.description}</p>
          </section>
        </div>
      </div>
    </div>
  );
}
