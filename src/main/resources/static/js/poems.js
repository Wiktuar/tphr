//расположение фотографии в зависимости от соотношения длины и ширины
let poemImage = document.getElementsByClassName("poem-image");

for(let image of poemImage){
    if(image.naturalWidth < image.naturalHeight){
        image.style.width = '30%';
    } else {
        image.style.width = '60%';
    }
}

// отображение загруженной фотографии на обложку стиха
const image = document.getElementById("image");
const view_cover = document.querySelector(".view_cover");
const default_cover = document.querySelector(".default_cover");

document.getElementById('file')
    .addEventListener('change', e => {
        let files = e.target.files;
        let fr = new FileReader();
        fr.onloadend = function() {
            image.src = fr.result;
            image.addEventListener("load", resize);
            default_cover.textContent = "";;
        };
        fr.readAsDataURL(files[0]);
    });

function resize(){
    if(image.naturalWidth < image.naturalHeight){
        image.style.width = '45%';
    } else {
        image.style.width = '60%';
    }
}

//отображение формы добавления стихотворения
(function(){
    const addPoemBtn = document.querySelector(".add_poem_btn");
    const addPoemBox = document.querySelector(".add_poem_box");

    addPoemBtn.addEventListener("click", function () {
        if(+addPoemBox.style.maxHeight === 0) {
            addPoemBox.style.maxHeight = (addPoemBox.scrollHeight + 300) + 'px';
        } else {
            addPoemBox.style.maxHeight = "";
        }
    });
})();

//функция подтверждающая удаление стихотворения
const deleteBtn = document.querySelector(".delete_link");
const poemHeader = document.querySelector(".inner_poem_header");
let id = poemHeader.dataset.id;

deleteBtn.addEventListener("click", e =>{
    e.preventDefault();
    $.confirm({
        title: "Вы уверены?",
        content: `<p>Вы удаляете стихотворение <br><span style="font-weight: bold;">&laquo;${poemHeader.textContent}&raquo;</span></p>`,
    }).then(() => {
        document.location = `/cabinet/delete/poem/${id}`;
    })
})