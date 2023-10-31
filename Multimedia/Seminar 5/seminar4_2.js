let xPoz = 10
let yPoz = 10
let dimensiune = 20
let pasX = 20
let pasY = 25
let raza = 10;

let canvas = document.getElementById("myCanvas");
let w = canvas.width;
let h = canvas.height;

function deseneaza() {
    let context = canvas.getContext("2d");

    context.clearRect(0,0,600,600)

    context.fillStyle = "red";
    context.beginPath();
    context.arc(xPoz,yPoz,raza,0, Math.PI*2);
    context.fill();
    context.closePath();
    xPoz +=  pasX;
    if(xPoz > w - dimensiune || xPoz - dimensiune < 0)
    {
        pasX *= -1;
        
    }
    yPoz +=  pasY;
    if(yPoz > h - dimensiune || yPoz - dimensiune < 0)
    {
        pasY *= -1; 
    }

}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }

const fn = () => {
    let context = canvas.getContext("2d");
    let imageData = context.getImageData(0,0,canvas.width, canvas.height);
    let pixels = imageData.data;
    for (let x = 0; x < canvas.width; x++) {
       for (let y = 0; y < canvas.height; y++) {
            let index = (y*canvas.width+x) * 4;
            if (x==xPoz && y==yPoz) {
                pixels[index] = getRandomInt(100);
                pixels[index+1] = getRandomInt(100);
                pixels[index=2] = getRandomInt(100);
            }
       }
        
    }

    context.putImageData(imageData, 0, 0);

}

deseneaza();

setInterval(deseneaza,50)

setInterval(fn, 10)