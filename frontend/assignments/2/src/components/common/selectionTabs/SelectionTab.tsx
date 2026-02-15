import styles from "./SelectionTab.module.scss";

interface SelectionTabProps {
  label: string;
  value: string;
  isSelected?: boolean;
  onSelect?: (value: string) => void;
  disabled?: boolean;
}

function SelectionTab({
  label,
  value,
  isSelected = false,
  onSelect,
  disabled = false,
}: SelectionTabProps) {
  const className = isSelected
    ? `${styles.selectionTab} ${styles.selected}`
    : styles.selectionTab;

  return (
    <button
      type="button"
      className={className}
      onClick={() => onSelect?.(value)}
      disabled={disabled}
    >
      {label}
    </button>
  );
}

export default SelectionTab;
