import styles from "./ProductPrice.module.scss";
import { getDiscountedPrice, hasDiscount } from "../../utils/pricing";

interface ProductPriceProps {
  price: number;
  discountPercentage: number;
  currencySymbol?: string;
}

export function ProductPrice({
  price,
  discountPercentage,
  currencySymbol = "$",
}: ProductPriceProps) {
  const discounted = getDiscountedPrice(price, discountPercentage);
  const discountedExists = hasDiscount(discountPercentage);

  return (
    <div className={styles.container}>
      {discountedExists ? (
        <>
          <span className={styles.original}>
            {currencySymbol}
            {price.toFixed(2)}
          </span>
          <span className={styles.discounted}>
            {currencySymbol}
            {discounted.toFixed(2)}
          </span>
        </>
      ) : (
        <span className={styles.discounted}>
          {currencySymbol}
          {price.toFixed(2)}
        </span>
      )}
    </div>
  );
}
