const image = document.getElementById("image");
const pa = document.querySelector(".previewAvatar");
const vw = document.querySelector(".modal-window");
const styles = window.getComputedStyle(vw);

//холст и контекст для отображения выделенного фрагмента.
let myCanvas = document.getElementById("myCanvas");
let myCtx = myCanvas.getContext('2d');

document.getElementById('file')
    .addEventListener('change', e => {
        let files = e.target.files;
        let fr = new FileReader();
        fr.onload = function({target}) {
            let image1 = new Image();
            image1.onload = function() {
                if(image1.width > image1.height){
                    image.style.maxHeight = '70%';
                }
                image.src = image1.src;
                modalWindow.open();
                setTimeout(() =>{
                    init();
                }, 2000 );
            };

            image1.src = target.result;
        };
        fr.readAsDataURL(files[0]);
    });


let canvas = document.getElementById('canvas'),
    ctx = canvas.getContext('2d'),
    rect = {},
    drag = false,
    mouseX,
    mouseY,
    closeEnough = 4;
    dragTL = dragBL = dragTR = dragBR = false,
    mDown = false;

    let canvasWidth, canvasHeight = 0;

// константа для корректного отображения полей модального окна. Их
// offset добавляется к полям  canvas
const vModal = document.querySelector(".modal-window");

//функция инициализации холста
function init() {
    canvas.width = image.width;
    canvas.height = image.height;

    canvas.setAttribute("style", "top: " + (image.offsetTop + 2) + "px; left: " + (image.offsetLeft + 2) + "px;");
    rect = {
        startX: 30,
        startY: 30,
        w: 100,
        h: 100
    }

    if(Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
        styles.transform.lastIndexOf(')'))) < 100){
        canvasWidth = '150';
        canvasHeight = '150';
    } else {
        canvasWidth = '300';
        canvasHeight = '300';
    }

    myCanvas.width = canvasWidth;
    myCanvas.height = canvasHeight;

    closeEnough = (Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
        styles.transform.lastIndexOf(')'))) < 100) ? 10 : 4;

    draw();
    canvas.addEventListener('mousedown', mouseDown, false);
    canvas.addEventListener('touchstart', mouseDown, false);
    canvas.addEventListener('mouseup', mouseUp, false);
    canvas.addEventListener('touchend', mouseUp, false);
    canvas.addEventListener('mousemove', mouseMove, false);
    canvas.addEventListener('touchmove', mouseMove, false);
    canvas.addEventListener('mouseout', mouseOut, false);
}

function mouseDown(e) {
    let X = 0;
    let Y = 0;
    if(e.type === 'touchstart'){
        // e.preventDefault()
        X = Math.floor(e.touches[0].clientX - (this.offsetLeft + vModal.offsetLeft));
        Y = Math.round(e.touches[0].clientY - Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
            styles.transform.lastIndexOf(')'))) - pa.offsetTop);
    }

    if(e.type === 'mousedown'){
        X = e.pageX - (this.offsetLeft + vModal.offsetLeft);
        Y = Math.ceil(e.clientY - Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
            styles.transform.lastIndexOf(')'))) - pa.offsetTop);
    }

    // 4 cases:
    // 1. top left
    if (checkCloseEnough(X, rect.startX) && checkCloseEnough(Y, rect.startY)) {
        dragTL = true;
        mDown = false;
    }
    // 2. top right
    else if (checkCloseEnough(X, rect.startX + rect.w) && checkCloseEnough(Y, rect.startY)) {
        dragTR = true;
        mDown = false;
    }
    // 3. bottom left
    else if (checkCloseEnough(X, rect.startX) && checkCloseEnough(Y, rect.startY + rect.h)) {
        dragBL = true;
        mDown = false;
    }
    // 4. bottom right
    else if (checkCloseEnough(X, rect.startX + rect.w) && checkCloseEnough(Y, rect.startY + rect.h)) {
        dragBR = true;
        mDown = false;
    } else if(X > rect.startX && X < rect.startX + rect.w &&
        Y > rect.startY && Y < rect.startY + rect.h){
        mDown = true;
    }
    // (5.) none of them
    else {
        // handle not resizing
    }

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    draw();

}

function checkCloseEnough(p1, p2) {
    return Math.abs(p1 - p2) < closeEnough;
}

function mouseUp() {
    dragTL = dragTR = dragBL = dragBR = mDown = false;
}

function mouseOut(){
    dragTL = dragTR = dragBL = dragBR = mDown = false;
}

function mouseMove(e) {
    let X = 0;
    let Y = 0;
    if(e.type === 'touchmove'){
        e.preventDefault();
        X = Math.floor(e.touches[0].clientX - (this.offsetLeft + vModal.offsetLeft));
        Y = Math.round(e.touches[0].clientY - Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
            styles.transform.lastIndexOf(')'))) - pa.offsetTop);
    }

    if(e.type === 'mousemove'){
        X = e.pageX - (this.offsetLeft + vModal.offsetLeft);
        Y = Math.ceil(e.clientY - Number(styles.transform.substring(styles.transform.lastIndexOf(',')+1,
            styles.transform.lastIndexOf(')'))) - pa.offsetTop);
    }

    if(e.type === "mousemove"){
        if(X < rect.startX + closeEnough && X > rect.startX - closeEnough &&
            Y < rect.startY + closeEnough && Y > rect.startY - closeEnough
        ){
            canvas.style.cursor = "nw-resize";
        } else if (X < rect.startX + rect.w + closeEnough && X > rect.startX + rect.w - closeEnough &&
            Y < rect.startY + closeEnough && Y > rect.startY - closeEnough){
            canvas.style.cursor = "ne-resize";
        } else if (X < rect.startX + closeEnough && X > rect.startX - closeEnough &&
            Y < rect.startY + rect.h + closeEnough && Y > rect.startY + rect.h - closeEnough){
            canvas.style.cursor = "ne-resize";
        } else if (X < rect.startX + rect.w + closeEnough && X > rect.startX + rect.w - closeEnough &&
            Y < rect.startY + rect.h + closeEnough && Y > rect.startY + rect.h - closeEnough){
            canvas.style.cursor = "nw-resize";
        } else if(X > rect.startX && X < rect.startX + rect.w &&
            Y > rect.startY && Y < rect.startY + rect.h){
            canvas.style.cursor = "move";
        }else {
            canvas.style.cursor = "auto";
        }
    }

    if (dragTL) {
        rect.w += rect.startX - X;
        rect.h = rect.w;
        rect.startX = X;
        rect.startY = Y;
    } else if (dragTR) {
        rect.w = Math.abs(rect.startX - X);
        rect.h = rect.w;
        rect.startY = Y;
    } else if (dragBL) {
        rect.w += rect.startX - X;
        rect.h = rect.w;
        rect.startX = X;
    } else if (dragBR) {
        rect.w = Math.abs(rect.startX - X);
        rect.h = rect.w;
    } else if (mDown) {
        rect.startX = X - rect.w / 2;
        rect.startY = Y - rect.h / 2;

        if(rect.startX <= 0) {
            rect.startX = 0;
        }

        if(rect.startY <= 0) {
            rect.startY = 0;
        }

        if(rect.startX >= canvas.width - rect.w) {
            rect.startX = canvas.width - rect.w;
        }

        if(rect.startY >= canvas.height - rect.h) {
            rect.startY = canvas.height - rect.h;
        }
    }
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    draw();
}

function draw() {
    drawSelection();
    ctx.clearRect(rect.startX, rect.startY, rect.w, rect.h);
    drawHandles();
    drawImage(image, rect);
}

function drawCircle(x, y, radius) {
    ctx.fillStyle = "#FF0000";
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, 2 * Math.PI);
    ctx.fill();
}

function drawHandles() {
    drawCircle(rect.startX, rect.startY, closeEnough);
    drawCircle(rect.startX + rect.w, rect.startY, closeEnough);
    drawCircle(rect.startX + rect.w, rect.startY + rect.h, closeEnough);
    drawCircle(rect.startX, rect.startY + rect.h, closeEnough);
}

function drawSelection(){
    ctx.fillStyle = "rgba(0, 0, 0, 0.4)";
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillRect(0, 0, canvas.width, canvas.height);
}

function drawImage(image, rect){
    //переменные с новыми расчетами в проценnах.
    // ширина по оси Х от нуля до начала прямоугольной области делится на ширину холста и умножатся на реальную ширину картинки
    let cropLeft = (rect.startX / canvas.width) * image.naturalWidth;
    // ширина по оси Y от нуля до начала прямоугольной области делится на высоту холста и умножатся на реальную высоту картинки
    let cropTop = (rect.startY / canvas.height) * image.naturalHeight;
    // ширина  прямоугольника по оси Х делится на ширину холста и умножатся на реальную ширину картинки
    let cropWidth = (rect.w / canvas.width) * image.naturalWidth;
    // высота прямоугольной области по оси  Y  делится на высоту холста и умножатся на реальную высотуу картинки
    let cropHeight = (rect.h / canvas.height) * image.naturalHeight;

    // функция отрисовывает на новом холсте изображение по получившимся размерам
    myCtx.drawImage(image,
        cropLeft, cropTop,
        cropWidth, cropHeight,
        0, 0,
        canvasWidth, canvasHeight);
}


