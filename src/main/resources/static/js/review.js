import {getAttentionDiv} from "./music/utils.js";

const submit = document.querySelector(".review_btn");

submit.addEventListener("click", e=> {
    e.preventDefault();
    if(_checkFormFields()) return;

    const endPoint = "/main/savereview";
    const formData = new FormData(document.getElementById("review_form"));

    fetch(endPoint, {
        method: "post",
        body: formData
    })
        .then(response => {
            if (response.status === 200){
                attentionWindow.open();
                attentionWindow.setTitle(" ");
                attentionWindow.setContent(`<p>Отзыв успешно отправлен!</p>`);
                attentionWindow.setHandLer();
            } else {
                attentionWindow.open();
                attentionWindow.setTitle(" ");
                attentionWindow.setContent(`<p>Отзыв не отправлен. Повторите попытку позднее</p>`);
                attentionWindow.setHandLer();
            }
        })
        .catch(error => console.error(error));
})

function _checkFormFields(){
    const form = document.getElementById("review_form");
    const sendBtn = document.querySelector(".review_btn");
    const textInputList = document.querySelectorAll('[data-text]');
    let hasEmptyInputs = false;

    if(document.querySelector(".attention"))
        document.querySelector(".attention").remove();

    for(let input of textInputList){
        if(!input.value){
            form.insertBefore(getAttentionDiv("Необходимо заполнить все поля формы!"), sendBtn);
            hasEmptyInputs = true;
            break;
        }
    }
    return hasEmptyInputs;
}