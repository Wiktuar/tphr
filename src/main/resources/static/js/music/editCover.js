export const addCanvas = function(){
    document.getElementById('cover_input')
        .addEventListener('change', e => {
            const canvasContainer = document.querySelector(".edit_cover_container");
            if(!canvasContainer.classList.contains("full")){
                _createCanvas();
                canvasContainer.querySelector(".confirm_btn").
                    addEventListener("click", removeCanvas);
            }

            const addPoemBox = document.querySelector(".add_poem_box");
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
                    if(addPoemBox){
                        let pHeight = addPoemBox.style.maxHeight.slice(0, -2);
                        let cHeight = canvasContainer.style.maxHeight.slice(0, -2);
                        addPoemBox.style.maxHeight = cHeight + pHeight + "px";
                    }
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
                <h3>Превью картинки</h3>
                <div class="resultPhoto">
                    <canvas id="myCanvas" width="250px" height="200px">
                        Your browser does not support JS or HTML5!
                    </canvas>
                </div>
                <button type="button" class="confirm_btn">Подтвердить</button>
            </div>`);
    // класс не используется в css. Он добавлен для последующей проверки
    // есть ли внутри контейнера уже созданный canvas.
    canvasContainer.classList.add("full");
}

function removeCanvas(){
    const cover = document.querySelector(".cover");
    const myCanvas = document.getElementById("myCanvas");
    const coverInput = document.getElementById("cover_input");
    const resultCoverInput = document.getElementById("result_cover_input");
    const coverText = document.querySelector(".default_cover");

    cover.src = myCanvas.toDataURL("image/jpeg");
    resultCoverInput.value = myCanvas.toDataURL("image/jpeg");
    coverInput.value = null;
    coverText.textContent = "";

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
        w: 200,
        h: 160
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
            this.rect.h = this.rect.w / 1.25;
            this.rect.startX = this.mouseX;
            this.rect.startY = this.mouseY;
        } else if (this.dragTR) {
            this.rect.w = Math.abs(this.rect.startX - this.mouseX);
            this.rect.h = this.rect.w / 1.25;
            this.rect.startY = this.mouseY;
        } else if (this.dragBL) {
            this.rect.w += this.rect.startX - this.mouseX;
            this.rect.h = this.rect.w / 1.25;
            this.rect.startX = this.mouseX;
        } else if (this.dragBR) {
            this.rect.w = Math.abs(this.rect.startX - this.mouseX);
            this.rect.h = this.rect.w / 1.25;
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
    //переменные с новыми расчетами в проценnах.
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
        250, 200);
    }
}

