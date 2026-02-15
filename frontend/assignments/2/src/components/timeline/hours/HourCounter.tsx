interface HourCounterProps {
  hours: number;
  onIncrease: () => void;
  onDecrease: () => void;
}
export function HourCounter({ hours }: HourCounterProps) {
  return (
    <div>
      <button>-</button>
      <span>{hours}</span>
      <button>+</button>
    </div>
  );
}
