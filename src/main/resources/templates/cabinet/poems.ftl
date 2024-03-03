<#--http://tradebenefit.ru/kak-raspolozhit-neskolko-div-v-ryad-css-->
<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../static/css/reset.css">
    <link rel="stylesheet" href="../../static/css/poems.css">
    <link rel="stylesheet" href="../../static/css/modal.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Мои стихи</title>
</head>
<body>
<div class="container">
<#-- импорт левого сайдбара с кнопками меню личного кабинета   -->
    <@c.cabinetButtons></@c.cabinetButtons>
    <div class="right-sb">
<#--  кнопка добавления стихотврения      -->
        <button type="button" class="add_poem_btn">Добавить стихотворение</button>
<#--  Форма добавления стихотворения      -->
        <div class="add_poem_box">
            <form method="post" action="/cabinet/poems" enctype="multipart/form-data">
                <input type="hidden" name="id" value="0">
<#--    Поскольку картинка может измениться, на сервер перадаем и имя файла старой картинки, чтобы ее можно было удалить              -->
                <input type="hidden" class="old_file_name" name="oldFileName">
                <input type="hidden" class="release_date_input" name="releaseDate">
                <input type="text" name="header" placeholder="Введите назввание стизотворения">
                <textarea name="content" placeholder="Введите текст стихотворения"></textarea>
                <div class="view_cover">
                    <img src="../../static/img/poemCover.jpg" id="image">
                    <span class="default_cover">*Это изображение будет установлено по умолчанию. Лучше его заменить.</span>
                </div>
                <div class="fileInput">
                    <label for="file">Загрузите картинку для стихотворения</label>
                    <input type="file" id="file" name="file" accept="image/*">
                </div>
                <input type="submit" class="savePoem" value="Сохранить стизотворение">
            </form>
        </div>
<#--   flex контейнер. а внутри него стихотворения   -->
        <div class="poems-container">
            <#if poems?has_content>
                <#list poems as poem>
                    <div class="poem_box">
                        <div class="poem_box_header">
                            <div class="poem_header">
                                <#-- .inner_poem_header испльзуется в js коде для поиска элемента.  -->
                                <#-- .ph отвечает непосредственно за стиль заголовка.  -->
                                <div class="inner_poem_header${poem.id} ph">${poem.header}</div>
                            </div>
                            <div class="author_block">
                                <img src="../../static/img${poem.pathToAvatar}" class="avatar" alt="аватар автора">
                                <h2 class="author-name">${poem.firstName} ${poem.lastName}</h2>
                            </div>
                        </div>

                        <div class="poem_box_body">
                            <img src="/img/${poem.fileName}" class="poem-image" alt="тематическая картинка">
                            <div class="poem_content">
                                ${poem.poemPreview}
                            </div>
                               <a href="/cabinet/poem/${poem.id}"><div class="fool_reading">
                                читать полностью
                            </div></a>
                        </div>

                        <div class="poem_box_footer">
                            <div class="like_comment">
                                <#if poem.meLiked>
                                    <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
                                <#else>
                                    <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>
                                </#if>
                                <span class="digit">${poem.likes}</span>
                                <img src="../../static/img/comments.png" class="comment" alt="комментарий">
                                <span class="digit">${poem.comments}</span>
                            </div>
                            <div class="time-stamp">
                                ${poem.releaseDate?truncate(11, "")}
                            </div>
                        </div>
                        <div class="footer_buttons">
                            <span class="update_link ${poem.id}" >Обновить</span>
                            <span class="delete_link ${poem.id}" >Удалить</span>
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
<#-- Скрипты, относящиеся к модальному окну -->
<script src="../../static/js/modal/base.js"></script>
<script src="../../static/js/modal/plugins/modal.js"></script>
<script src="../../static/js/modal/plugins/confirm.js"></script>
<script src="../../static/js/modal/index.js"></script>
<#-- Скрипт, относящийся непосредственно к странице-->
<script src="../../static/js/poems.js"></script>
</body>
</html>