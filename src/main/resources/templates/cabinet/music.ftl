<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../static/css/music.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Save Music</title>
</head>
<body>
<div class="container">
    <@c.cabinetButtons></@c.cabinetButtons>
    <div class="right-sb">
        <div class="choose-music">
            <div class="create-single">
                <img class="add_music_img" src="../../static/img/music/cd_disk.png" alt="создать сингл">
                <h3 class="create_music_header">Создать сингл</h3>
            </div>
            <div class="create-album">
                <img class="add_music_img" src="../../static/img/music/album.png" alt="создать сингл">
                <h3 class="create_music_header">Создать альбом</h3>
            </div>
        </div>

<#--    здесь через JS будут размещаться формы для добавления музыки    -->
        <div class="form_container">

        </div>

        <div class="albums-container">
            <#if albums?has_content>
                <#list albums as album>
                    <div class="album_box">
                        <div class="album_box_header">
                            <div class="album_header">
                            <#-- .inner_album_header испльзуется в js коде для поиска элемента.  -->
                            <#-- .ah отвечает непосредственно за стиль заголовка.  -->
                                <div class="inner_album_header${album.id} ah">${album.header}</div>
                            </div>
                            <div class="author_block">
                                <img src="../../static/img${album.authorDTO.pathToAvatar}" class="avatar" alt="аватар автора">
                                <h2 class="author-name">${album.authorDTO.firstName} ${album.authorDTO.lastName}</h2>
                            </div>
                        </div>


                        <div class="album_box_body">
                            <img src="/img/${album.fileName}" class="album-image" alt="тематическая картинка">
                            <div class="album_content">
                                <#list album.songs as song>
                                    <div class="player">
                                        <div class="title">${song.header}</span></div>
                                        <div class="meta-data">
                                            <div class="current_time">00:00</div>
                                            <audio class="audio" src="/music/${album.songPreview}" preload="metadata" data-status="pause"></audio>
                                            <div class="buttons">
                                                <div class="btn play"><img class="img_src" src="../../static/img/musicButtons/play.png" alt="play png"></div>
                                            </div>
                                            <div class="duration">${song.duration}</div>
                                        </div>
                                        <div class="progress_container">
                                            <div class="progress"></div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                            <a href="/cabinet/album/${album.id}">
                                <div class="fool_reading">слушать полностью</div>
                            </a>
                        </div>

                        <div class="album_box_footer">
                            <div class="like_comment">
<#--                                <#if poem.meLiked>-->
                                    <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
<#--                                <#else>-->
<#--                                    <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>-->
<#--                                </#if>-->
<#--                                <span class="digit">${poem.likes}</span>-->
                                    <img src="../../static/img/comments.png" class="comment" alt="комментарий">
<#--                                <span class="digit">${poem.comments}</span>-->
                            </div>
                            <div class="time-stamp">
                                ${album.releaseDate?truncate(11, "")}
                            </div>
                        </div>
                        <div class="footer_buttons">
                            <span class="update_link ${album.id}" >Обновить</span>
                            <span class="delete_link ${album.id}" >Удалить</span>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="empty_list">
                    Здесь пока ничего нет. <br> Ждем Вашего творчества!
                </div>
            </#if>
        </div>
    </div>
</div>
<script type="module" src="../../static/js/music/music.js"></script>
</body>
</html>