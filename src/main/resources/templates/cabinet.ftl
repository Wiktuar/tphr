<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../static/css/cabinet.css">
    <link rel="stylesheet" href="../static/css/reset.css">
    <title>Личный кабинет</title>
</head>
<body>
<div class="container">
    <div class="left-sb">
        <ul>
            <li class="menu-item"><a href="/person">Личные данные</a></li>
            <li class="menu-item"><a href="/poems">Мои стихи</a></li>
            <li class="menu-item"><a href="/tails">Моя проза</a></li>
            <li class="menu-item"><a href="/music">Моя музыка</a></li>
            <li class="menu-item"><a href="/draws">Мои рисунки</a></li>
        </ul>
    </div>

    <div class="right-sb">
        <#if author.activationCode??>
            <p class="attention">Пожалуйста, подтвердите Вашу почту, чтобы стать автором на нашем сайте.</p>
        </#if>
        <div class="flex-wrapper">
            <img src="../static/img/${author.pathToAvatar}"  class="user-photo" alt="Фотография профиля">
            <div class="personal-data">
                <h2>${author.firstName} ${author.lastname}</h2>
                <div class="email-block">
                    ${author.email}
                </div>
                <p>Мои социальные сети:</p>
                <div class="flex-social-nets">
                    <ul class="social-nets">
                        <#if author.vk??>
                            <li><img src="../static/img/vk.png" title="страница Вконтакте" alt="страница Вконтакте"></li>
                        </#if>
                        <#if author.tg??>
                            <li><img src="../static/img/tg.svg" title="канал в Telegram" alt="канал в Telegram"></li>
                        </#if>
                        <#if authir.yt??>
                            <li><img src="../static/img/yt.png" title="канал на youTUbe" alt="канал на youTUbe"></li>
                        </#if>
                    </ul>
                    <a href="" class="change-data">Изменить данные</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../static/js/cabinet.js"></script>
</body>
</html>