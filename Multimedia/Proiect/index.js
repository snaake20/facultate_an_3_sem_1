// classes start
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
// classes end

// start of variables

const canvas = document.querySelector('#canvas');
const ctx = canvas.getContext('2d');

const drawings = [];

const listOfDrawings = document.querySelector('#drawings');

// start of functions needed for proxy

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
    case 'rect':
      labelDimX.textContent = 'width:';
      break;
    case 'line':
      labelDimX.textContent = 'x2:';
      break;
  }
  const inputDimX = document.createElement('input');
  inputDimX.type = 'number';
  inputDimX.min = 0;
  inputDimX.max = canvas.width;
  switch (value.type) {
    case 'ellipse':
      inputDimX.value = value.figure.radiusX;
      inputDimX.placeholder = value.figure.radiusX;
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.width) e.target.value = canvas.width;
        value.figure.radiusX = e.target.value;
        showAllDrawings();
      });
      break;
    case 'rect':
      inputDimX.value = value.figure.width;
      inputDimX.placeholder = value.figure.width;
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.width) e.target.value = canvas.width;
        value.figure.width = e.target.value;
        showAllDrawings();
      });
      break;
    case 'line':
      inputDimX.value = value.figure.x2;
      inputDimX.placeholder = value.figure.x2;
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.width) e.target.value = canvas.width;
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
    case 'rect':
      labelDimY.textContent = 'height:';
      break;
    case 'line':
      labelDimY.textContent = 'y2:';
      break;
  }
  const inputDimY = document.createElement('input');
  inputDimY.type = 'number';
  inputDimY.min = 0;
  inputDimY.max = canvas.height;
  switch (value.type) {
    case 'ellipse':
      inputDimY.value = value.figure.radiusY;
      inputDimY.placeholder = value.figure.radiusY;
      inputDimY.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.height) e.target.value = canvas.height;
        value.figure.radiusY = e.target.value;
        showAllDrawings();
      });
      break;
    case 'rect':
      inputDimY.value = value.figure.height;
      inputDimY.placeholder = value.figure.height;
      inputDimY.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.height) e.target.value = canvas.height;
        value.figure.height = e.target.value;
        showAllDrawings();
      });
      break;
    case 'line':
      inputDimY.value = value.figure.y2;
      inputDimY.placeholder = value.figure.y2;
      inputDimY.addEventListener('input', (e) => {
        if (!e.target.value) return;
        if (e.target.value < 0) e.target.value = 0;
        if (e.target.value > canvas.height) e.target.value = canvas.height;
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
  inputX.min = 0;
  inputX.max = canvas.width;
  inputX.value = value.figure.x;
  inputX.placeholder = value.figure.x;
  inputX.addEventListener('input', (e) => {
    if (!e.target.value) return;
    if (e.target.value < 0) e.target.value = 0;
    if (e.target.value > canvas.width) e.target.value = canvas.width;
    value.figure.x = e.target.value;
    showAllDrawings();
  });
  divModX.appendChild(labelInputX);
  divModX.appendChild(inputX);

  const divModY = document.createElement('div');
  const labelInputY = document.createElement('label');
  labelInputY.textContent = 'y:';
  const inputY = document.createElement('input');
  inputY.min = 0;
  inputY.max = canvas.height;
  inputY.type = 'number';
  inputY.value = value.figure.y;
  inputY.placeholder = value.figure.y;
  inputY.addEventListener('input', (e) => {
    if (!e.target.value) return;
    if (e.target.value < 0) e.target.value = 0;
    if (e.target.value > canvas.height) e.target.value = canvas.height;
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
    // appending to the list
    div.className = 'drawing';
    listOfDrawings.appendChild(div);
  }
  // default behavior
  return true;
};

// end of functions needed for proxy

const handler = {
  set: handlerSetter,
};

const proxy = new Proxy(drawings, handler); // proxy for drawings to render list of drawings

let pivotX, pivotY; // for drawing

let mouseX, mouseY; // for drawing

let inPreview = false; // for preview

let instrument = document.querySelector('#instruments').value;

let canvasColor = 'transparent'; // for transparent png (canvas color changes when you change the input color)

let saveMimeType = document.querySelector('#mime-type').value;

// end of variables

const saveToRaster = (mimeType) => {
  const link = document.createElement('a');
  let dataURL = null;
  if (canvasColor === 'transparent' && mimeType !== 'png') {
    showAllDrawings('#fff');
    dataURL = canvas.toDataURL(`image/${mimeType}`);
    showAllDrawings();
  } else {
    dataURL = canvas.toDataURL(`image/${mimeType}`);
  }
  
  link.href = dataURL;
  link.download = `canvas.${mimeType}`;
  link.click();
};

const saveToSVG = () => {
  const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
  svg.setAttribute('width', canvas.width);
  svg.setAttribute('height', canvas.height);
  svg.setAttribute('viewBox', `0 0 ${canvas.width} ${canvas.height}`);
  svg.setAttribute('xmlns', 'http://www.w3.org/2000/svg');

  const background = document.createElementNS(
    'http://www.w3.org/2000/svg',
    'rect'
  );
  background.setAttribute('width', canvas.width);
  background.setAttribute('height', canvas.height);
  background.setAttribute('fill', canvasColor);
  svg.appendChild(background);

  proxy.forEach((d) => {
    const figure = document.createElementNS(
      'http://www.w3.org/2000/svg',
      d.type
    );
    switch (d.type) {
      case 'ellipse':
        figure.setAttribute('cx', d.figure.x);
        figure.setAttribute('cy', d.figure.y);
        figure.setAttribute('rx', d.figure.radiusX);
        figure.setAttribute('ry', d.figure.radiusY);
        break;
      case 'rect':
        figure.setAttribute('x', d.figure.x);
        figure.setAttribute('y', d.figure.y);
        figure.setAttribute('width', d.figure.width);
        figure.setAttribute('height', d.figure.height);
        break;
      case 'line':
        figure.setAttribute('x1', d.figure.x);
        figure.setAttribute('y1', d.figure.y);
        figure.setAttribute('x2', d.figure.x2);
        figure.setAttribute('y2', d.figure.y2);
        break;
    }
    figure.setAttribute('stroke', d.stroke);
    figure.setAttribute('stroke-width', d.lineWidth);
    figure.setAttribute('fill', 'none');
    svg.appendChild(figure);
  });

  const dataURL = `data:image/svg+xml;base64,${btoa(
    new XMLSerializer().serializeToString(svg)
  )}`;

  const link = document.createElement('a');
  link.href = dataURL;
  link.download = `canvas.svg`;
  link.click();
};

const changeInstrument = (e) => {
  instrument = e.target.value;
};

const changeMimeType = (e) => {
  saveMimeType = e.target.value;
};

const colorCanvas = (color) => {
  ctx.fillStyle = color ?? canvasColor;
  ctx.fillRect(0, 0, canvas.width, canvas.height);
};

const showAllDrawings = (tempColor) => {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  colorCanvas(tempColor);
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
      case 'rect':
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
    case 'rect':
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
    case 'rect':
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

// start of event listeners

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

document.querySelector('#instruments').addEventListener('change', changeInstrument);

document.querySelector('#mime-type').addEventListener('input', changeMimeType);

document.querySelector('#save').addEventListener('click', () => {
  if (saveMimeType === 'svg') {
    return saveToSVG();
  }
  return saveToRaster(saveMimeType);
});

document.querySelector('#canvas-bg').addEventListener('input', (e) => {
  canvasColor = e.currentTarget.value;
  showAllDrawings();
});

// end of event listeners