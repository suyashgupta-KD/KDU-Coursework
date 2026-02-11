import { useCallback, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";

import { QuantityBox } from "../../components/cart/QuantityBox";
import { selectCartItems } from "../../redux/features/cart/cart.selectors";
import { addToCart, updateQuantity } from "../../redux/features/cart/cart.slice";
import {
  selectProductError,
  selectProductLoading,
  selectSelectedProduct,
} from "../../redux/features/product/product.selectors";
import { fetchProductById } from "../../redux/features/product/product.thunks";
import type { AppDispatch } from "../../redux/store";

import { LoadingState } from "../../components/common/loading/LoadingState";
import { ErrorState } from "../../components/common/error/ErrorState";
import { ProductGallery } from "../../components/product/ProductGallery";
import { ProductPrice } from "../../components/product/ProductPrice";
import { StarRating } from "../../components/common/rating/StarRating";

import styles from "./ProductDetailsPage.module.scss";

export function ProductDetailsPage() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { id } = useParams();
  const selectedProduct = useSelector(selectSelectedProduct);
  const cartItems = useSelector(selectCartItems);
  const loading = useSelector(selectProductLoading);
  const error = useSelector(selectProductError);

  const productId = Number(id);
  const isValidId = Number.isFinite(productId) && productId > 0;

  useEffect(() => {
    if (isValidId) {
      void dispatch(fetchProductById(productId));
    }
  }, [isValidId, productId, dispatch]);

  const handleBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const handleAddToCart = useCallback(() => {
    if (!selectedProduct || selectedProduct.stock <= 0) {
      return;
    }
    dispatch(addToCart(selectedProduct));
  }, [selectedProduct, dispatch]);

  const currentCartItem = selectedProduct
    ? cartItems.find((item) => item.product.id === selectedProduct.id)
    : undefined;
  const productStock = selectedProduct?.stock ?? 0;
  const isOutOfStock = productStock <= 0;
  const isAtStockLimit = currentCartItem
    ? currentCartItem.quantity >= productStock
    : false;

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
        onRetry={() => void dispatch(fetchProductById(productId))}
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

          <div className={styles.cartActionRow}>
            {currentCartItem ? (
              <>
                <span className={styles.quantityLabel}>Quantity:</span>
                <QuantityBox
                  quantity={currentCartItem.quantity}
                  onDecrease={() =>
                    dispatch(
                      updateQuantity({
                        id: currentCartItem.product.id,
                        quantity: currentCartItem.quantity - 1,
                      }),
                    )
                  }
                  onIncrease={() =>
                    dispatch(
                      updateQuantity({
                        id: currentCartItem.product.id,
                        quantity: currentCartItem.quantity + 1,
                      }),
                    )
                  }
                  disableIncrease={isAtStockLimit}
                />
              </>
            ) : (
              <button
                type="button"
                className={styles.addToCartButton}
                onClick={handleAddToCart}
                disabled={isOutOfStock}
              >
                {isOutOfStock ? "Out of Stock" : "Add to Cart"}
              </button>
            )}
          </div>
          {isAtStockLimit && (
            <p className={styles.stockNote}>Maximum available stock reached.</p>
          )}
        </div>
      </div>
    </div>
  );
}
