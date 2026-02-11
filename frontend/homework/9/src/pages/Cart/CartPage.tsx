import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { QuantityBox } from "../../components/cart/QuantityBox";
import { OrderSummaryTab } from "../../components/cart/OrderSummaryTab";
import { ProductPrice } from "../../components/product/ProductPrice";
import {
  selectCartItems,
  selectTotalItems,
  selectTotalPrice,
} from "../../redux/features/cart/cart.selectors";
import {
  removeFromCart,
  updateQuantity,
} from "../../redux/features/cart/cart.slice";
import type { AppDispatch } from "../../redux/store";
import { ROUTES } from "../../routes/routePaths";
import { getDiscountedPrice } from "../../utils/pricing";
import styles from "./CartPage.module.scss";

export function CartPage() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const items = useSelector(selectCartItems);
  const totalItems = useSelector(selectTotalItems);
  const totalPrice = useSelector(selectTotalPrice);

  const handleContinueShopping = () => {
    navigate(ROUTES.HOME);
  };

  if (items.length === 0) {
    return (
      <section className={styles.emptyWrap}>
        <h1 className={styles.pageTitle}>Shopping Cart</h1>
        <p className={styles.emptyMessage}>Cart is empty.</p>
        <button
          type="button"
          className={styles.continueButton}
          onClick={handleContinueShopping}
        >
          Continue Shopping
        </button>
      </section>
    );
  }

  return (
    <section className={styles.page}>
      <h1 className={styles.pageTitle}>Shopping Cart</h1>

      <div className={styles.layout}>
        <div className={styles.items}>
          {items.map((item) => {
            const discounted = getDiscountedPrice(
              item.product.price,
              item.product.discountPercentage,
            );
            const isAtStockLimit = item.quantity >= item.product.stock;

            return (
              <article key={item.product.id} className={styles.itemCard}>
                <img
                  src={item.product.thumbnail}
                  alt={item.product.title}
                  className={styles.thumb}
                />

                <div className={styles.info}>
                  <h2 className={styles.title}>{item.product.title}</h2>
                  <p className={styles.brand}>{item.product.brand}</p>
                  <ProductPrice
                    price={item.product.price}
                    discountPercentage={item.product.discountPercentage}
                  />
                </div>

                <div className={styles.quantityArea}>
                  <div className={styles.label}>Quantity:</div>
                  <QuantityBox
                    quantity={item.quantity}
                    onDecrease={() =>
                      dispatch(
                        updateQuantity({
                          id: item.product.id,
                          quantity: item.quantity - 1,
                        }),
                      )
                    }
                    onIncrease={() =>
                      dispatch(
                        updateQuantity({
                          id: item.product.id,
                          quantity: item.quantity + 1,
                        }),
                      )
                    }
                    disableIncrease={isAtStockLimit}
                  />
                </div>

                <div className={styles.totalArea}>
                  <div className={styles.label}>Item Total:</div>
                  <div className={styles.itemTotal}>
                    ${(discounted * item.quantity).toFixed(2)}
                  </div>
                </div>

                <button
                  type="button"
                  className={styles.removeButton}
                  onClick={() => dispatch(removeFromCart(item.product.id))}
                >
                  Remove
                </button>
              </article>
            );
          })}
        </div>

        <OrderSummaryTab
          totalItems={totalItems}
          totalPrice={totalPrice}
          onContinueShopping={handleContinueShopping}
        />
      </div>
    </section>
  );
}
