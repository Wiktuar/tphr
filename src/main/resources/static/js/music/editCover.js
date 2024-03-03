export const addCanvas = function(){
    document.getElementById('cover_input')
        .addEventListener('change', e => {
            const canvasContainer = document.querySelector(".edit_cover_container");
            if(!canvasContainer.classList.contains("full")){
                _createCanvas();
                canvasContainer.querySelector(".confirm_avatar").
                    addEventListener("click", removeCanvas);
            }

            let image = document.getElementById("image");
            let files = e.target.files;
            let fr = new FileReader();
            fr.onloadend = function() {
                image.src = fr.result;
                setTimeout(() =>{
                    canvasCreator.init();
                    canvasContainer.style.maxHeight = canvasContainer.scrollHeight + 'px';
                    canvasContainer.style.marginTop = '45px';
                    canvasContainer.style.marginBottom = '45px';
                }, 200 );
            };
            fr.readAsDataURL(files[0]);
        });
}

function _createCanvas(){
    const canvasContainer = document.querySelector(".edit_cover_container");
    canvasContainer.insertAdjacentHTML("afterbegin", `
            <div class="preview_cover">
                <img src="" id="image" class="image" alt="Картинка обложки">
                <canvas id="canvas" class="canvas">
                    Your browser does not support JS or HTML5!
                </canvas>
            </div>
            <div class="result_cover">
                <h3>Обложка альбома</h3>
                <div class="resultPhoto">
                    <canvas id="myCanvas" width="200" height="200">
                        Your browser does not support JS or HTML5!
                    </canvas>
                </div>
                <button type="button" class="confirm_avatar">Подтвердить</button>
            </div>`);
    // класс не используется в css. Он добавлен для последующей рповерки
    // есть ли внутри контейнера уже созданный canvas.
    canvasContainer.classList.add("full");
}

function removeCanvas(){
    const cover = document.querySelector(".cover");
    const myCanvas = document.getElementById("myCanvas");
    const coverInput = document.getElementById("cover_input");
    const resultCoverInput = document.getElementById("result_cover_input");

    cover.src = myCanvas.toDataURL("image/jpeg");
    resultCoverInput.value = myCanvas.toDataURL("image/jpeg");
    coverInput.value = null;

    const canvasContainer = document.querySelector(".edit_cover_container");
    canvasContainer.style.maxHeight = "";
    canvasContainer.style.marginTop = '';
    canvasContainer.style.marginBottom = '';
    canvasContainer.classList.remove("full");
    setTimeout(() => canvasContainer.innerHTML = "", 600);
}

const canvasCreator = {
    //поля, связанные с HTML документом
    container: null,
    image: null,
    canvas: null,
    ctx: null,
    myCanvas: null,
    myCtx: null,

    // служебные поля
    rect: {
        startX: 80,
        startY: 80,
        w: 150,
        h: 150
    },

    drag: false,
    dragTL: false,
    dragBL: false,
    dragTR: false,
    dragBR: false,
    mDown: false,
    closeEnough: 4,

    //координаты нажатия и движения мыши
    mouseX: 0,
    mouseY: 0,

    getElementsFromHTML(){
        this.container = document.querySelector(".preview_cover");
        this.image = document.querySelector(".image");
        this.canvas = document.getElementById('canvas');
        this.ctx = canvas.getContext('2d');
        this.myCanvas = document.getElementById("myCanvas");
        this.myCtx = myCanvas.getContext('2d');
    },

    init: function (){
        this.getElementsFromHTML();
        this.canvas.width = this.image.width;
        this.canvas.height = this.image.height;
        this.canvas.setAttribute("style", "top: " + (this.image.offsetTop + 2) + "px; left: " + (this.image.offsetLeft + 2) + "px;");

        this.draw();

        this.canvas.addEventListener('mousedown', e => {
            this.mouseDown(e);
        });
        this.canvas.addEventListener('mouseup', e => {
            this.mouseUp();
        });
        this.canvas.addEventListener('mousemove', e => {
            this.mouseMove(e);
        });
        this.canvas.addEventListener('mouseout', e => {
            this.mouseOut();
        });
    },

    checkCloseEnough(p1, p2) {
        return Math.abs(p1 - p2) < this.closeEnough;
    },

    mouseDown(e) {
        this.mouseX = e.pageX - this.container.offsetLeft;
        this.mouseY = e.pageY - this.container.offsetTop;
        // console.log(e.pageY);
        console.log(this.canvas.offsetLeft);

        // 4 cases:
        // 1. top left
        if (this.checkCloseEnough(this.mouseX, this.rect.startX) && this.checkCloseEnough(this.mouseY, this.rect.startY)) {
            this.dragTL = true;
            this.mDown = false;
        }
        // 2. top right
        else if (this.checkCloseEnough(this.mouseX, this.rect.startX + this.rect.w) && this.checkCloseEnough(this.mouseY, this.rect.startY)) {
            this.dragTR = true;
            this.mDown = false;
        }
        // 3. bottom left
        else if (this.checkCloseEnough(this.mouseX, this.rect.startX) && this.checkCloseEnough(this.mouseY, this.rect.startY + this.rect.h)) {
            this.dragBL = true;
            this.mDown = false;
        }
        // 4. bottom right
        else if (this.checkCloseEnough(this.mouseX, this.rect.startX + this.rect.w) && this.checkCloseEnough(this.mouseY, this.rect.startY + this.rect.h)) {
            this.dragBR = true;
            this.mDown = false;
        } else if(this.mouseX > this.rect.startX && this.mouseX < this.rect.startX + this.rect.w &&
            this.mouseY > this.rect.startY && this.mouseY < this.rect.startY + this.rect.h){
            this.mDown = true;
        }
        // (5.) none of them
        else {
            // handle not resizing
        }

        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.draw();
    },

    mouseUp() {
        this.dragTL = false;
        this.dragTR = false;
        this.dragBL = false;
        this.dragBR = false;
        this.mDown = false;
    },

    mouseOut(){
        this.dragTL = false;
        this.dragTR = false;
        this.dragBL = false;
        this.dragBR = false;
        this.mDown = false;
    },

    mouseMove(e) {
        this.mouseX = e.pageX - this.container.offsetLeft;
        this.mouseY = e.pageY - this.container.offsetTop;

        if(this.mouseX < this.rect.startX + this.closeEnough && this.mouseX > this.rect.startX - this.closeEnough &&
            this.mouseY < this.rect.startY + this.closeEnough && this.mouseY > this.rect.startY - this.closeEnough
        ){
            this.canvas.style.cursor = "nw-resize";
        } else if (this.mouseX < this.rect.startX + this.rect.w + this.closeEnough && this.mouseX > this.rect.startX + this.rect.w - this.closeEnough &&
            this.mouseY < this.rect.startY + this.closeEnough && this.mouseY > this.rect.startY - this.closeEnough){
            this.canvas.style.cursor = "ne-resize";
        } else if (this.mouseX < this.rect.startX + this.closeEnough && this.mouseX > this.rect.startX - this.closeEnough &&
            this.mouseY < this.rect.startY + this.rect.h + this.closeEnough && this.mouseY > this.rect.startY + this.rect.h - this.closeEnough){
            this.canvas.style.cursor = "ne-resize";
        } else if (this.mouseX < this.rect.startX + this.rect.w + this.closeEnough && this.mouseX > this.rect.startX + this.rect.w - this.closeEnough &&
            this.mouseY < this.rect.startY + this.rect.h + this.closeEnough && this.mouseY > this.rect.startY + this.rect.h - this.closeEnough){
            this.canvas.style.cursor = "nw-resize";
        } else if(this.mouseX > this.rect.startX && this.mouseX < this.rect.startX + this.rect.w &&
            this.mouseY > this.rect.startY && this.mouseY < this.rect.startY + this.rect.h){
            this.canvas.style.cursor = "move";
        }else {
            this.canvas.style.cursor = "auto";
        }


        if (this.dragTL) {
            this.rect.w += this.rect.startX - this.mouseX;
            this.rect.h = this.rect.w;
            this.rect.startX = this.mouseX;
            this.rect.startY = this.mouseY;
        } else if (this.dragTR) {
            this.rect.w = Math.abs(this.rect.startX - this.mouseX);
            this.rect.h = this.rect.w;
            this.rect.startY = this.mouseY;
        } else if (this.dragBL) {
            this.rect.w += this.rect.startX - this.mouseX;
            this.rect.h = this.rect.w;
            this.rect.startX = this.mouseX;
        } else if (this.dragBR) {
            this.rect.w = Math.abs(this.rect.startX - this.mouseX);
            this.rect.h = this.rect.w;
        } else if (this.mDown) {
            this.rect.startX = this.mouseX - this.rect.w / 2;
            this.rect.startY = this.mouseY - this.rect.h / 2;

            if(this.rect.startX <= 0) {
                this.rect.startX = 0;
            }

            if(this.rect.startY <= 0) {
                this.rect.startY = 0;
            }

            if(this.rect.startX >= this.canvas.width - this.rect.w) {
                this.rect.startX = this.canvas.width - this.rect.w;
            }

            if(this.rect.startY >= this.canvas.height - this.rect.h) {
                this.rect.startY = this.canvas.height - this.rect.h;
            }
        }
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.draw();
    },

    draw() {
        this.drawSelection();
        this.ctx.clearRect(this.rect.startX, this.rect.startY, this.rect.w, this.rect.h);
        this.drawHandles();
        this.drawImage(this.image, this.rect);
    },

    drawSelection(){
        this.ctx.fillStyle = "rgba(0, 0, 0, 0.4)";
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
    },

    drawHandles() {
        this.drawCircle(this.rect.startX, this.rect.startY, this.closeEnough);
        this.drawCircle(this.rect.startX + this.rect.w, this.rect.startY, this.closeEnough);
        this.drawCircle(this.rect.startX + this.rect.w, this.rect.startY + this.rect.h, this.closeEnough);
        this.drawCircle(this.rect.startX, this.rect.startY + this.rect.h, this.closeEnough);
    },

    drawCircle(x, y, radius) {
        this.ctx.fillStyle = "#FF0000";
        this.ctx.beginPath();
        this.ctx.arc(x, y, radius, 0, 2 * Math.PI);
        this.ctx.fill();
    },

    drawImage(image, rect){
    //переменные с новыми рассчетами в проценах.
    // ширина по оси Х от нуля до начала прямоугольной области делится на ширину холста и умножатся на реальную ширину картинки
    let cropLeft = (this.rect.startX / this.canvas.width) * this.image.naturalWidth;
    // ширина по оси Y от нуля до начала прямоугольной области делится на высоту холста и умножатся на реальную высоту картинки
    let cropTop = (this.rect.startY / this.canvas.height) * this.image.naturalHeight;
    // ширина  прямоугольника по оси Х делится на ширину холста и умножатся на реальную ширину картинки
    let cropWidth = (this.rect.w / this.canvas.width) * this.image.naturalWidth;
    // высота прямоугольной области по оси  Y  делится на высоту холста и умножатся на реальную высотуу картинки
    let cropHeight = (this.rect.h / this.canvas.height) * this.image.naturalHeight;

    // функция отрисовывает на новом холсте изображение по получившимся размерам
    this.myCtx.drawImage(this.image,
        cropLeft, cropTop,
        cropWidth, cropHeight,
        0, 0,
        200, 200);
    }
}


// холст и контекст для отображения выделенного фрагмента.
// let myCanvas = document.getElementById("myCanvas");
// let myCtx = myCanvas.getContext('2d');
//
// let canvas = document.getElementById('canvas'),
//     ctx = canvas.getContext('2d'),
//     rect = {},
//     drag = false,
//     mouseX,
//     mouseY,
//     closeEnough = 4,
//     dragTL = dragBL = dragTR = dragBR = false,
//     mDown = false;
//
// константа для корректного отображения полей модального окна. Их
// offset добавляется к полям  canvas
// const container = document.querySelector(".edit_cover_container");
//
// //функция инициализации холста
// function init() {
//     canvas.width = image.width;
//     canvas.height = image.height;
//
//     canvas.setAttribute("style", "top: " + (image.offsetTop + 2) + "px; left: " + (image.offsetLeft + 2) + "px;");
//     rect = {
//         startX: 80,
//         startY: 80,
//         w: 150,
//         h: 150
//     }
//
//     draw();
//     canvas.addEventListener('mousedown', mouseDown, false);
//     canvas.addEventListener('mouseup', mouseUp, false);
//     canvas.addEventListener('mousemove', mouseMove, false);
//     canvas.addEventListener('mouseout', mouseOut, false);
// }
//
// function mouseDown(e) {
//     mouseX = e.pageX - canvas.offsetLeft;
//     mouseY = e.pageY - canvas.offsetTop;
//
//     // 4 cases:
//     // 1. top left
//     if (checkCloseEnough(mouseX, rect.startX) && checkCloseEnough(mouseY, rect.startY)) {
//         dragTL = true;
//         mDown = false;
//     }
//     // 2. top right
//     else if (checkCloseEnough(mouseX, rect.startX + rect.w) && checkCloseEnough(mouseY, rect.startY)) {
//         dragTR = true;
//         mDown = false;
//     }
//     // 3. bottom left
//     else if (checkCloseEnough(mouseX, rect.startX) && checkCloseEnough(mouseY, rect.startY + rect.h)) {
//         dragBL = true;
//         mDown = false;
//     }
//     // 4. bottom right
//     else if (checkCloseEnough(mouseX, rect.startX + rect.w) && checkCloseEnough(mouseY, rect.startY + rect.h)) {
//         dragBR = true;
//         mDown = false;
//     } else if(mouseX > rect.startX && mouseX < rect.startX + rect.w &&
//         mouseY > rect.startY && mouseY < rect.startY + rect.h){
//         mDown = true;
//     }
//     // (5.) none of them
//     else {
//         // handle not resizing
//     }
//
//     ctx.clearRect(0, 0, canvas.width, canvas.height);
//     draw();
//
// }
//
// function checkCloseEnough(p1, p2) {
//     return Math.abs(p1 - p2) < closeEnough;
// }
//
// function mouseUp() {
//     dragTL = dragTR = dragBL = dragBR = mDown = false;
// }
//
// function mouseOut(){
//     dragTL = dragTR = dragBL = dragBR = mDown = false;
// }
//
// function mouseMove(e) {
//     // mouseX = e.pageX - (this.offsetLeft + vModal.offsetLeft);
//     // mouseY = e.pageY - (this.offsetTop + 100);
//     mouseX = e.pageX - canvas.offsetLeft;
//     mouseY = e.pageY - canvas.offsetTop;
//
//     if(mouseX < rect.startX + closeEnough && mouseX > rect.startX - closeEnough &&
//         mouseY < rect.startY + closeEnough && mouseY > rect.startY - closeEnough
//     ){
//         canvas.style.cursor = "nw-resize";
//     } else if (mouseX < rect.startX + rect.w + closeEnough && mouseX > rect.startX + rect.w - closeEnough &&
//         mouseY < rect.startY + closeEnough && mouseY > rect.startY - closeEnough){
//         canvas.style.cursor = "ne-resize";
//     } else if (mouseX < rect.startX + closeEnough && mouseX > rect.startX - closeEnough &&
//         mouseY < rect.startY + rect.h + closeEnough && mouseY > rect.startY + rect.h - closeEnough){
//         canvas.style.cursor = "ne-resize";
//     } else if (mouseX < rect.startX + rect.w + closeEnough && mouseX > rect.startX + rect.w - closeEnough &&
//         mouseY < rect.startY + rect.h + closeEnough && mouseY > rect.startY + rect.h - closeEnough){
//         canvas.style.cursor = "nw-resize";
//     } else if(mouseX > rect.startX && mouseX < rect.startX + rect.w &&
//         mouseY > rect.startY && mouseY < rect.startY + rect.h){
//         canvas.style.cursor = "move";
//     }else {
//         canvas.style.cursor = "auto";
//     }
//
//
//     if (dragTL) {
//         rect.w += rect.startX - mouseX;
//         rect.h = rect.w;
//         rect.startX = mouseX;
//         rect.startY = mouseY;
//     } else if (dragTR) {
//         rect.w = Math.abs(rect.startX - mouseX);
//         rect.h = rect.w;
//         rect.startY = mouseY;
//     } else if (dragBL) {
//         rect.w += rect.startX - mouseX;
//         rect.h = rect.w;
//         rect.startX = mouseX;
//     } else if (dragBR) {
//         rect.w = Math.abs(rect.startX - mouseX);
//         rect.h = rect.w;
//     } else if (mDown) {
//         rect.startX = mouseX - rect.w / 2;
//         rect.startY = mouseY - rect.h / 2;
//
//         if(rect.startX <= 0) {
//             rect.startX = 0;
//         }
//
//         if(rect.startY <= 0) {
//             rect.startY = 0;
//         }
//
//         if(rect.startX >= canvas.width - rect.w) {
//             rect.startX = canvas.width - rect.w;
//         }
//
//         if(rect.startY >= canvas.height - rect.h) {
//             rect.startY = canvas.height - rect.h;
//         }
//     }
//     ctx.clearRect(0, 0, canvas.width, canvas.height);
//     draw();
// }
//
// function draw() {
//     drawSelection();
//     ctx.clearRect(rect.startX, rect.startY, rect.w, rect.h);
//     drawHandles();
//     drawImage(image, rect);
// }
//
// function drawCircle(x, y, radius) {
//     ctx.fillStyle = "#FF0000";
//     ctx.beginPath();
//     ctx.arc(x, y, radius, 0, 2 * Math.PI);
//     ctx.fill();
// }
//
// function drawHandles() {
//     drawCircle(rect.startX, rect.startY, closeEnough);
//     drawCircle(rect.startX + rect.w, rect.startY, closeEnough);
//     drawCircle(rect.startX + rect.w, rect.startY + rect.h, closeEnough);
//     drawCircle(rect.startX, rect.startY + rect.h, closeEnough);
// }
//
// function drawSelection(){
//     ctx.fillStyle = "rgba(0, 0, 0, 0.4)";
//     ctx.clearRect(0, 0, canvas.width, canvas.height);
//     ctx.fillRect(0, 0, canvas.width, canvas.height);
// }
//
// function drawImage(image, rect){
//     //переменные с новыми рассчетами в проценах.
//     // ширина по оси Х от нуля до начала прямоугольной области делится на ширину холста и умножатся на реальную ширину картинки
//     let cropLeft = (rect.startX / canvas.width) * image.naturalWidth;
//     // ширина по оси Y от нуля до начала прямоугольной области делится на высоту холста и умножатся на реальную высоту картинки
//     let cropTop = (rect.startY / canvas.height) * image.naturalHeight;
//     // ширина  прямоугольника по оси Х делится на ширину холста и умножатся на реальную ширину картинки
//     let cropWidth = (rect.w / canvas.width) * image.naturalWidth;
//     // высота прямоугольной области по оси  Y  делится на высоту холста и умножатся на реальную высотуу картинки
//     let cropHeight = (rect.h / canvas.height) * image.naturalHeight;
//
//     // функция отрисовывает на новом холсте изображение по получившимся размерам
//     myCtx.drawImage(image,
//         cropLeft, cropTop,
//         cropWidth, cropHeight,
//         0, 0,
//         200, 200);
// }
