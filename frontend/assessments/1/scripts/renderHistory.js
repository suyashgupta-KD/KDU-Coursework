const renderHistory = () => {
  if (tradeHistory.length === 0) {
    dom.list.innerHTML = '<li class="empty">No trades yet</li>';
    return;
  }

  dom.list.innerHTML = "";

  for (let i = 0; i < tradeHistory.length; i += 1) {
    const trade = tradeHistory[i];
    const typeText = trade.type === "buy" ? "Buy" : "Sell";
    const card = document.createElement("li");

    card.className = "card";
    card.innerHTML =
      '<div class="card-top">' +
      '<p class="card-qty">' +
      trade.quantity +
      " stocks</p>" +
      '<span class="tag ' +
      trade.type +
      '">' +
      typeText +
      "</span>" +
      "</div>" +
      '<p class="card-meta">' +
      formatTradeTime(trade.time) +
      "</p>";

    dom.list.appendChild(card);
  }
};
