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
  constructor(x1, y1, x2, y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}

const canvas = document.querySelector('#canvas');
const ctx = canvas.getContext('2d');

const drawings = [];

let pivotX, pivotY;

let mouseX, mouseY;

let inPreview = false;

const instruments = ['ellipse', 'rectangle', 'line'];

let instrument = document.querySelector('#instruments').value ?? 'ellipse';

const saveToRaster = (mimeType) => {
  const dataURL = canvas.toDataURL(`image/${mimeType}`);

  const link = document.createElement('a');
  link.href = dataURL;
  link.download = `canvas.${mimeType}`;
  link.click();
};

const saveToSVG = () => {
  const svgData = new XMLSerializer().serializeToString(canvas);

  const link = document.createElement('a');
  link.href = 'data:image/svg+xml,' + svgData;
  link.download = 'canvas.svg';
  link.click();
};

const changeInstrument = (e) => {
  instrument = e.target.value;
  inPreview = false;
  pivotX = pivotY = undefined;
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  showAllDrawings();
};

document
  .querySelector('#instruments')
  .addEventListener('change', changeInstrument);

// init ui
(() => {})();

const showAllDrawings = () => {
  drawings.forEach((d) => {
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
        ctx.moveTo(d.figure.x1, d.figure.y1);
        ctx.lineTo(d.figure.x2, d.figure.y2);
        break;
    }
    ctx.stroke();
  });
};

const draw = () => {
  ctx.lineWidth = document.querySelector('#thickness').value ?? 1;
  ctx.strokeStyle = document.querySelector('#color').value ?? 'black';
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
};

const preview = () => {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
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

const reset = () => {
  drawings.push({
    type: instrument,
    fill: '',
    lineWidth: document.querySelector('#thickness').value ?? 1,
    stroke: document.querySelector('#color').value ?? 'black',
    figure: saveFigure(),
  });
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  showAllDrawings();
  inPreview = false;
  pivotX = pivotY = undefined;
};

canvas.addEventListener('mouseup', reset);

canvas.addEventListener('mousedown', (e) => {
  if (e.button == 0) {
    if (inPreview) return;
    pivotX = e.offsetX;
    pivotY = e.offsetY;
    inPreview = true;
  }
});

canvas.addEventListener('mousemove', (e) => {
  mouseX = e.offsetX;
  mouseY = e.offsetY;
  if (inPreview) preview();
});
