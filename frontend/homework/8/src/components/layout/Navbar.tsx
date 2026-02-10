import { useEffect } from "react";
import { NavLink } from "react-router-dom";
import { ROUTES } from "../../routes/routePaths";
import { useProductContext } from "../../context/ProductContext";
import styles from "./Navbar.module.scss";

export function Navbar() {
  const { searchQuery, setSearchQuery, searchProducts, clearSearch } =
    useProductContext();

  useEffect(() => {
    const trimmed = searchQuery.trim();
    if (!trimmed) return;

    const timer = setTimeout(() => {
      searchProducts(trimmed);
    }, 500);

    return () => clearTimeout(timer);
  }, [searchQuery, searchProducts]);

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
                clearSearch();
                return;
              }
              setSearchQuery(next);
            }}
            placeholder="Search products..."
          />
          {searchQuery.trim() && (
            <button
              type="button"
              className={styles.clearButton}
              onClick={clearSearch}
            >
              X
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
          </nav>
        </div>
      </div>
    </header>
  );
}
