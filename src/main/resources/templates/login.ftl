<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Вход на сайт</title>
</head>
<body>
    <H2>Добро пожаловать!</H2>
    <form method="post" action="">
        <div>
            <label for="username">Ваша почта</label>
            <input type="email" id="username" name="username" autofocus/>
            <label for="password">Ваш пароль</label>
            <input type="password" id="password" name="password"/>
            <a href="#">Я забыл(-а) пароль</a>
            <p> К сожалению, пользователь с такием <strong>email</strong> не найден</p>
            <button type="submit">Войти на сайт</button>
        </div>
    </form>
</body>
</html>