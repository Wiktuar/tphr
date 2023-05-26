//расположение фотографии в зависимости от соотношения длины и ширины
(function(){
    let poemImage = document.getElementsByClassName("poem-image");

    for(let image of poemImage){
        if(image.naturalWidth < image.naturalHeight){
            image.style.width = '30%';
        } else {
            image.style.width = '60%';
        }
    }
})()



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


async function updatePoem(id){
    const addPoemBox = document.querySelector(".add_poem_box");
    const inputId = document.querySelector(".add_poem_box input[type=hidden]");
    const oldFileName = document.querySelector(".add_poem_box input[type=hidden].old_file_name");
    const releaseDate = document.querySelector(".add_poem_box input[type=hidden].release_date_input");
    const inputHeader = document.querySelector(".add_poem_box input[type=text]");
    const inputContent = document.querySelector(".add_poem_box textarea");
    const poemImage = document.getElementById("image");
    const default_cover = document.querySelector(".default_cover");
    const addPoemBtn = document.querySelector(".add_poem_btn");

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
         poemImage.src = `/img/${poem.fileName}`;
         addPoemBtn.textContent = "Обновить стихотворение";
         default_cover.textContent = "";
     });

    window.scroll({
        top : 0,
        behavior: "smooth"
    });

    addPoemBox.style.maxHeight = (addPoemBox.scrollHeight + 300) + 'px';
}


