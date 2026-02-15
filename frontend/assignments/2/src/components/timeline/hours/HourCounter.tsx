interface HourCounterProps {
  hours: number;
  increase: () => void;
  decrease: () => void;
}

export function HourCounter({ hours, increase, decrease }: HourCounterProps) {
  return (
    <div>
      <button type="button" onClick={decrease}>
        -
      </button>
      <span>{hours}</span>
      <button type="button" onClick={increase}>
        +
      </button>
    </div>
  );
}
