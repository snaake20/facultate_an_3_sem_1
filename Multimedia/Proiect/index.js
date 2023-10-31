
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

const canvas = document.querySelector('#canvas');
const ctx = canvas.getContext('2d');

const drawings = [];

let pivotX, pivotY;

let mouseX, mouseY;

let once = true;

const instruments = ['ellipse', 'rectangle', 'line'];

let instrument = 'ellipse';

// init ui
(() => {



})();

const showAllDrawings = () => {
  drawings.forEach((d) => {
    ctx.strokeStyle = d.stroke;
    ctx.beginPath();
    switch (d.type) {
      case 'ellipse':
        ctx.ellipse(
          d.figure.x,
          d.figure.y,
          d.figure.radiusX,
          d.figure.radiusY,
          Math.PI / 4,
          0,
          2 * Math.PI
        );
        break;
      case 'rectangle':
        ctx.rectangle(d.figure.x, d.figure.y, d.figure.width, d.figure.height);
        break;
    }
    ctx.stroke();
  });
};

const draw = () => {
  ctx.strokeStyle = document.querySelector('#color').value ?? 'black';
  ctx.beginPath();
  ctx.ellipse(
    pivotX,
    pivotY,
    Math.abs(mouseX - pivotX),
    Math.abs(mouseY - pivotY),
    Math.PI / 4,
    0,
    2 * Math.PI
  );
  ctx.stroke();
};

const preview = () => {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  showAllDrawings();
  if (pivotX && pivotY) {
    draw();
  }
};

const reset = () => {
  drawings.push({
    type: 'ellipse',
    fill: '',
    stroke: document.querySelector('#color').value ?? 'black',
    figure: new Ellipse(
      pivotX,
      pivotY,
      Math.abs(pivotX - mouseX),
      Math.abs(pivotY - mouseY)
    ),
  });
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  showAllDrawings();
  once = true;
  pivotX = pivotY = undefined;
};

canvas.addEventListener('mouseup', reset);

canvas.addEventListener('mousedown', (e) => {
  if (e.button == 0) {
    if (!once) return;
    pivotX = e.offsetX;
    pivotY = e.offsetY;
    once = false;
  }
});

canvas.addEventListener('mousemove', (e) => {
  mouseX = e.offsetX;
  mouseY = e.offsetY;
  if (!once) preview();
});


