const renderPricePanel = () => {
  dom.price.textContent = currentPrice.toFixed(2);
  dom.arrow.textContent = getArrowFromDirection(direction);
  dom.percent.textContent = getPercentChange(previousPrice, currentPrice) + "%";

  dom.arrow.className = "arrow " + direction;
  dom.percent.className = "percent " + direction;
};
