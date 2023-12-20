class Ellipse {
  constructor(x, y, radiusX, radiusY) {
    this.x = x;
    this.y = y;
    this.radiusX = radiusX;
    this.radiusY = radiusY;
  }
}

class Rectangle {
  constructor(x, y, width, height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
}

class Line {
  constructor(x, y, x2, y2) {
    this.x = x;
    this.y = y;
    this.x2 = x2;
    this.y2 = y2;
  }
}

const canvas = document.querySelector('#canvas');
const ctx = canvas.getContext('2d');

const drawings = [];

const listOfDrawings = document.querySelector('#drawings');

const renderMainInformation = (div, target, value) => {
  const p = document.createElement('p');
  p.textContent = value.type;
  const button = document.createElement('button');
  button.type = 'button';
  button.textContent = 'delete';
  button.addEventListener('click', () => {
    target.splice(
      target.findIndex((d) => d.id === value.id),
      1
    );
    showAllDrawings();
    div.remove();
  });
  const main = document.createElement('div');
  main.appendChild(p);
  main.appendChild(button);
  main.className = 'main';
  div.appendChild(main);
};

const getValueDimX = (value) => {
  const labelDimX = document.createElement('label');
  switch (value.type) {
    case 'ellipse':
      labelDimX.textContent = 'radiusX:';
      break;
    case 'rectangle':
      labelDimX.textContent = 'width:';
      break;
    case 'line':
      labelDimX.textContent = 'x2:';
      break;
  }
  const inputDimX = document.createElement('input');
  inputDimX.type = 'number';
  switch (value.type) {
    case 'ellipse':
      inputDimX.value = value.figure.radiusX;
      inputDimX.addEventListener('input', (e) => {
        value.figure.radiusX = e.target.value;
        showAllDrawings();
      });
      break;
    case 'rectangle':
      inputDimX.value = value.figure.width;
      inputDimX.addEventListener('input', (e) => {
        value.figure.width = e.target.value;
        showAllDrawings();
      });
      break;
    case 'line':
      inputDimX.value = value.figure.x2;
      inputDimX.addEventListener('input', (e) => {
        value.figure.x2 = e.target.value;
        showAllDrawings();
      });
      break;
  }
  return [labelDimX, inputDimX];
};

const getValueDimY = (value) => {
  const labelDimY = document.createElement('label');
  switch (value.type) {
    case 'ellipse':
      labelDimY.textContent = 'radiusY:';
      break;
    case 'rectangle':
      labelDimY.textContent = 'height:';
      break;
    case 'line':
      labelDimY.textContent = 'y2:';
      break;
  }
  const inputDimY = document.createElement('input');
  inputDimY.type = 'number';
  switch (value.type) {
    case 'ellipse':
      inputDimY.value = value.figure.radiusY;
      inputDimY.addEventListener('input', (e) => {
        value.figure.radiusY = e.target.value;
        showAllDrawings();
      });
      break;
    case 'rectangle':
      inputDimY.value = value.figure.height;
      inputDimY.addEventListener('input', (e) => {
        value.figure.height = e.target.value;
        showAllDrawings();
      });
      break;
    case 'line':
      inputDimY.value = value.figure.y2;
      inputDimY.addEventListener('input', (e) => {
        value.figure.y2 = e.target.value;
        showAllDrawings();
      });
      break;
  }
  return [labelDimY, inputDimY];
};

const renderModifiers = (div, value) => {
  const divModX = document.createElement('div');
  const labelInputX = document.createElement('label');
  labelInputX.textContent = 'x:';
  const inputX = document.createElement('input');
  inputX.type = 'number';
  inputX.value = value.figure.x;
  inputX.addEventListener('input', (e) => {
    value.figure.x = e.target.value;
    showAllDrawings();
  });
  divModX.appendChild(labelInputX);
  divModX.appendChild(inputX);

  const divModY = document.createElement('div');
  const labelInputY = document.createElement('label');
  labelInputY.textContent = 'y:';
  const inputY = document.createElement('input');
  inputY.type = 'number';
  inputY.value = value.figure.y;
  inputY.addEventListener('input', (e) => {
    value.figure.y = e.target.value;
    showAllDrawings();
  });
  divModY.appendChild(labelInputY);
  divModY.appendChild(inputY);

  const divModDimX = document.createElement('div');
  const [labelDimX, inputDimX] = getValueDimX(value);
  divModDimX.appendChild(labelDimX);
  divModDimX.appendChild(inputDimX);

  const divModDimY = document.createElement('div');
  const [labelDimY, inputDimY] = getValueDimY(value);
  divModDimY.appendChild(labelDimY);
  divModDimY.appendChild(inputDimY);

  const modifiers = document.createElement('div');
  modifiers.appendChild(divModX);
  modifiers.appendChild(divModY);
  modifiers.appendChild(divModDimX);
  modifiers.appendChild(divModDimY);
  modifiers.className = 'modifiers';
  div.appendChild(modifiers);
};

const handlerSetter = (target, prop, value) => {
  if (prop !== 'length') {
    // default behavior
    target[prop] = value;
    // adding to the list of drawings
    const div = document.createElement('div');
    // rendering main information
    renderMainInformation(div, target, value);
    // rendering modifiers
    renderModifiers(div, value);
    div.className = 'drawing';
    listOfDrawings.appendChild(div);
  }
  // returning true to indicate that the operation was successful
  return true;
};

const handler = {
  set: handlerSetter,
};

const proxy = new Proxy(drawings, handler);

let pivotX, pivotY;

let mouseX, mouseY;

let inPreview = false;

let instrument = document.querySelector('#instruments').value;

let canvasColor = 'white';

let saveMimeType = 'png';

const colorCanvas = () => {
  ctx.fillStyle = canvasColor;
  ctx.fillRect(0, 0, canvas.offsetWidth, canvas.offsetHeight);
};

const saveToRaster = (mimeType) => {
  const dataURL = canvas.toDataURL(`image/${mimeType}`);

  const link = document.createElement('a');
  link.href = dataURL;
  link.download = `canvas.${mimeType}`;
  link.click();
};

const saveToSVG = () => {};

const changeInstrument = (e) => {
  instrument = e.target.value;
};

document
  .querySelector('#instruments')
  .addEventListener('change', changeInstrument);

const changeMimeType = (e) => {
  console.log(e.target.value);
  saveMimeType = e.target.value;
};

document.querySelector('#mime-type').addEventListener('input', changeMimeType);

document.querySelector('#save').addEventListener('click', () => {
  if (saveMimeType === 'svg') {
    return saveToSVG();
  }
  return saveToRaster(saveMimeType);
});
// init ui
(() => {})();

const showAllDrawings = () => {
  ctx.clearRect(0, 0, canvas.offsetWidth, canvas.offsetHeight);
  colorCanvas();
  proxy.forEach((d) => {
    ctx.lineWidth = d.lineWidth;
    ctx.strokeStyle = d.stroke;
    ctx.beginPath();
    switch (d.type) {
      case 'ellipse':
        ctx.ellipse(
          d.figure.x,
          d.figure.y,
          d.figure.radiusX,
          d.figure.radiusY,
          0,
          0,
          2 * Math.PI
        );
        break;
      case 'rectangle':
        ctx.rect(d.figure.x, d.figure.y, d.figure.width, d.figure.height);
        break;
      case 'line':
        ctx.moveTo(d.figure.x, d.figure.y);
        ctx.lineTo(d.figure.x2, d.figure.y2);
        break;
    }
    ctx.stroke();
  });
};

const draw = () => {
  ctx.lineWidth = document.querySelector('#thickness').value ?? 1;
  const colorHex = document.querySelector('#color').value;
  const colorRGB = `rgba(${parseInt(colorHex.slice(1, 3), 16)},${parseInt(
    colorHex.slice(3, 5),
    16
  )},${parseInt(colorHex.slice(5), 16)},0.5)`;
  const dashArray = [6, 6];
  ctx.setLineDash(dashArray);
  ctx.strokeStyle = colorRGB ?? 'black';
  ctx.beginPath();
  switch (instrument) {
    case 'ellipse':
      ctx.ellipse(
        pivotX,
        pivotY,
        Math.abs(mouseX - pivotX),
        Math.abs(mouseY - pivotY),
        0,
        0,
        2 * Math.PI
      );
      break;
    case 'rectangle':
      ctx.rect(pivotX, pivotY, mouseX - pivotX, mouseY - pivotY);
      break;
    case 'line':
      ctx.moveTo(pivotX, pivotY);
      ctx.lineTo(mouseX, mouseY);
      break;
  }
  ctx.stroke();
  ctx.setLineDash([]);
};

const preview = () => {
  showAllDrawings();
  if (pivotX && pivotY) {
    draw();
  }
};

const saveFigure = () => {
  let figure = null;
  switch (instrument) {
    case 'ellipse':
      figure = new Ellipse(
        pivotX,
        pivotY,
        Math.abs(mouseX - pivotX),
        Math.abs(mouseY - pivotY)
      );
      break;
    case 'rectangle':
      figure = new Rectangle(pivotX, pivotY, mouseX - pivotX, mouseY - pivotY);
      break;
    case 'line':
      figure = new Line(pivotX, pivotY, mouseX, mouseY);
      break;
  }
  return figure;
};

const reset = (e) => {
  if (e.button != 0) return;
  // if mouseX and mouseY are the same as pivotX and pivotY, or if the width or height is 0 or not enough to see the figure, don't save
  if (pivotX === mouseX && pivotY === mouseY) {
    showAllDrawings();
    inPreview = false;
    pivotX = pivotY = undefined;
    return;
  }
  proxy.push({
    id: Math.random().toString(16).slice(2),
    type: instrument,
    lineWidth: document.querySelector('#thickness').value ?? 1,
    stroke: document.querySelector('#color').value ?? 'black',
    figure: saveFigure(),
  });
  showAllDrawings();
  inPreview = false;
  pivotX = pivotY = undefined;
};

canvas.addEventListener('mouseup', reset);

canvas.addEventListener('mousedown', (e) => {
  if (e.button != 0) return;
  if (inPreview) return;
  pivotX = e.offsetX;
  pivotY = e.offsetY;
  inPreview = true;
});

canvas.addEventListener('mousemove', (e) => {
  mouseX = e.offsetX;
  mouseY = e.offsetY;
  if (inPreview) preview();
});

document.querySelector('#canvas-bg').addEventListener('change', (e) => {
  canvasColor = e.currentTarget.value;
  showAllDrawings();
});
