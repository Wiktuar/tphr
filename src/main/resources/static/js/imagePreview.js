let image = document.getElementById("image");

document.getElementById('file')
    .addEventListener('change', e => {
        let files = e.target.files;
        let fr = new FileReader();
        fr.onloadend = function() {
            image.src = fr.result;
            modalWindow.open();
            setTimeout(() =>{
                init();
            }, 400 );
        };
        fr.readAsDataURL(files[0]);
    });

//холст и контекст для отображения выделенного фрагмента.
let myCanvas = document.getElementById("myCanvas");
let myCtx = myCanvas.getContext('2d');

let canvas = document.getElementById('canvas'),
    ctx = canvas.getContext('2d'),
    rect = {},
    drag = false,
    mouseX,
    mouseY,
    closeEnough = 4,
    dragTL = dragBL = dragTR = dragBR = false,
    mDown = false;

// константа для корректного отображения полей модального окна. Их
// offset добавляется к полям  canvas
const vModal = document.querySelector(".modal-window");

//функция инициализации холста
function init() {
    canvas.width = image.width;
    canvas.height = image.height;

    canvas.setAttribute("style", "top: " + (image.offsetTop + 2) + "px; left: " + (image.offsetLeft + 2) + "px;");
    rect = {
        startX: 80,
        startY: 80,
        w: 150,
        h: 150
    }

    draw();
    canvas.addEventListener('mousedown', mouseDown, false);
    canvas.addEventListener('mouseup', mouseUp, false);
    canvas.addEventListener('mousemove', mouseMove, false);
    canvas.addEventListener('mouseout', mouseOut, false);
}

function mouseDown(e) {
    mouseX = e.pageX - (this.offsetLeft + vModal.offsetLeft);
    mouseY = e.pageY - (this.offsetTop + 100);

    // 4 cases:
    // 1. top left
    if (checkCloseEnough(mouseX, rect.startX) && checkCloseEnough(mouseY, rect.startY)) {
        dragTL = true;
        mDown = false;
    }
    // 2. top right
    else if (checkCloseEnough(mouseX, rect.startX + rect.w) && checkCloseEnough(mouseY, rect.startY)) {
        dragTR = true;
        mDown = false;
    }
    // 3. bottom left
    else if (checkCloseEnough(mouseX, rect.startX) && checkCloseEnough(mouseY, rect.startY + rect.h)) {
        dragBL = true;
        mDown = false;
    }
    // 4. bottom right
    else if (checkCloseEnough(mouseX, rect.startX + rect.w) && checkCloseEnough(mouseY, rect.startY + rect.h)) {
        dragBR = true;
        mDown = false;
    } else if(mouseX > rect.startX && mouseX < rect.startX + rect.w &&
        mouseY > rect.startY && mouseY < rect.startY + rect.h){
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
    mouseX = e.pageX - (this.offsetLeft + vModal.offsetLeft);
    mouseY = e.pageY - (this.offsetTop + 100);

    if(mouseX < rect.startX + closeEnough && mouseX > rect.startX - closeEnough &&
        mouseY < rect.startY + closeEnough && mouseY > rect.startY - closeEnough
    ){
        canvas.style.cursor = "nw-resize";
    } else if (mouseX < rect.startX + rect.w + closeEnough && mouseX > rect.startX + rect.w - closeEnough &&
        mouseY < rect.startY + closeEnough && mouseY > rect.startY - closeEnough){
        canvas.style.cursor = "ne-resize";
    } else if (mouseX < rect.startX + closeEnough && mouseX > rect.startX - closeEnough &&
        mouseY < rect.startY + rect.h + closeEnough && mouseY > rect.startY + rect.h - closeEnough){
        canvas.style.cursor = "ne-resize";
    } else if (mouseX < rect.startX + rect.w + closeEnough && mouseX > rect.startX + rect.w - closeEnough &&
        mouseY < rect.startY + rect.h + closeEnough && mouseY > rect.startY + rect.h - closeEnough){
        canvas.style.cursor = "nw-resize";
    } else if(mouseX > rect.startX && mouseX < rect.startX + rect.w &&
        mouseY > rect.startY && mouseY < rect.startY + rect.h){
        canvas.style.cursor = "move";
    }else {
        canvas.style.cursor = "auto";
    }


    if (dragTL) {
        rect.w += rect.startX - mouseX;
        rect.h = rect.w;
        rect.startX = mouseX;
        rect.startY = mouseY;
    } else if (dragTR) {
        rect.w = Math.abs(rect.startX - mouseX);
        rect.h = rect.w;
        rect.startY = mouseY;
    } else if (dragBL) {
        rect.w += rect.startX - mouseX;
        rect.h = rect.w;
        rect.startX = mouseX;
    } else if (dragBR) {
        rect.w = Math.abs(rect.startX - mouseX);
        rect.h = rect.w;
    } else if (mDown) {
        rect.startX = mouseX - rect.w / 2;
        rect.startY = mouseY - rect.h / 2;

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
    //переменные с новыми рассчетами в проценах.
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
        200, 200);
}

