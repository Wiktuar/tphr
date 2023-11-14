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
    <title>Восстановление пароля</title>
</head>
<body>
<div class="container">
    <!-- Форма создания нового пароля -->
    <div class="wrapLoginForm">
        <H2>Придумайте новый пароль</H2>
        <div class="authenticationWarning"></div>
        <form>
            <input type="hidden" id="id" name="id" value="${author_id}">
            <div class="fName">
                <label for="password">Введите пароль:</label>
                <input type="password" id="password" name="password" autofocus/>
                <span class="showPassword" data-target="password" ></span>
            </div>
            <div class="fName">
                <label for="confirm_password">Пвторите пароль:</label>
                <input type="password" id="confirm_password" name="confirm_password"/>
                <span class="showPassword" data-target="confirm_password"></span>
            </div>
            <button type="submit" id="updatePassport">Обновить пароль</button>
        </form>
    </div> <!-- end wrapLoginForm -->
</div>

<#--переменная "author_nor_found" приходит из модели. Далее она проверяется на null-->
<#--если проверка верна, то локальной переменной "X" присваиваеся пустая строка, если нет, и в переменной есть значение, -->
<#--то оно также присваивается переменной X, и потом эта переменная передается в JS код. -->
<#if author_nor_found??>
    <#assign x = author_nor_found>
<#else>
    <#assign x = "">
</#if>

<script>
    let wrongToken = "${x}"
</script>

<#-- Скрипты, относящиеся к модальному окну -->
<script src="../../static/js/modal/base.js"></script>
<script src="../../static/js/modal/plugins/modal.js"></script>
<script src="../../static/js/modal/index.js"></script>

<#-- Скрипты, относящиеся к отправке запроса на восстановление пароля   -->
<script src="../../static/js/registerControl.js"></script>
<script src="../../static/js/resetPassword.js"></script>
</body>
</html>