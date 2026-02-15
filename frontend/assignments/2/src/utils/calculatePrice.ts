import type { BookingConfig } from "../types/BookingConfig";

type CalculatePriceInput = {
  config: BookingConfig | null;
  selectedTypeId: string;
  selectedFrequencyId: string;
  selectedExtras: string[];
  bedrooms: number;
  bathrooms: number;
};

export function calculatePrice({
  config,
  selectedTypeId,
  selectedFrequencyId,
  selectedExtras,
  bedrooms,
  bathrooms,
}: CalculatePriceInput) {
  if (!config || !selectedTypeId || !selectedFrequencyId) {
    return 0;
  }

  const basePrice =
    config.cleaningTypes.find((item) => item.id === selectedTypeId)
      ?.basePrice ?? 0;

  const frequencyMultiplier =
    config.frequencies.find((item) => item.id === selectedFrequencyId)
      ?.multiplier ?? 1;

  const bedroomPrice =
    config.roomPricing.find((item) => item.id === "BEDROOM")?.price ?? 0;
  const bathroomPrice =
    config.roomPricing.find((item) => item.id === "BATHROOM")?.price ?? 0;

  const roomsPrice = bedrooms * bedroomPrice + bathrooms * bathroomPrice;

  const extrasPrice = selectedExtras.reduce((total, extraId) => {
    const price = config.extras.find((item) => item.id === extraId)?.price ?? 0;
    return total + price;
  }, 0);

  return (basePrice + roomsPrice + extrasPrice) * frequencyMultiplier;
}
