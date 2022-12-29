//кнопка отправки фориы
const $regBtn = document.getElementById("regBtn");

//переменные для получения значений полей формы
const firstName = document.getElementById("firstName");
const lastName = document.getElementById("lastName");
const email = document.getElementById("email");
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirm_password");
const imageAva = document.getElementById("inp_img");
const vk = document.getElementById("vk");
const tg = document.getElementById("tg");
const yt = document.getElementById("yt");

//метод, который вызывается при получении ошибки
function mistakeMethod(mistake) {
    attentionWindow.open();
    attentionWindow.setTitle(" ")
    attentionWindow.setContent(`<p>${mistake}</p>`);
}

//метод, проверяющий различные поля формы
function _checkFormFields(){
    const checkPersonPattern = /[^А-Яа-яЁё]/;
    const checkEmailPattern = /[^0-9A-Za-z@.]/;
    const checkEmptyPattern = /^\s*$/;
    // const checkSpacePattern = /[^\s]/;

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

    if(checkEmptyPattern.test(password.value)){
        mistakeMethod('Поле "Пароль" пустое');
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

    if(!checkPersonPattern.test(password.value) || password.value.includes(" ")){
        mistakeMethod('В поле "Пароль" присутствуют русские символы или есть пробел');
        return true;
    }

    if(password.value.length < 8 || password.value.length > 15) {
        mistakeMethod('Пароль должен быть от 8 до 15 символов');
        return true;
    }

    if(password.value !== confirmPassword.value){
        mistakeMethod('Пароли не совпадают');
        return true;
    }
}

$regBtn.addEventListener("click", e => {
    e.preventDefault();
    if(_checkFormFields())return;

    const endPoint = "/check";
    const formData = new FormData();
    formData.append("firstName", firstName.value);
    formData.append("lastName", lastName.value);
    formData.append("email", email.value);
    formData.append("password", password.value);
    formData.append("pathToAvatar", imageAva.value);
    formData.append("vk", vk.value);
    formData.append("tg", tg.value);
    formData.append("yt", yt.value);

    fetch(endPoint, {
        method: "post",
        body: formData
    })
        .then(response => {
            if(response.status === 400) {
                attentionWindow.open();
                attentionWindow.setContent(`<p>Аккаунт с почтой <span style="red">${email.value}</span> уже занят</p>`);
            } else if (response.status === 200){
                attentionWindow.open();
                attentionWindow.setTitle(`<p style="text-align: center; font-size: 20px">Поздравляем!</p>`);
                attentionWindow.setContent(`<p>Вы успешно зарегистрированы. 
                        На Ваш email мы направили письмо с ссылкой для активации Вашего аккаунта</p>`);
                attentionWindow.setHandLer();
            }
        })
        .catch(error => console.error(error));
})