//пустая функция. Она служит далее, как заглушка
function noop(){

}

// создание дополнительного поля (функции) прототипу  Element, который реализуют все HTML теги,
//чтобы реализовать добавение  HTML элемента после существующего элемента.
Element.prototype.appendAfter = function (element){
    element.parentNode.insertBefore(this, element.nextSibling);
}

//функция созддания футера и его кнопок. Дефотным значением аргумента стоит пустой объект, если вдруг массива нет
function _createModalFooter(buttons= {}){
    if(buttons.length === 0) {
        return document.createElement("div");
    }

    const wrap = document.createElement("div")
    wrap.classList.add("vmodal-footer");

    //проверка для того, чтобы не добавлять label в тех модальных окнах, где это не нужно
    if(!(buttons[0].text === "Хорошо!")){
        const label = document.createElement('label');
        label.setAttribute("for", "file");
        label.textContent = 'Другое фото';
        wrap.appendChild(label);
    }

    buttons.forEach(btn => {
        const $btn = document.createElement('button');
        $btn.textContent = btn.text;
        $btn.classList.add('btn');
        $btn.onclick = btn.handler || noop;
        wrap.appendChild($btn);
    })

    return wrap;
}


// это системная приватная функция. На это указывает нижнее подчеркивание. её не надо вызывать отдельно
//функция формирует модальное окно
function _createModal(options){
    //дефолтное значение ширины модального окна
    const DEFAULT_WIDTH = "600px";

    //создается корневой элемент <div class=""vmodal>  и добавляется в него содержимое
    const modal = document.createElement('div');
    modal.classList.add('vmodal');

    //data-close специальный атрибут, придуманной мною для пометки элементов, при клике на которые модальное окно закрывается
    modal.insertAdjacentHTML('afterbegin',
        `
    <div class="modal-overlay" data-close="true">
        <div class="modal-window" style="width: ${options.width || DEFAULT_WIDTH}">
            <div class="vmodal-header">
                <span class="modal-title">${options.title || "Окно"}</span>
                ${options.closeable ? `<span class="modal-close" data-close="true">&times;</span>` : ""}
            </div>
            <div class="vmodal-body" data-content>
                ${options.content || ""}
            </div>
        </div>
    </div>`
    );
    const footer = _createModalFooter(options.footerButtons);
    footer.appendAfter(modal.querySelector('[data-content]'));
    document.body.appendChild(modal);
    return modal;
}

// Это - главная функция. Она создает модальное окно, добавяя поле modal объекту $.
// Также эта функция добавляет основные методы работы модального окна.
$.modal = function (options){
    //константа времени анимации
    const ANIMATION_SPEED = 200;

    // служебная переменная для корректной работы закрытия окна, чтобы случайно его не открыть во время анимации закрытия
    let closing = false;

    //служебная переменная для метода destroy
    let destroyed = false;

    // знаком $ удобно обозначать, что переменная содержит DOM Node элемент
    const $modal = _createModal(options);

    //создан произвольный объект, чтобы работал слушатель ниже, вызывающий его метод.
    //этот объект и возвращает функция
    // фактически - это набор функций для работы с модальеым окном, которое лежит в константе  $modal
    const modal = {
        open(){
            if(destroyed){
                console.log("Modal window is destroyed");
            }
            // добавлять .open только если  closing false
            !closing && $modal.classList.add("open");
        },
        close(){
            closing = true;
            $modal.classList.remove("open");
            $modal.classList.add("hide");
            setTimeout(() => {
                $modal.classList.remove("hide");
                closing = false;
                if(typeof options.onClose === 'function'){
                    options.onClose();
                }
            }, ANIMATION_SPEED);
        }
    }

    //событие слушает клик по любому месту окна. И если у элемента есть атриут data-close = "true"
    //то срабатывает функция на закрытие
    const listener = event => {
        if (event.target.dataset.close) {
            modal.close();
        }
    }

    $modal.addEventListener("click", listener);

    // добавление в объект функции через метод копирования из другого объекта
    // при объявлении объекта
    // modal это было сделать нельзя, поскольку не было еще добавлено слушателя
    return Object.assign(modal, {
        destroy(){
            $modal.parentNode.removeChild($modal);
            $modal.removeEventListener("click", listener);
            destroyed = true;
        },

        // динамическая установка содержимого модаьного окна
        // так же можно устанавливать и заголовок
        setContent(html){
            $modal.querySelector('[data-content]').innerHTML = html;
        },

        setTitle(html){
            const $title = $modal.querySelector('.vmodal-header');
            $title.style.display = "block";
            $title.innerHTML = html;
        },

        setHandLer(){
            let buttons = Array.from(document.querySelectorAll(".btn"));
            buttons[1].onclick = function(){
                document.location = "/cabinet";
                attentionWindow.close();
            }
        }
    });
}