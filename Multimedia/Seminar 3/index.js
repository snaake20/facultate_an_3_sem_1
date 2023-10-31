let counter = document.getElementById('counter');
function increment() {
  counter.textContent = +counter.textContent + 1;
}
function decrement() {
  counter.textContent = +counter.textContent - 1;
}
let table = document.getElementById('table');
function getLastLineNumber() {
  let lastLine = table.querySelectorAll('tr')[table.querySelectorAll('tr').length - 1];
  let firstCol = lastLine.querySelector('td');
  return firstCol.textContent.split(".")[0];
}
function addLineTable() {
  let line = document.createElement('tr');
  let nextNumber = +(getLastLineNumber()) + 1;
  for (let index = 1; index <= 3; index++) {
    let column = document.createElement('td');
    column.textContent = nextNumber + '.' + index;
    line.appendChild(column);
  }
  table.appendChild(line);
}