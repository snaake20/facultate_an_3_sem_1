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

const listOfDrawings = document.querySelector('#drawings');

const handler = {
  set(target, prop, value) {
    if (prop !== 'length') {
      target[prop] = value;
      const p = document.createElement('p');
      p.textContent = value.type;
      const button = document.createElement('button');
      button.type = 'button';
      button.textContent = 'delete?';
      const div = document.createElement('div');
      button.addEventListener('click', () => {
        const ans = confirm('Sigur vrei sa stergi figura?');
        if (ans) {
          target.splice(
            target.findIndex((d) => d.id === value.id),
            1
          );
          showAllDrawings();
          div.remove();
          console.log(target);
        }
      });
      div.appendChild(p);
      div.appendChild(button);
      div.className = 'drawing';
      listOfDrawings.appendChild(div);
    }
    return true;
  },
};

const proxy = new Proxy(drawings, handler);

let pivotX, pivotY;

let mouseX, mouseY;

let inPreview = false;

const instruments = ['ellipse', 'rectangle', 'line'];

let instrument = document.querySelector('#instruments').value;

let canvasColor = 'transparent';

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

const saveToSVG = () => {
  const svgData = new XMLSerializer().serializeToString(canvas);

  const link = document.createElement('a');
  link.href = 'data:image/svg+xml,' + svgData;
  link.download = 'canvas.svg';
  link.click();
};

const changeInstrument = (e) => {
  instrument = e.target.value;
};

document
  .querySelector('#instruments')
  .addEventListener('change', changeInstrument);

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
