<#import "common.ftl" as c>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="../static/css/reset.css">
  <link rel="stylesheet" href="../static/css/common.css">
  <link rel="stylesheet" href="../static/css/poems.css">
  <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
  <title>Главная страница</title>
</head>
<body>
<#--pаголовок сайта-->
<@c.header></@c.header>
<#--блок с основным контентом-->
  <main>
    <div class="poems-container">
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
            <a href="/main/poem/${poem.id}"><div class="fool_reading">
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
        </div>
      </#list>
    </div>
  </main>
<#--блок добавления футера-->
<@c.footer></@c.footer>
<script src="../static/js/main.js"></script>
</body>
</html>