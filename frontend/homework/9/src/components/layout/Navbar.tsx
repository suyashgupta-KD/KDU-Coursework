import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { NavLink } from "react-router-dom";
import { selectTotalItems } from "../../redux/features/cart/cart.selectors";
import {
  clearSearch,
  setSearchQuery,
} from "../../redux/features/product/product.slice";
import { selectSearchQuery } from "../../redux/features/product/product.selectors";
import {
  fetchAllProducts,
  searchProducts,
} from "../../redux/features/product/product.thunks";
import type { AppDispatch } from "../../redux/store";
import { ROUTES } from "../../routes/routePaths";
import styles from "./Navbar.module.scss";

export function Navbar() {
  const dispatch = useDispatch<AppDispatch>();
  const searchQuery = useSelector(selectSearchQuery);
  const totalItems = useSelector(selectTotalItems);

  useEffect(() => {
    const trimmed = searchQuery.trim();
    if (!trimmed) {
      return;
    }

    const timer = setTimeout(() => {
      void dispatch(searchProducts(trimmed));
    }, 500);

    return () => clearTimeout(timer);
  }, [searchQuery, dispatch]);

  const handleClearSearch = () => {
    if (!searchQuery.trim()) {
      return;
    }
    dispatch(clearSearch());
    void dispatch(fetchAllProducts());
  };

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div className={styles.title}>Product Discovery</div>

        <div className={styles.search}>
          <input
            type="text"
            className={styles.searchInput}
            value={searchQuery}
            onChange={(event) => {
              const next = event.target.value;
              if (!next.trim()) {
                handleClearSearch();
                return;
              }
              dispatch(setSearchQuery(next));
            }}
            placeholder="Search products..."
          />
          {searchQuery.trim() && (
            <button
              type="button"
              className={styles.clearButton}
              onClick={handleClearSearch}
            >
              x
            </button>
          )}
        </div>

        <div className={styles.actions}>
          <nav className={styles.nav}>
            <NavLink
              to={ROUTES.HOME}
              className={({ isActive }) =>
                isActive ? `${styles.link} ${styles.active}` : styles.link
              }
            >
              Home
            </NavLink>
            <NavLink
              to={ROUTES.CART}
              className={({ isActive }) =>
                isActive ? `${styles.link} ${styles.active}` : styles.link
              }
            >
              <ShoppingCartOutlinedIcon className={styles.cartIcon} />
              Cart
              <span className={styles.badge}>{totalItems}</span>
            </NavLink>
          </nav>
        </div>
      </div>
    </header>
  );
}
