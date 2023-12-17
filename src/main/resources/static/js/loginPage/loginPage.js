// функция, отвечающая за появление формы напоминания пароля
 (function(){
    const btn = document.querySelector(".loginBtn");
    const remindEmail = document.querySelector(".remindEmail");

    btn.addEventListener("click", function () {
        remindEmail.classList.toggle("unVisible");
    });
})();


// изменение типа инпута пароля и подтверждения пароля для текстового отображения введенных данных
const $btnShowPassword = document.querySelector(".showPassword");
const $inputPass = document.getElementById("password");


$btnShowPassword.addEventListener("click", () => {

    if($inputPass.getAttribute('type') === 'password'){
        $inputPass.setAttribute('type', 'text');
        $btnShowPassword.classList.add('view');
    } else {
        $inputPass.setAttribute('type', 'password');
        $btnShowPassword.classList.remove('view');
    }
})

let sendFormBtn = document.getElementById("sendLoginForm");
sendFormBtn.addEventListener("click", e => loginUser(e));

// функция обработки результата запроса на логгирование
function handleJsonResult(status, result){
    let authenticationWarning = document.querySelector(".authenticationWarning");
    let authMessage = document.querySelector(".authMessage");
    let repeatBtn = document.querySelector(".repeatBtn");
    if(status === 401){
        if(result.includes("Bad credentials")){
            authenticationWarning.style.display = 'block';
            authMessage.textContent = "Неыерпный пароль";
        }
        else if(result.includes("not exists")){
            authenticationWarning.style.display = 'block';
            authMessage.textContent ="Пользователь с таким логином не найллен";
        }
        else if(result.includes("is disabled")){
            authenticationWarning.style.display = 'block';
            repeatBtn.style.display = 'block';
            authMessage.textContent = "Ваш аккаунт не активен";
        }
        else {
            authenticationWarning.style.display = 'block';
            authMessage.textContent = "Ваш аккаунт заблокирован администрацией сайта";
        }
    } else {
        window.location.href = result;
    }
}

async function loginUser(e){
    e.preventDefault();
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let rememberMe = document.getElementById("remember")

    const formData = new FormData();
    formData.append("username", username.value);
    formData.append("password", password.value);
    formData.append("remember-me", rememberMe.checked);
    const result = await fetch("/login", {
        method: 'POST',
        body: formData
    });

    result.json().then(str => handleJsonResult(result.status, str));
}