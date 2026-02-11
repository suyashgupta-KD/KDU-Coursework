import { Outlet } from "react-router-dom";
import { Navbar } from "../components/layout/Navbar";
import styles from "./MainLayout.module.scss";

export function MainLayout() {
  return (
    <div className={styles.container}>
      <Navbar />
      <main className={styles.main}>
        <Outlet />
      </main>
    </div>
  );
}
