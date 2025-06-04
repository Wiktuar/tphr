// функция инициализирует переменные для отображения пагинации
function initData(){
    const poemContainer = document.querySelector(".poem_fool_content");
    let poemText = poemContainer.textContent;
    const stringsArray = poemText.split("\n");
    renderPagination(poemContainer, stringsArray);
    addClickEvents(stringsArray, poemContainer);
}

// функция отображает блок пагинации
function renderPagination(poemContainer, arr){
    let num = ((arr.length - 2) / 39).toFixed(2);
    let char = num.charAt(num.length - 2);
    let count = (+char >= 1) ? Math.ceil((arr.length - 2) / 39) : Math.floor((arr.length - 2) / 39);
    if(count > 1){
        poemContainer.innerHTML = "";
        for(let i = 1; i <= 39; i++){
            poemContainer.innerHTML += (arr[i] + `<br>`);
        }

        const pagList = document.querySelector(".pag_list");
        for(let i = 1; i <= count; i++){
            if(i === 1){
                pagList.insertAdjacentHTML("beforeend",
                    `<li class="pag_item clicked">${i}</li>`);
            } else {
                pagList.insertAdjacentHTML("beforeend",
                    `<li class="pag_item">${i}</li>`);
            }
        }
        pagList.style.display = "flex";
    }
}

// функция, вешающая обработчики событий на каждую кнопку пагинации
function addClickEvents(arr, poemContainer){
    const pagElements = document.querySelectorAll(".pag_item");
    pagElements.forEach(
        elem => elem.addEventListener("click", (e) => getCurrentStrings(pagElements, e, arr, poemContainer)))
}

// функция отображения выбранного фрагмента
function getCurrentStrings(pagElements, e, arr, poemContainer){
    document.querySelector('#fulltext').scrollIntoView({ behavior: 'smooth' });

    pagElements.forEach(elem => {
        if(elem.classList.contains("clicked")) elem.classList.remove("clicked");
    });
    e.target.classList.add("clicked");
    let value = e.target.textContent;

    poemContainer.textContent = "";
    let k = (+value * 39 + 1);
    if(arr.length - k < 5) k = arr.length;
    for(let i = (+value-1)*39 + 1; i < k; i++){
        if(i > arr.length - 1)break;
        poemContainer.innerHTML += (arr[i] + `<br>`);
    }
}

initData();

