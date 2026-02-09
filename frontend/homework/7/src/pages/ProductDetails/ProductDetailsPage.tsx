import { useCallback, useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getProductById } from "../../api/products";
import type { Product } from "../../types/product";

import { LoadingState } from "../../components/common/LoadingState";
import { ErrorState } from "../../components/common/ErrorState";
import { ProductGallery } from "../../components/product/ProductGallery";
import { ProductPrice } from "../../components/product/ProductPrice";
import { StarRating } from "../../components/common/StarRating";

import styles from "./ProductDetailsPage.module.scss";

export function ProductDetailsPage() {
  const navigate = useNavigate();
  const { id } = useParams();

  const productId = useMemo(() => {
    const n = Number(id);
    return Number.isFinite(n) && n > 0 ? n : null;
  }, [id]);

  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchProduct = useCallback(async () => {
    if (!productId) {
      setLoading(false);
      setProduct(null);
      setError("Invalid product ID.");
      return;
    }

    try {
      setLoading(true);
      setError(null);

      const data = await getProductById(productId);
      setProduct(data);
    } catch {
      setProduct(null);
      setError("Failed to load product details. Please try again.");
    } finally {
      setLoading(false);
    }
  }, [productId]);

  useEffect(() => {
    fetchProduct();
  }, [fetchProduct]);

  const handleBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  if (loading) {
    return <LoadingState />;
  }

  if (error) {
    return (
      <ErrorState
        message={error}
        onRetry={productId ? fetchProduct : undefined}
      />
    );
  }

  if (!product) {
    return <ErrorState message="Product not found." />;
  }

  return (
    <div className={styles.page}>
      <button type="button" className={styles.backButton} onClick={handleBack}>
        Back
      </button>

      <div className={styles.layout}>
        <div className={styles.left}>
          <ProductGallery title={product.title} images={product.images} />
        </div>

        <div className={styles.right}>
          <h1 className={styles.title}>{product.title}</h1>

          <div className={styles.subRow}>
            <span className={styles.brand}>{product.brand}</span>
            <span className={styles.category}>{product.category}</span>
          </div>

          <div className={styles.ratingRow}>
            <StarRating value={product.rating} size="medium" />
            <span className={styles.ratingText}>
              {product.rating.toFixed(1)} / 5
            </span>
          </div>

          <div className={styles.priceBlock}>
            <ProductPrice
              price={product.price}
              discountPercentage={product.discountPercentage}
            />
            {product.discountPercentage > 0 && (
              <span className={styles.discountText}>
                {product.discountPercentage.toFixed(2)}% off
              </span>
            )}
          </div>

          <div className={styles.metaGrid}>
            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Stock</div>
              <div className={styles.metaValue}>{product.stock}</div>
            </div>

            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Category</div>
              <div className={styles.metaValue}>{product.category}</div>
            </div>

            <div className={styles.metaItem}>
              <div className={styles.metaLabel}>Brand</div>
              <div className={styles.metaValue}>{product.brand}</div>
            </div>
          </div>

          <section className={styles.section}>
            <h2 className={styles.sectionTitle}>Description</h2>
            <p className={styles.description}>{product.description}</p>
          </section>
        </div>
      </div>
    </div>
  );
}
