//кнопка отправки фориы
const $regBtn = document.getElementById("regBtn");


//метод, который вызывается при получении ошибки
function mistakeMethod(mistake) {
    attentionWindow.open();
    attentionWindow.setTitle(" ")
    attentionWindow.setContent(`<p>${mistake}</p>`);
}

//метод, проверяющий различные поля формы
function _checkFormFields(){
    const firstName = document.getElementById("firstName");
    const lastName = document.getElementById("lastName");
    const email = document.getElementById("email");

    const checkPersonPattern = /[^А-Яа-яЁё]/;
    const checkEmailPattern = /[^0-9A-Za-z@.]/;
    const checkEmptyPattern = /^\s*$/;

    if(checkEmptyPattern.test(firstName.value)){
        mistakeMethod('Поле "Имя" пустое');
        return true;
    }

    if(checkEmptyPattern.test(lastName.value)){
        mistakeMethod('Поле "Фамилия" пустое');
        return true;
    }

    if(checkEmptyPattern.test(email.value)){
        mistakeMethod('Поле "Почта" пустое');
        return true;
    }

    if(checkPersonPattern.test(firstName.value.trim())){
        mistakeMethod('В поле "Имя" присутствуют латинские символы или есть пробел');
        return true;
    }

    if(firstName.value.trim().length > 12){
        mistakeMethod('В поле "Имя" слишком много символов');
        return true;
    }

    if(checkPersonPattern.test(lastName.value.trim())){
        mistakeMethod('В поле "Фамилия" присутствуют латинские символы или есть пробел');
        return true;
    }

    if(lastName.value.trim().length > 20){
        mistakeMethod('В поле "Фамилия" слишком много символов');
        return true;
    }

    if(checkEmailPattern.test(email.value.trim())){
        mistakeMethod('В поле "Почта" присутствуют недопустимые символы или есть пробел');
        return true;
    }

    if(!email.value.trim().includes("@")){
        mistakeMethod('В адресе почта нет символа "@"');
        return true;
    }
}

$regBtn.addEventListener("click", e => {
    e.preventDefault();
    if(_checkFormFields())return;

    const endPoint = "/editauthor";
    const form = document.getElementById("regForm");
    const formData = new FormData(form);

    fetch(endPoint, {
        method: "post",
        body: formData
    })
        .then(response => {
            console.log(response.status);
            if(response.status === 400) {
                attentionWindow.open();
                attentionWindow.setContent(`<p>Аккаунт с почтой <span style="red">${email.value}</span> уже занят</p>`);
            } else if (response.status === 202){
                logoutWindow.open();
                logoutWindow.setTitle(`<p style="text-align: center; font-size: 20px">Поздравляем!</p>`);
                logoutWindow.setContent(`<p>Вы успешно обновили данные профиля. На Вашу почту отправлена ссылка для его повторной активации в связи с измнением адреса электронной почты </p>`);
            } else if (response.status === 200){
                attentionWindow.open();
                attentionWindow.setTitle(`<p style="text-align: center; font-size: 20px">Поздравляем!</p>`);
                attentionWindow.setContent(`<p>Вы успешно обновили данные профиля.</p>`);
                attentionWindow.setHandLer();
            }
        })
        .catch(error => console.error(error));
})

//установление аватара по умолчанию
const defaultBtn = document.querySelector(".default");

defaultBtn.addEventListener("click", setDefaultAva);

function setDefaultAva(){
    const viewImg = document.querySelector(".imageAva");
    const input = document.getElementById("inp_img");
    viewImg.src = "/img/defaultAva.png";
    input.value = "defaultAva.png";
}
