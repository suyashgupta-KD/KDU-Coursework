import { Rating } from "@mui/material";
import StarIcon from "@mui/icons-material/Star";

interface StarRatingProps {
  value: number;
  size?: "small" | "medium" | "large";
}

export function StarRating({ value, size = "small" }: StarRatingProps) {
  return (
    <Rating
      value={value}
      precision={0.5}
      readOnly
      size={size}
      emptyIcon={<StarIcon style={{ opacity: 0.4 }} fontSize="inherit" />}
    />
  );
}
