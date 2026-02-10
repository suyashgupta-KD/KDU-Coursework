import { useMemo, useState } from "react";
import styles from "./ProductGallery.module.scss";

interface ProductGalleryProps {
  title: string;
  images: string[];
}

export function ProductGallery({ title, images }: ProductGalleryProps) {
  // Ensure we always have at least one image to show
  const allImages = useMemo(() => {
    const merged = [...images].filter(Boolean);
    // remove duplicates while preserving order
    return Array.from(new Set(merged));
  }, [images]);

  const [selected, setSelected] = useState<string>(allImages[0]);

  // If props change (rare here), keep selected valid
  const selectedSafe = allImages.includes(selected) ? selected : allImages[0];

  const hasThumbnails = allImages.length > 1;

  return (
    <div className={styles.wrapper}>
      <div className={styles.mainImageWrap}>
        <img className={styles.mainImage} src={selectedSafe} alt={title} />
      </div>

      {hasThumbnails && (
        <div className={styles.thumbRow}>
          {allImages.map((url) => {
            const isActive = url === selectedSafe;

            return (
              <button
                key={url}
                type="button"
                className={isActive ? styles.thumbActive : styles.thumb}
                onClick={() => setSelected(url)}
                aria-label={`View image for ${title}`}
              >
                <img src={url} alt={title} className={styles.thumbImg} />
              </button>
            );
          })}
        </div>
      )}
    </div>
  );
}
