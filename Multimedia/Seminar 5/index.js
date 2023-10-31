const canvas = document.getElementById('myCanvas');
const ctx = canvas.getContext('2d');

const imagePath = 'b1a.gif';

const initialImage = new Image();
initialImage.src = imagePath;

let mouseX,mouseY;
let isMousePressed = false;

initialImage.onload = () => {
    ctx.fillStyle = 'black';
    ctx.fillRect(0,0,canvas.width, canvas.height);

    canvas.addEventListener('mousemove', (e) => {
        mouseX = e.clientX - canvas.getBoundingClientRect().left; // mouseX = e.offsetX
        mouseY = e.clientY - canvas.getBoundingClientRect().top; // mouseT = e.offsetY
        if (e.button === 0) {
            isMousePressed = true;
        } else isMousePressed = false;
    })
};

const drawImageAroundMouse = () => {
    ctx.drawImage(initialImage, 0,0,canvas.width, canvas.height);
    let imageData = ctx.getImageData(0,0,canvas.width, canvas.height);
    let pixels = imageData.data;
    console.log(pixels);
    for (let x = 0; x < canvas.width; x++) {
       for (let y = 0; y < canvas.height; y++) {
            let index = (y*canvas.width+x) * 4;
            // if (Math.sqrt((mouseX-x)**2 + (mouseY-y)**2) < 100) {
            //     let media = (pixels[index] + pixels[index+1] + pixels[index+2])/3;
            //     pixels[index] = pixels[index+1] = pixels[index+2] = media;
            // }
            if (Math.abs(x-mouseX) < 50 && Math.abs(y-mouseY) < 50) pixels[index+3] = 100;
       }
        
    }
    ctx.putImageData(imageData, 0, 0);
}

setInterval(drawImageAroundMouse, 30);