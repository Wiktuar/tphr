<#--макрос заголовка сайта-->
<#macro header>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <header>
        <div class="logo-block">
            <img src="../static/img/logo.jpg" class="logo" alt="Логотип">
            <h2>Творчество православных христиан</h2>
        </div>
        <div class="search-block">
            <a href="#" class="authors">Авторы</a>
            <span class="search">Поиск по сайту</span>
        </div>
        <#if known>
            <div class="user-data">
                <button type="button" class="cabinet-button">
                    <img src="../static/img/defaultAva.png" class="user-pic" alt="аватар пользователя"> Виктор
                </button>
                <div class="dropdown-box">
                    <a href="/cabinet">Личные данные</a>
                    <a href="/cabinet/getAll">Мои стихи</a>
                    <a href="#">Моя проза</a>
                    <a href="#">Моя музыка</a>
                    <a href="#">Мои рисунки</a>
                    <a href="/logout">Выйти</a>
                </div>
            </div>
        <#else>
            <div class="login-block">
                <a href="/login" class="enter">Вход</a>
                <a href="/registration" class="registration">Регистрация</a>
            </div>
        </#if>
    </header>
</#macro>

<#--макрос футера сайта-->
<#macro footer>
    <footer>

    </footer>
</#macro>