// кнопка, отправляющая введенный комментарий на сохранение
const sendCommentBtn = document.querySelector(".send_comment_btn");
const textArea = document.getElementById("text_area");
//элемент HTML хрнящий количество комментариев
const countOfComments = document.querySelector(".p_digit_c");

// контейнер для комментариев
const commentsContainer = document.querySelector(".comments_container");

// таким приемом можно передавать аргументы в функцию, которая вешается на событие
window.addEventListener("DOMContentLoaded", () => getAllComments(poemID));

// метод отправки комментария по нажатию Enter и перехода на новую строку при нажатии Shift + Enter
sendCommentBtn.addEventListener("click",   async e => saveOrUpdate(e, poemID));
textArea.addEventListener("keydown", e => {
    if(e.shiftKey && e.key === "Enter"){
        // здесь ничего нет, потому что он сам в текстовой области переводит на новую строку.
    } else if(e.key === "Enter"){
        saveOrUpdate(e);
    }
});

// функция сохранения или обновления комментария
async function saveOrUpdate(e){
    e.preventDefault();
    const input = document.getElementById("comment_input");
    const textArea = document.getElementById("text_area");
    let id = Number(input.value);
    let text = textArea.value;
    let poemId = poemID;

    //если ID не равно 0, это значит, что мы обновляем комментарий
    // у вновь созданного комментария ID равно 0
    let res = null;
    if (id !== 0 ){
        //  вместо строки json посылаю обычный post запрос, поскольку с точки зрения сервера так удобнее
        const formData = new FormData();
        formData.append("id", String(id));
        formData.append("text", text);
        res = await fetch("/editcomment/${id}", {
            method: 'POST',
            // // headers: {
            // //     'Content-Type': 'application/json'
            // // },
            // body: JSON.stringify({id, text})
            body: formData
        })

        res.json().then(comment => {
            // let txt = comment.text.replaceAll("<br>", "\r\n");
            // console.log(txt);
            document.querySelector(`#comment${comment.id} .comment_text`)
                .innerHTML = comment.text;
        });

    } else {
        const formData = new FormData();
        formData.append("id", String(id));
        formData.append("text", text);
        formData.append("poemId", poemId);
        res = await fetch("/addComment", {
            method: 'POST',
            // headers: {
            //     'Content-Type': 'application/json'
            // },
            //навания переменных в фигурных скобках должны соответствовать
            //названию полей в сущности. В данном случае здесь одно поле text
            // body: JSON.stringify({id, text, poemId})
            body: formData
        });
        res.json().then(comment => {
            commentToHTML(comment);
            countOfComments.textContent = comment.countOfComments;
        })
    }

     input.value = String(0);
     textArea.value = "";
}

//функция, получающая с сервера все комментарии
async function getAllComments(id){
    const res = await fetch(`/getComments/${id}`);
    const comments = await res.json();

    comments.forEach(c => commentToHTML(c));
}

//функция внедряющая сомментарий в HTML разметку.
function commentToHTML(comment){
    const commentsContainer = document.querySelector(".comments_container");
    commentsContainer.insertAdjacentHTML('beforeend', `
            <div id="comment${comment.id}" class="comment_block">
                <img src="../../static/img${comment.author.pathToAvatar}" class="comment_author_avatar" alt="аватар автора">
                <div class="comment_body">
                    <h2 class="comment_author_name">${comment.author.firstName}  ${comment.author.lastName}</h2>
                    <a href="#" class="update_comment_btn"><img src="../../static/img/edit.png" class="edit_pencil ${comment.id}" alt="Обновление комментария"></a>
                    <a href="#" class="delete_comment_btn"><img src="../../static/img/close.png" class="close_cross ${comment.id}" alt="Удаление комментария"></a>
                    <div class="comment_text" style="text-align: justify">
                        ${comment.text}
                    </div>
                    <span class="time_stamp">${comment.timeStamp.substr(0, 16)}</span>
                </div>
            </div>`);
}

// функция, делегирующая событие клика мышки от контейнера комментариев к конкретному блоку
commentsContainer.addEventListener("click", e => {
    e.preventDefault();
    if(e.target.classList.length === 2){
        if(e.target.classList[0] === "close_cross"){
            // Решить роблему! из метода возвращается промис, который игнорируется.
            deleteCommentById(Number(e.target.classList[1]), poemID)
        }

        if(e.target.classList[0] === "edit_pencil"){
            updateCommentById(Number(e.target.classList[1]));
        }
    }
})



// функция, удаляющая комментарий. Без ассинхронности не работает
 async function deleteCommentById(id, poemId){
    const res = await fetch(`/deletecomment/${id}/${poemId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    if(res.status === 200){
        document.getElementById(`comment${id}`).remove();
        res.json().then(r => countOfComments.textContent = r);
    }
}

//  метод для редактирования комментария
async function updateCommentById(id){
    const input = document.getElementById("comment_input");
    const textArea = document.getElementById("text_area");

    const res = await fetch(`/getcomment/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    res.json().then(comment => {
        console.log(comment.text);
        input.value = comment.id;
        textArea.value = comment.text;
    })
}
