import{addCanvas} from "./music/editCover.js";
import{removeAdditionalClass} from "./music/utils.js";
import{getAttentionDiv} from "./music/utils.js";

addCanvas();

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

// метод сохранения стихотворения
document.querySelector(".savePoem")
    .addEventListener("click", e =>  {
        e.preventDefault();
        const form = document.getElementById("poem_form");
        if(form.querySelector(".attention")){
            form.querySelector(".attention").remove()
        }
        const sendBtn = form.querySelector(".savePoem");
        if(checkMistakes().hasMistakes){
            form.insertBefore(getAttentionDiv(checkMistakes().message), sendBtn);
            return;
        }

        const data = new FormData(form);

        fetch("/cabinet/poems", {
            method: "POST",
            body: data,
        }).then(response => {
            if(response.status === 200)
                document.location = "/cabinet/poems";
            else if(response.status === 422){
                form.insertBefore(getAttentionDiv("Стихотворение с таким названием уже существует"), sendBtn);
            } else {
                form.insertBefore(getAttentionDiv("Проищошла непревиденная ошибка"), sendBtn);
            }
        });

    })


//функция подтверждающая удаление стихотворения
function deletePoem(id){
    const poemHeader = document.querySelector(`.inner_poem_header${id}`);

    $.confirm({
        title: "Вы уверены?",
        content: `<p>Вы удаляете стихотворение <br><span style="font-weight: bold;">&laquo;${poemHeader.textContent}&raquo;</span></p>`,
    }).then(() => {
        document.location = `/cabinet/delete/poem/${id}`;
    })
}

// обновление и удаление стихотворений. Событие клика мышки вешается на контейнер
const poemsContainer = document.querySelector(".poems-container");

poemsContainer.addEventListener("click", e => {
    if(e.target.classList.length === 2){
        if(e.target.classList[0] === "delete_link"){
            deletePoem(e.target.classList[1]);
        }

        if(e.target.classList[0] === "update_link"){
            updatePoem(e.target.classList[1]);
        }
    }
});

// функция обновления стихотворения
async function updatePoem(id){
    const addPoemBox = document.querySelector(".add_poem_box");
    const inputId = document.querySelector(".add_poem_box input[type=hidden]");
    const oldFileName = document.querySelector(".add_poem_box input[type=hidden].old_file_name");
    const releaseDate = document.querySelector(".add_poem_box input[type=hidden].release_date_input");
    const inputHeader = document.querySelector(".add_poem_box input[type=text]");
    const inputContent = document.querySelector(".add_poem_box textarea");
    const poemImage = document.querySelector(".cover");
    const default_cover = document.querySelector(".default_cover");
    const addPoemBtn = document.querySelector(".add_poem_btn");


    removeAdditionalClass("delete_link", id);
    const deleteBtn = document.getElementsByClassName(`delete_link ${id}`)[0];;
    deleteBtn.classList.add("forbidden");

    const res = await fetch(`/cabinet/updatete/poem/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })

     res.json().then(poem => {
         inputId.value = poem.id;
         oldFileName.value = poem.fileName;
         releaseDate.value = poem.releaseDate;
         inputHeader.value = poem.header;
         inputContent.value = poem.content;
         poemImage.src = `/upload/${poem.fileName}`;
         addPoemBtn.textContent = "Обновить стихотворение";
         default_cover.textContent = "";
     });

    // поднимет страницу вверх. С первого раза делает это резко, исправить!!!
    window.scroll({
        top : 0,
        behavior: "smooth"
    });

    addPoemBox.style.maxHeight = (addPoemBox.scrollHeight + 300) + 'px';
}

function checkMistakes(form){
    const error = {
        hasMistakes: false,
        message: ""
    };

    const headerText = document.getElementById("text");
    const content = document.getElementById("content");

    headerText.value = headerText.value.trim();
    headerText.value = headerText.value.replaceAll(/\s+/g, ' ');
    content.value = content.value.trim();

    if(!headerText.value) {
        error.hasMistakes = true;
        error.message = "Вы не заполнили заглавие стихотворения!";
    }

    if(!content.value) {
        error.hasMistakes = true;
        error.message = "Вы не заполнили содержание стихотворения";
    }

    return error;
}


