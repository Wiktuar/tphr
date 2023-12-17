const repeatBtn = document.querySelector(".repeatBtn");

repeatBtn.addEventListener("click", () => {
    const username = document.getElementById("username");
    const endPoint = "/activate/repeat";
    const formData = new FormData();
    formData.append("email", username.value);

    fetch(endPoint, {
        method: 'post',
        body: formData
    })
        .then(response => {
          if(response.status === 200){
              attentionWindow.open();
              attentionWindow.setContent(`<p>Ссылка на форму восстановления пароля успешно отправлена на Вашу почту</p>`);
              attentionWindow.setHandLer();
          } else {
              document.querySelector(".authenticationWarning p")
                  .textContent = "Проверьте, правильно ли введен имейл в поле Логин или " +
                  "свяжитесь с Администрацией сайта";
          }
        })
        .catch(error => console.log(error));
});