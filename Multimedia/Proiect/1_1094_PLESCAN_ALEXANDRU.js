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

const canvas = document.querySelector('#canvas'); // canvas element
const ctx = canvas.getContext('2d'); // canvas context

const drawings = []; // array of drawings

const listOfDrawings = document.querySelector('#drawings'); // list of editable drawings 

// start of functions needed for proxy

// rendering main information such as the drawing type and the delete button
const renderMainInformation = (div, target, value) => {
  const p = document.createElement('p');
  p.textContent = value.type;
  const button = document.createElement('button');
  button.type = 'button';
  button.textContent = 'delete';
  // function for deleting the drawing (from the array of drawings, editable list and from the canvas)
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

// returns the label and the input for the dimension of the drawing based on the type of the drawing
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
      // event listener for changing the dimension of the drawing, redrawing drawing after changing the dimension
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return; // if the input is empty, do nothing
        if (e.target.value < 0) e.target.value = 0; // if the input is less than 0, set it to 0
        if (e.target.value > canvas.width) e.target.value = canvas.width; // if the input is greater than the width of the canvas, set it to the width of the canvas
        value.figure.radiusX = e.target.value;
        showAllDrawings();
      });
      break;
    case 'rect':
      inputDimX.value = value.figure.width;
      inputDimX.placeholder = value.figure.width;
      // event listener for changing the dimension of the drawing, redrawing drawing after changing the dimension
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return; // if the input is empty, do nothing
        if (e.target.value < 0) e.target.value = 0; // if the input is less than 0, set it to 0
        if (e.target.value > canvas.width) e.target.value = canvas.width; // if the input is greater than the width of the canvas, set it to the width of the canvas
        value.figure.width = e.target.value;
        showAllDrawings();
      });
      break;
    case 'line':
      inputDimX.value = value.figure.x2;
      inputDimX.placeholder = value.figure.x2;
      // event listener for changing the dimension of the drawing, redrawing drawing after changing the dimension
      inputDimX.addEventListener('input', (e) => {
        if (!e.target.value) return; // if the input is empty, do nothing
        if (e.target.value < 0) e.target.value = 0; // if the input is less than 0, set it to 0
        if (e.target.value > canvas.width) e.target.value = canvas.width; // if the input is greater than the width of the canvas, set it to the width of the canvas
        value.figure.x2 = e.target.value;
        showAllDrawings();
      });
      break;
  }
  return [labelDimX, inputDimX];
};

// same as getValueDimX, but for the Y dimension
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
      // same as in getValueDimX, same guard clauses
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
      // same as in getValueDimX, same guard clauses
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
      // same as in getValueDimX, same guard clauses
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

// rendering modifiers such as the position of the drawing and the dimensions of the drawing
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
  // event listener for changing the position of the drawing, redrawing drawing after changing the position
  inputX.addEventListener('input', (e) => {
    if (!e.target.value) return; // if the input is empty, do nothing
    if (e.target.value < 0) e.target.value = 0; // if the input is less than 0, set it to 0
    if (e.target.value > canvas.width) e.target.value = canvas.width; // if the input is greater than the width of the canvas, set it to the width of the canvas
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
  // same as for inputX, same guard clauses
  inputY.addEventListener('input', (e) => {
    if (!e.target.value) return;
    if (e.target.value < 0) e.target.value = 0;
    if (e.target.value > canvas.height) e.target.value = canvas.height;
    value.figure.y = e.target.value;
    showAllDrawings();
  });
  divModY.appendChild(labelInputY);
  divModY.appendChild(inputY);

  // rendering modifiers for the dimensions of the drawing (X axis)
  const divModDimX = document.createElement('div');
  const [labelDimX, inputDimX] = getValueDimX(value);
  divModDimX.appendChild(labelDimX);
  divModDimX.appendChild(inputDimX);

  // rendering modifiers for the dimensions of the drawing (Y axis)
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
  // if the property is not length, instead the new element in the array of drawings, render the drawing in the list of drawings
  if (prop !== 'length') {
    // default behavior of setting the new element in the array of drawings
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

const proxy = new Proxy(drawings, handler); // proxy for drawings to render list of drawings based on changes on the array of drawings

let pivotX, pivotY; // for drawing

let mouseX, mouseY; // for drawing

let inPreview = false; // for preview

let instrument = document.querySelector('#instruments').value; // instrument for drawing (ellipse, rectangle, line)

let canvasColor = '#fff'; // background color of the canvas

let isTransparent = false; // if the canvas bg should be transparent (for saving) available only on png and svg

let saveMimeType = document.querySelector('#mime-type').value; // mime type for saving (png, jpeg, bmp, svg)

// end of variables

// start of functions

// function for saving to raster (png, jpeg, bmp)
const saveToRaster = (mimeType) => {
  const link = document.createElement('a');
  let dataURL = null;
  // if the canvas bg should be transparent, redraw with transparent bg, get Data URL and redraw with the original bg
  if (isTransparent) {
    showAllDrawings('transparent');
    dataURL = canvas.toDataURL(`image/${mimeType}`);
    showAllDrawings();
  } else { // if the canvas bg should not be transparent, get Data URL, (default behaviour)
    dataURL = canvas.toDataURL(`image/${mimeType}`);
  }

  link.href = dataURL;
  link.download = `canvas.${mimeType}`;
  link.click();
};

// function for saving to svg
const saveToSVG = () => {
  // creating the svg document element
  const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
  svg.setAttribute('width', canvas.width);
  svg.setAttribute('height', canvas.height);
  svg.setAttribute('viewBox', `0 0 ${canvas.width} ${canvas.height}`);
  svg.setAttribute('xmlns', 'http://www.w3.org/2000/svg');
  // if the canvas bg should not be transparent, create a background for the svg
  if (!isTransparent) {
    const background = document.createElementNS(
      'http://www.w3.org/2000/svg',
      'rect'
    );
    background.setAttribute('width', canvas.width);
    background.setAttribute('height', canvas.height);
    background.setAttribute('fill', canvasColor);
    svg.appendChild(background);
  }
  // creating the svg elements based on the drawings
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
  // searched on MDN https://developer.mozilla.org/en-US/docs/Web/API/XMLSerializer, basically serializing the svg element to a string and then encoding it to base64
  const dataURL = `data:image/svg+xml;base64,${btoa(
    new XMLSerializer().serializeToString(svg) 
  )}`;

  const link = document.createElement('a');
  link.href = dataURL;
  link.download = `canvas.svg`;
  link.click();
};

// changing the instrument for drawing
const changeInstrument = (e) => {
  instrument = e.target.value;
};

// changing the mime type for saving
const changeMimeType = (e) => {
  // if the mime type is jpeg or bmp, disable the transparent bg option
  if (['jpeg', 'bmp'].includes(e.target.value)) {
    document.querySelector('#is-transparent').disabled = true;
  } else { // if the mime type is png or svg, enable the transparent bg option
    document.querySelector('#is-transparent').disabled = false;
  }
  saveMimeType = e.target.value;
};

// function for coloring the canvas with an optional parameter for the color (used when changing to transparent bg)
const colorCanvas = (color) => {
  ctx.fillStyle = color ?? canvasColor;
  ctx.fillRect(0, 0, canvas.width, canvas.height);
};

// function for showing all drawings from the array of drawings with an optional parameter for the color (used when changing to transparent bg)
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

// function for drawing the preview of the drawing
const draw = () => {
  ctx.lineWidth = document.querySelector('#thickness').value ?? 1;
  const colorHex = document.querySelector('#color').value;
  // converting the hex color to rgb color for transparency
  const colorRGB = `rgba(${parseInt(colorHex.slice(1, 3), 16)},${parseInt(
    colorHex.slice(3, 5),
    16
  )},${parseInt(colorHex.slice(5), 16)},0.5)`;
  // setting the dash array for the preview
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
  // resetting the dash array (don't want drawings to be dashed)
  ctx.setLineDash([]);
};

// function for drawing the preview of the drawing
const preview = () => {
  showAllDrawings();
  if (pivotX && pivotY) {
    draw();
  }
};

// function for saving the figure based on the instrument, using the pivot and the mouse position
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
      figure = new Rectangle(
        Math.min(pivotX, mouseX),
        Math.min(pivotY, mouseY),
        Math.abs(mouseX - pivotX),
        Math.abs(mouseY - pivotY)
      );
      break;
    case 'line':
      figure = new Line(pivotX, pivotY, mouseX, mouseY);
      break;
  }
  return figure;
};

// function for resetting the pivot and the preview and pushing the new drawing to the array of drawings
const reset = (e) => {
  // guard clause for right click
  if (e.button != 0) return;
  // guard clause when clicking but not moving the mouse (drawing a point)
  if (pivotX === mouseX && pivotY === mouseY) {
    showAllDrawings();
    inPreview = false;
    pivotX = pivotY = undefined;
    return;
  }
  proxy.push({
    id: Math.random().toString(16).slice(2), // random id for the drawing (for deleting)
    type: instrument,
    lineWidth: document.querySelector('#thickness').value ?? 1,
    stroke: document.querySelector('#color').value ?? 'black',
    figure: saveFigure(),
  });
  showAllDrawings();
  inPreview = false;
  pivotX = pivotY = undefined;
};

// end of functions

// start of event listeners

// event listener for ending the preview and saving the drawing
canvas.addEventListener('mouseup', reset);

// event listener for start of the preview
canvas.addEventListener('mousedown', (e) => {
  // guard clause for right click
  if (e.button != 0) return;
  // guard clause for preview (drawing in progress) shouldn't change the start of the drawing (pivotX, pivotY)
  if (inPreview) return;
  pivotX = e.offsetX;
  pivotY = e.offsetY;
  inPreview = true;
});

// event listener for preview
canvas.addEventListener('mousemove', (e) => { // this function changes the values of mouseX and mouseY for the preview
  mouseX = e.offsetX;
  mouseY = e.offsetY;
  if (inPreview) preview();
});

// event listener for changing the instrument
document.querySelector('#instruments').addEventListener('change', changeInstrument);

// event listener for changing the mime type
document.querySelector('#mime-type').addEventListener('input', changeMimeType);

// event listener for having a transparent background when saving
document.querySelector('#is-transparent').addEventListener('change', (e) => {
  // disabling the mime types that don't support transparency
  document.querySelector('#mime-jpeg').disabled = e.target.checked;
  document.querySelector('#mime-bmp').disabled = e.target.checked;
  isTransparent = e.target.checked;
});

// event listener for saving the canvas to raster or svg
document.querySelector('#save').addEventListener('click', () => {
  if (saveMimeType === 'svg') {
    return saveToSVG();
  }
  return saveToRaster(saveMimeType);
});

// event listener for changing the background color of the canvas
document.querySelector('#canvas-bg').addEventListener('input', (e) => {
  canvasColor = e.currentTarget.value;
  showAllDrawings();
});

// end of event listeners
