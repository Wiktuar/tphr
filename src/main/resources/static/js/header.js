(function (){
    const burgerBtn = document.querySelector(".header_burger_btn");
    const burgerBox = document.querySelector(".dropdown_burger_box");
    const dropDownBos = document.querySelector(".dropdown-box-mobile");
    const menuBtn = document.querySelector(".cabinet-button-mobile");

    burgerBtn.addEventListener("click", () => {
        burgerBox.classList.toggle("open");
    })

    burgerBtn.addEventListener("touchstart", () => {
        burgerBox.classList.toggle("open");
    });

    if(menuBtn){
        menuBtn.addEventListener("click", e => dropDown(e, dropDownBos));
    }
})();


//выпадающий список меню личного кабинета
(function(){
    const cabinetBtn = document.querySelector(".cabinet-button");
    const dropDownBox = document.querySelector(".dropdown-box");
//  здесь true - это свойство погружения, то есть событие, повешенное на верхний элемент
//  будет распространяться и на его потомки
    cabinetBtn.addEventListener("click", (e) => dropDown(e, dropDownBox));
})()

function dropDown(e, dropDownBox){
    if(+dropDownBox.style.maxHeight === 0) {
        dropDownBox.style.maxHeight = (dropDownBox.scrollHeight + 300) + 'px';
    } else {
        dropDownBox.style.maxHeight = "";
    }
}

// функция, закрывающая окно меню личного кабинете при клике вне
document.addEventListener("click", e => {
    if(!e.target.classList.contains("cabinet-button") && !e.target.classList.contains("user-pic")){
        const dropDownBox = document.querySelector(".dropdown-box");
        dropDownBox.style.maxHeight = "";
    }
});

