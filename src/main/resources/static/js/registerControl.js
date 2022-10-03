let $btnShowPassword = document.querySelectorAll(".showPassword");

// изменение типа инпута пароля и подтверждения пароля для текстового отображения введенных данных
$btnShowPassword.forEach((btn) => {
    btn.addEventListener("click", () => {
        let target = btn.getAttribute("data-target");
        console.log(target);
        let index;
        (target === "password") ? index = 0 : index = 1;

        let $inputPass = document.getElementById(target);
            if($inputPass.getAttribute('type') === 'password'){
                $inputPass.setAttribute('type', 'text');
                $btnShowPassword[index].classList.add('view');
            } else {
                $inputPass.setAttribute('type', 'password');
                $btnShowPassword[index].classList.remove('view');
            }
    })
});











// let inputPass = document.querySelector("#password");

// $btnShowPassword.addEventListener("click", () => {
//     if(inputPass.getAttribute('type') === 'password') {
//         inputPass.setAttribute('type', 'text');
//         $btnShowPassword.classList.add('view');
//     } else {
//         inputPass.setAttribute('type', 'password');
//         $btnShowPassword.classList.remove('view');
//     }
// })