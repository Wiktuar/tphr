// кнопка, отправляющая введенный комментарий на сохранение
const sendCommentBtn = document.querySelector(".send_comment_btn");

// контейнер для комментариев
const commentsContainer = document.querySelector(".comments_container");

// function printMessage(poemID){
//     console.log(poemID);
// }
//
// window.addEventListener("DOMContentLoaded", () => printMessage(poemID));
window.addEventListener("DOMContentLoaded", getAllComments);

sendCommentBtn.addEventListener("click",  async e => {
    e.preventDefault();
    const input = document.getElementById("comment_input");
    const textArea = document.getElementById("text_area");
    let id = Number(input.value);
    let text = textArea.value;

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

        res.json().then(comment => document.querySelector(`#comment${comment.id} .comment_text`)
            .textContent = comment.text);

    } else {
        res = await fetch("/addComment", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            //навания переменных в фигурных скобках должны соответствовать
            //названию полей в сущности. В данном случае здесь одно поле text
            body: JSON.stringify({id, text})
        });
        res.json().then(comment => commentToHTML(comment));
    }

    textArea.value = "";
});

//функция, получающая с сервера все комментарии
async function getAllComments(){
    const res = await fetch("/getComments");
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
            deleteCommentById(Number(e.target.classList[1]))
        }

        if(e.target.classList[0] === "edit_pencil"){
            editCommentById(Number(e.target.classList[1]));
        }
    }
})



// функция, удаляющая комментарий. Без ассинхронности не работает
 async function deleteCommentById(id){
    const res = await fetch(`/deletecomment/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    if(res.status === 200){
        document.getElementById(`comment${id}`).remove();
    }
}

//  метод для редактирования комментария
async function editCommentById(id){
    const input = document.getElementById("comment_input");
    const textArea = document.getElementById("text_area");

    const res = await fetch(`/getcomment/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    res.json().then(str => {
        let arr = str.split(",");
        input.value = arr[0];
        textArea.value = arr[1];
    })
}
