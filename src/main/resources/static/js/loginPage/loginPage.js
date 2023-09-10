// функция, отвечающая за появление формы напоминания пароля
(function(){
    const btn = document.querySelector(".loginBtn");
    const remindEmail = document.querySelector(".remindEmail");

    btn.addEventListener("click", function () {
        remindEmail.classList.toggle("unVisible");
    });
})();

// функция, отображающая в зависимости от  наличия парамтра "/error" блок с предупреждением о неверно введенных данных
(function (){
    let credentialsWarning = document.querySelector(".credentialsWarning");

    if(attention !== "")
        console.log(attention);
        credentialsWarning.style.display = "block";
})()

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



