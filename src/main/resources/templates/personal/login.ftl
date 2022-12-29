<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../../static/css/reset.css">
    <link rel="stylesheet" href="../../static/css/loginPage.css"">
    <link rel="stylesheet" href="../../static/css/modal.css">
    <title>Вход на сайт</title>
</head>
<body>
    <div class="container">
        <!-- Форма логирования в личном кабинете -->
        <div class="wrapLoginForm">
            <H2>Добро пожаловать!</H2>
                <div class="credentialsWarning"> К сожалению, пользователь с такими учетными данными не найден!
                    Пожалуйста, попробуйте еще раз</div>
            <form method="post" action="/login">
                <div class="fName">
                    <label for="username">Ваша почта:</label>
                    <input type="email" id="username" name="username" autofocus/>
                </div>
                <div class="fName">
                    <label for="password">Ваш пароль:</label>
                    <input type="password" id="password" name="password"/>
                    <span class="showPassword" data-target="password" ></span>
                </div>
                <button type="submit" id="sendLoginForm">Войти на сайт</button>
             </form>
        </div> <!-- end wrapLoginForm -->

    <#--Блок напоминания пароля-->
        <div class="remindEmail unVisible">
            <form method="post" action="/reset/remindPassword" class="remindForm">
                <label for="email">Укажите Вашу почту для восстановления пароля<br></label>
                <input type="email" id="email" name="email">
                <button type="submit" class="remindBtn">Напомнить пароль</button>
            </form>
        </div><!-- end remindEmmail -->
        <#--атрибут "href" удален, поскольку при пустом его значении не отображается окно напоминания пароля -->
        <a class="loginBtn">Я забыл(-а) пароль</a>
    </div>

<#-- Скрипты, относящиеся к модальному окну -->
<script src="../../static/js/modal/base.js"></script>
<script src="../../static/js/modal/plugins/modal.js"></script>
<script src="../../static/js/modal/index.js"></script>

<#-- Скрипты, относящиеся к отправке запроса на восстановление пароля   -->
<script src="../../static/js/loginPage/loginPage.js"></script>
<script src="../../static/js/loginPage/remindPassword.js"></script>
</body>
</html>