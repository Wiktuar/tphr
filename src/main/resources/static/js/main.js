//расположение фотографии в зависимости от соотношения длины и ширины
(function resizePoemImage(){
    let poemImage = document.getElementsByClassName("poem-image");

    for(let image of poemImage){
        if(image.naturalWidth < image.naturalHeight){
            image.style.width = '30%';
        } else {
            image.style.width = '60%';
        }
    }
})();

//выпадающий список меню личного кабинета
(function(){
    const cabinetBtn = document.querySelector(".cabinet-button");
    const dropDownBox = document.querySelector(".dropdown-box");

    cabinetBtn.addEventListener("click", function () {
        if(+dropDownBox.style.maxHeight === 0) {
            console.log(dropDownBox);
            dropDownBox.style.maxHeight = (dropDownBox.scrollHeight + 300) + 'px';
        } else {
            console.log(2);
            dropDownBox.style.maxHeight = "";
        }
    });
})();