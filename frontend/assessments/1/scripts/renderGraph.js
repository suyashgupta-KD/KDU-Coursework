const renderBars = () => {
  let maxBars = Math.floor(dom.bars.clientWidth / config.barWidthPx);

  if (maxBars < 1) {
    maxBars = 1;
  }

  while (barHistory.length > maxBars) {
    barHistory.shift();
  }

  dom.bars.textContent = "";

  for (let i = 0; i < barHistory.length; i += 1) {
    const barData = barHistory[i];
    const bar = document.createElement("span");
    let safeHeight = barData.price;

    if (safeHeight < 0) {
      safeHeight = 0;
    }

    if (safeHeight > config.graphHeightPx) {
      safeHeight = config.graphHeightPx;
    }

    bar.className = "bar " + barData.direction;
    bar.style.height = safeHeight + "px";
    bar.style.width = config.barWidthPx + "px";

    dom.bars.appendChild(bar);
  }
};

const renderAxis = () => {
  const graphWidth = dom.graph.clientWidth;
  let maxAxisValue = Math.floor(graphWidth / config.axisStepPx) * config.axisStepPx;

  if (maxAxisValue < config.axisStepPx) {
    maxAxisValue = config.axisStepPx;
  }

  dom.axis.textContent = "";

  for (let value = 0; value <= maxAxisValue; value += config.axisStepPx) {
    const label = document.createElement("span");

    label.className = "axis-label";
    label.textContent = value;

    if (value === 0) {
      label.style.left = "0";
    } else if (value === maxAxisValue) {
      label.style.right = "0";
    } else {
      label.style.left = value + "px";
      label.style.transform = "translateX(-50%)";
    }

    dom.axis.appendChild(label);
  }
};
