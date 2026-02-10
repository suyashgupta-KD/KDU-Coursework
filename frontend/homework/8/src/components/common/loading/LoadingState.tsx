import { GridLoader } from "react-spinners";
import styles from "./LoadingState.module.scss";

export function LoadingState() {
  return (
    <div className={styles.container}>
      <GridLoader />
    </div>
  );
}
