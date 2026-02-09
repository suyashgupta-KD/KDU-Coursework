import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";
import { ROUTES } from "../../routes/routePaths";
import styles from "./Navbar.module.scss";

const THEME_STORAGE_KEY = "theme";

export function Navbar() {
  const [theme, setTheme] = useState<"light" | "dark">(() => {
    if (typeof window === "undefined") return "light";
    const stored = localStorage.getItem(THEME_STORAGE_KEY);
    if (stored === "light" || stored === "dark") return stored;
    return window.matchMedia("(prefers-color-scheme: dark)").matches
      ? "dark"
      : "light";
  });

  useEffect(() => {
    document.documentElement.dataset.theme = theme;
    localStorage.setItem(THEME_STORAGE_KEY, theme);
  }, [theme]);

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div className={styles.title}>Product Discovery</div>

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

          <button
            type="button"
            className={styles.darkModeToggle}
            onClick={() =>
              setTheme((current) => (current === "dark" ? "light" : "dark"))
            }
            aria-label="Toggle dark mode"
            aria-pressed={theme === "dark"}
          >
            {theme === "dark" ? (
              <LightModeIcon fontSize="small" />
            ) : (
              <DarkModeIcon fontSize="small" />
            )}
          </button>
        </div>
      </div>
    </header>
  );
}
