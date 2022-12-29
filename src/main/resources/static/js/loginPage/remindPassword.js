const email = document.getElementById("email");
const remindBtn = document.querySelector(".remindBtn");

function mistakeEmail(mistake) {
    attentionWindow.open();
    attentionWindow.setTitle(" ")
    attentionWindow.setContent(`<p>${mistake}</p>`);
}

//метод, проверяющий заполнение поля "Почта"
function _checkEmailField(){
    const checkEmailPattern = /[^0-9A-Za-z@.]/;
    const checkEmptyPattern = /^\s*$/;
    // const checkSpacePattern = /[^\s]/;

    if(checkEmptyPattern.test(email.value)){
        mistakeEmail('Поле "Почта" пустое');
        return true;
    }

    if(checkEmailPattern.test(email.value.trim())){
        mistakeEmail('В поле "Почта" присутствуют недопустимые символы или есть пробел');
        return true;
    }

    if(!email.value.trim().includes("@")){
        mistakeEmail('В адресе почта нет символа "@"');
        return true;
    }
}

remindBtn.addEventListener("click", e => {
    e.preventDefault();
    if(_checkEmailField()) return;

    const endPoint = "/reset/remindPassword";
    const formData = new FormData();

    formData.append("email", email.value);

    fetch(endPoint, {
        method: "post",
        body: formData
    })
        .then(response => {
            if(response.status === 400) {
                attentionWindow.open();
                attentionWindow.setContent(`<p>Аккаунт с почтой <span style="red">${email.value}</span> не найден</p>`);
            } else if (response.status === 200){
                attentionWindow.open();
                attentionWindow.setContent(`<p>Ссылка на форму восстановления пароля успешно отправлена на Вашу почту</p>`);
                attentionWindow.setHandLer();
            }
        })
        .catch(error => console.error(error));
})
