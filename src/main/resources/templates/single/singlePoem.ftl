<#assign known = SPRING_SECURITY_CONTEXT??>
<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../../static/css/reset.css">
    <link rel="stylesheet" href="../../../static/css/common.css">
    <link rel="stylesheet" href="../../../static/css/single/singlePoem.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Стихотворение</title>
</head>
<body>
<#--pаголовок сайта-->
<@c.header></@c.header>
<#--блок с основным контентом-->
<main>
    <h1>${poem.header}</h1>
    <img src="/img/${poem.fileName}" class="poem_fool_image" alt="тематическая картинка">
    <div class="poem_content_container">
        <div class="poem_fool_content">
            ${poem.content}
        </div>
    </div>
    <div class="like_comment">
        <span class="like_btn"></span>
        <span class="p_digit_l">${poem.likes}</span>
        <img src="../../static/img/comments.png" class="p_comment" alt="комментарии">
        <span class="p_digit_c">${poem.comments}</span>
        <div class="enter_for_like">
            <h3>Понравилось произведение?</h3>
            <p>Войдите в аккаунт, чтобы поставить отметку</p>
            <a href="" class="login">Войти</a>
        </div>
    </div>
    <img src="../../static/img/vin.png" class="vignette" alt="виньетка">
    <div class="comments_container">
        <#--Здесь загружаются комментарии из JavaScript -->
    </div>
    <#if known>
        <div class="new_comment">
            <input type="hidden" id="comment_input">
            <textarea placeholder="Ваш комментарий" maxlength="700" rows="8" id="text_area"></textarea>
            <a href="#" class="send_comment_btn"><img src="../../static/img/send_message.png" class="send_comment_img" alt="Отправка сообщения"></a>
        </div>
    <#else>
        <p class="enter_for_comment">Пожалуйста, авторизуйтесь, чтобы сотавить комментарий!</p>
    </#if>
</main>
<@c.footer></@c.footer>
<#--Необходимо для получения ID стихотворения для запроса комментариев -->
<script>
    let poemID = ${poem.id};
    let meLiked = ${poem.meLiked?string("1", "0")};
    // если делать здесь не числовами, а словами "yes", "no", то выдает ошибку
    let knownUser = ${known?string('1', '0')};
</script>
<script src="../../static/js/comments.js"></script>
<script src="../../static/js/likes.js"></script>
<script src="../../static/js/url.js"></script>
</body>
</html>