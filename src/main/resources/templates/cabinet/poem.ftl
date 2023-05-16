 <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../static/css/reset.css">
    <link rel="stylesheet" href="../../static/css/poems.css">
    <title>Редактирование стихотворения</title>
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
       <h3>${poem.header}</h3>
        <div>
            <img src="/img/${poem.fileName}" class="poem_fool_image" alt="тематическая картинка">
            <div class="poem_content_container">
                <div class="poem_fool_content">
                    ${poem.content}
                </div>
            </div>
        </div>
        <img src="../../static/img/vin.png" class="vignette" alt="виньетка">
        <div class="comments_container">
            <#--Здесь будут загружаться комментарии из JavaScript -->
        </div>
        <div class="new_comment">
            <input type="hidden" id="comment_input">
            <textarea placeholder="Ваш комментарий" maxlength="700" rows="8" id="text_area"></textarea>
            <a href="#" class="send_comment_btn"><img src="../../static/img/send_message.png" class="send_comment_img" alt="Отправка сообщения"></a>
        </div>
    </div>
</div>
<#--Необходимо для получения ID стихотворения для запроса комментариев -->
<script>
    let poemID = ${poem.id};
</script>
<script src="../../static/js/comments.js"></script>
</body>
</html>