// функция, отвечающая за появление формы напоминания пароля
// (function(){
//     const btn = document.querySelector(".loginBtn");
//     const remindEmail = document.querySelector(".remindEmail");
//
//     btn.addEventListener("click", function () {
//         remindEmail.classList.toggle("unVisible");
//     });
// })();
//
// // функция, отображающая в зависимости от  наличия парамтра "/error" блок с предупреждением о неверно введенных данных
// (function (){
//     let credentialsWarning = document.querySelector(".authenticationWarning");
//
//     if(attention !== "")
//         console.log(attention);
//         credentialsWarning.style.display = "block";
// })()
//
// // изменение типа инпута пароля и подтверждения пароля для текстового отображения введенных данных
// const $btnShowPassword = document.querySelector(".showPassword");
// const $inputPass = document.getElementById("password");
//
// $btnShowPassword.addEventListener("click", () => {
//     if($inputPass.getAttribute('type') === 'password'){
//         $inputPass.setAttribute('type', 'text');
//         $btnShowPassword.classList.add('view');
//     } else {
//         $inputPass.setAttribute('type', 'password');
//         $btnShowPassword.classList.remove('view');
//     }
// })

let sendFormBtn = document.getElementById("sendLoginForm");
sendFormBtn.addEventListener("click", e => loginUser(e))

// функция обработки результата запроса на логгирование
function handleJsonResult(status, result){
    console.log(result);
    console.log(status);
    let authenticationWarning = document.querySelector(".authenticationWarning");
    if(status === 401){
        console.log(result);
        if(result.includes("Bad credentials"))authenticationWarning.textContent = "Неыерпный пароль";
        else if(result.includes("not exists"))
            authenticationWarning.textContent ="Пользователь с таким логином не найллен";
        else if(result.includes("not active"))
            authenticationWarning.textContent = "Ваш аккаунт не активен. Пожалуйста, активируйте его," +
                "воспользовавшись отправленной Вам на почту ссылкой. Если письмо не пришло, проверьте папку \"Спам\"" +
                "Отправить письмо повторно";
        else authenticationWarning.textContent = "Ваш аккаунт заблокирован администрацией сайта";
        authenticationWarning.style.display = "block";
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