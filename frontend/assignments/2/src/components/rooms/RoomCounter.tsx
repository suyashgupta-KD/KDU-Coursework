import bathroomImage from "../../assets/bathroom.png";
import bedroomImage from "../../assets/bedroom.png";

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
    <div>
      <img src={imageSrc} alt={label} />
      <strong>{label}</strong>
      <div>
        <button type="button" onClick={onDecrease}>
          -
        </button>
        <span>{count}</span>
        <button type="button" onClick={onIncrease}>
          +
        </button>
      </div>
    </div>
  );
}
