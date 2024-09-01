<#import "../common.ftl" as c>
<#assign known = SPRING_SECURITY_CONTEXT??>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/static/css/music.css">
    <link rel="stylesheet" href="/static/css/common.css">
    <link rel="stylesheet" href="/static/css/common/likesAndComments.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Музыкальный альбом</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="cabinet_container">
        <@c.cabinetButtons></@c.cabinetButtons>
        <div class="right-sb">
            <#if album.firstName??>
                <img src="../../static/img${album.pathToAvatar}">
                <div class="author">${album.firstName} ${album.lastName}</div>
            </#if>
            <div class="wrapper">
                <div class="player">
                    <div class="cover"><img src="/img/${album.fileName}" class="cover_img" alt="картинка песни"></div>
                    <div class="meta-data">
                        <div class="title_time">
                            <div class="title">Smoke on thw water</div>
                            <div class="time"><span class="current_time">02:38</span> / <span class="full_time">05:35</span></div>
                        </div>
                        <audio class="audio" preload="metadata"></audio>
                        <div class="progress_container">
                            <div class="progress"></div>
                        </div>
                        <div class="buttons">
                            <div class="btn prev"><img class="img_src" src="../../static/img/music/buttons/previous.png" alt="prev png"></div>
                            <div class="btn play"><img class="img_src playing" src="../../static/img/music/buttons/play.png" alt="play png"></div>
                            <div class="btn next"><img class="img_src" src="../../static/img/music/buttons/next.png" alt="next png"></div>
                        </div>
                    </div>
                </div>
                <div class="song_list">
                    <!--    Сюда будет приходить контент из js       -->
                </div>
            </div>
            <div class="like_comment">
                <span class="like_btn"></span>
                <span class="p_digit_l">${album.likes}</span>
                <img src="../../static/img/comments.png" class="p_comment" alt="комментарии">
                <span class="p_digit_c">${album.comments}</span>
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
                Пожалуйста, зарегистрируйтесь!
            </#if>
        </div>
    </div>

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
    <#--Необходимо для получения ID стихотворения для запроса комментариев -->
    <script>
        let compID = ${album.id};
        let meLiked = ${album.meLiked?string("1", "0")};
        let knownUser = ${known?string('1', '0')};
    </script>
    <script type="module" src="../../static/js/music/albumPlayer.js"></script>
    <script src="../../static/js/comments.js"></script>
    <script src="../../static/js/likes.js"></script>
</body>
</html>