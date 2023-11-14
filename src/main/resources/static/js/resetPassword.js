//переменные для получения значений полей формы
const id = document.getElementById("id")
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirm_password");
const credentialsWarning = document.querySelector(".authenticationWarning");
const submit = document.getElementById("updatePassport");

//переменная приходит с шаблона. Блок кода сработает, когда пользователь вводит токен
//которого нет в базе.
if(wrongToken !== ""){
    mistakeMethod(wrongToken);
    password.oninput = e => {
        e.target.value = ''; // не важно, что ввели, значение всегда пустое будет
    }
}

//метод, который вызывается при получении ошибки
function mistakeMethod(mistake) {
    credentialsWarning.style.display = "block";
    credentialsWarning.textContent = mistake;
}

//метод, проверяющий различные поля формы
function _checkFormFields(){
    const checkPersonPattern = /[^А-Яа-яЁё]/;

    if(!checkPersonPattern.test(password.value) || password.value.includes(" ")){
        mistakeMethod('В поле "Пароль" присутствуют русские символы или есть пробел');
        return true;
    }

    if(!checkPersonPattern.test(confirmPassword.value) || confirmPassword.value.includes(" ")){
        mistakeMethod('В поле "Повторите пароль" присутствуют русские символы или есть пробел');
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

submit.addEventListener("click", e=> {
    e.preventDefault();
    if(_checkFormFields()) return;

    const endPoint = "/reset/password";
    const formData = new FormData();
    formData.append("id", id.value);
    formData.append("password", password.value);

    fetch(endPoint, {
        method: "post",
        body: formData
    })
        .then(response => {
           if (response.status === 200){
                attentionWindow.open();
                attentionWindow.setContent(`<p>Пароль успешно изменен</p>`);
                attentionWindow.setHandLer();
            }
        })
        .catch(error => console.error(error));
})