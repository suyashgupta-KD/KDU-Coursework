interface RoomCounterProps {
  label: string;
  count: number;
  onIncrease: () => void;
  onDecrease: () => void;
}

export function RoomCounter({
  label,
  count,
  onIncrease,
  onDecrease,
}: RoomCounterProps) {
  return (
    <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
      <strong>{label}</strong>
      <button type="button" onClick={onDecrease}>
        -
      </button>
      <span>{count}</span>
      <button type="button" onClick={onIncrease}>
        +
      </button>
    </div>
  );
}
