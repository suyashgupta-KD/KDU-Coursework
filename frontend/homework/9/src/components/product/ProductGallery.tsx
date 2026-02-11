import { useEffect, useState } from "react";
import styles from "./ProductGallery.module.scss";

interface ProductGalleryProps {
  title: string;
  images: string[];
}

export function ProductGallery({ title, images }: ProductGalleryProps) {
  const [selected, setSelected] = useState(images[0]);

  useEffect(() => {
    setSelected(images[0]);
  }, [images]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.mainImageWrap}>
        <img className={styles.mainImage} src={selected} alt={title} />
      </div>

      <div className={styles.thumbRow}>
        {images.map((url) => (
          <button
            key={url}
            type="button"
            className={url === selected ? styles.thumbActive : styles.thumb}
            onClick={() => setSelected(url)}
            aria-label={`View image for ${title}`}
          >
            <img src={url} alt={title} className={styles.thumbImg} />
          </button>
        ))}
      </div>
    </div>
  );
}
