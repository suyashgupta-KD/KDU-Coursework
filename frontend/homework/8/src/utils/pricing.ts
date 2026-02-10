export function getDiscountedPrice(
  price: number,
  discountPercentage: number,
): number {
  if (discountPercentage <= 0) return price;

  const discounted = price * (1 - discountPercentage / 100);

  return Math.round(discounted * 100) / 100;
}

export function hasDiscount(discountPercentage: number): boolean {
  return discountPercentage > 0;
}
