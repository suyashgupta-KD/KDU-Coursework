const getRandomPrice = () => {
  return Math.floor(Math.random() * (config.maxPrice - config.minPrice + 1)) + config.minPrice;
};

const getDirectionFromPrices = (previousPrice, currentPrice) => {
  return currentPrice < previousPrice ? "down" : "up";
};

const getArrowFromDirection = (direction) => {
  return direction === "down" ? "↓" : "↑";
};

const getPercentChange = (previousPrice, currentPrice) => {
  if (previousPrice === 0) {
    return "0.00";
  }

  const change = Math.abs(((currentPrice - previousPrice) / previousPrice) * 100);
  return change.toFixed(2);
};

const formatTradeTime = (time) => {
  return new Date(time).toUTCString();
};
