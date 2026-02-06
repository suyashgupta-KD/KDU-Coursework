let previousPrice = config.initialPrice;
let currentPrice = config.initialPrice;
let direction = "up";
let barHistory = [];
let tradeHistory = [];

const addCurrentBar = () => {
  barHistory.push({
    price: currentPrice,
    direction: direction,
  });
};

const addTrade = (type) => {
  const quantity = Number.parseInt(dom.qty.value, 10);

  if (!Number.isFinite(quantity) || quantity < 1) {
    return;
  }

  tradeHistory.unshift({
    type: type,
    quantity: quantity,
    time: Date.now(),
  });

  dom.qty.value = "";
  renderHistory();
};

const updateMarket = () => {
  previousPrice = currentPrice;
  currentPrice = getRandomPrice();
  direction = getDirectionFromPrices(previousPrice, currentPrice);

  addCurrentBar();
  renderPricePanel();
  renderBars();
};

dom.buy.addEventListener("click", () => {
  addTrade("buy");
});

dom.sell.addEventListener("click", () => {
  addTrade("sell");
});

addCurrentBar();
renderPricePanel();
renderAxis();
renderBars();
renderHistory();

setInterval(() => {
  updateMarket();
}, config.priceUpdateIntervalMs);
