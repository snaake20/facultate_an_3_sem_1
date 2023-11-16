function animationEndHandler(e) {
  let circles = document.querySelectorAll('.circle');
  e.currentTarget.classList.remove('animated');
  if (e.currentTarget.nextElementSibling) e.currentTarget.nextElementSibling.classList.add('animated');
  else circles[0].classList.add('animated');
}

function color(o) {
  let r = Math.floor(Math.random() * 255);
  let g = Math.floor(Math.random() * 255);
  let b = Math.floor(Math.random() * 255);
  o.style.backgroundColor = `rgb(${r}, ${g}, ${b})`;
}

function init() {
  let circles = document.querySelectorAll('.circle');
  for (const element of circles) {
    color(element);
    element.addEventListener('animationend', animationEndHandler);
  }
  circles[0].classList.add('animated');
};
init();
function deleteHandlers() {
  let circles = document.querySelectorAll('.circle');
  for (const element of circles) {
    element.classList.remove('animated');
    element.removeEventListener('animationend', animationEndHandler);
  }
}

document.querySelector('button')?.addEventListener('click', () => {
  let circle = document.createElement('div');
  circle.classList.add('circle');
  document.querySelector('.container')?.appendChild(circle);
  deleteHandlers();
  init();
});