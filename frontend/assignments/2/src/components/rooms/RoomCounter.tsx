import bathroomImage from "../../assets/bathroom.png";
import bedroomImage from "../../assets/bedroom.png";
import styles from "./RoomCounter.module.scss";

interface RoomCounterProps {
  id: "bedroom" | "bathroom";
  label: string;
  count: number;
  onIncrease: () => void;
  onDecrease: () => void;
}

export function RoomCounter({
  id,
  label,
  count,
  onIncrease,
  onDecrease,
}: RoomCounterProps) {
  const imageSrc = id === "bedroom" ? bedroomImage : bathroomImage;

  return (
    <div className={styles.room} id={id}>
      <img src={imageSrc} alt={label} className={styles.logo} />
      <span className={styles.label}>{label}</span>
      <div className={styles.counter}>
        <button type="button" onClick={onDecrease} className={styles.button}>
          -
        </button>
        <span className={styles.count}>{count}</span>
        <button type="button" onClick={onIncrease} className={styles.button}>
          +
        </button>
      </div>
    </div>
  );
}
