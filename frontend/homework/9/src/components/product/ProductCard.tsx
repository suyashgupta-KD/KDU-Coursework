import type { Product } from "../../types/product";
import { ProductPrice } from "./ProductPrice";
import { StarRating } from "../common/rating/StarRating";
import styles from "./ProductCard.module.scss";

interface ProductCardProps {
  product: Product;
  onClick: (id: number) => void;
}

export function ProductCard({ product, onClick }: ProductCardProps) {
  return (
    <div
      className={styles.card}
      role="button"
      tabIndex={0}
      onClick={() => onClick(product.id)}
    >
      <div className={styles.discountBadge}>
        {product.discountPercentage.toFixed(0)}% OFF
      </div>
      <img
        src={product.thumbnail}
        alt={product.title}
        className={styles.thumbnail}
      />

      <div className={styles.content}>
        <h3 className={styles.title}>{product.title}</h3>
        <p className={styles.brand}>{product.brand}</p>

        <div className={styles.meta}>
          <StarRating value={product.rating} />
          <ProductPrice
            price={product.price}
            discountPercentage={product.discountPercentage}
          />
        </div>
      </div>
    </div>
  );
}
