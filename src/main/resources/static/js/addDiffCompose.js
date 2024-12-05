import {createMusicPlayersForAlbums} from "./music/music.js";

//получение элементов страницы
const poemBtn = document.getElementById("poem_btn");
const proseBtn = document.getElementById("prose_btn");
const musicBtn = document.getElementById("music_btn");
const drawBtn = document.getElementById("draw_btn");
const compContainer = document.querySelector(".compose_container")

poemBtn.addEventListener("click", () => getPoems(authorId));
musicBtn.addEventListener("click", () => getAlbums(authorId));

//получение стихотворений автора
async function getPoems(id){
    const res = await fetch(`/authors/${id}/poems`);
    if (!Number(res.headers.get("X-Total-Count"))){
        compContainer.textContent = "Здесь пока ничего нет :((";
        return;
    }
    const poems = await res.json();
    compContainer.classList.remove("default");
    compContainer.innerHTML = "";
    poems.forEach(poem => poemToHTML(poem));
}

//получение альбомов автора
async function getAlbums(id){
    const res = await fetch(`/authors/${id}/albums`);
    if (!Number(res.headers.get("X-Total-Count"))){
        compContainer.textContent = "Здесь пока ничего нет :((";
        return;
    }
    const albums = await res.json();
    compContainer.innerHTML = "";
    compContainer.classList.remove("default");
    albums.forEach(album => musicToHTML(album));
    createMusicPlayersForAlbums(compContainer);
}

proseBtn.addEventListener("click", defaultContent);
drawBtn.addEventListener("click", defaultContent);

// функция дефолтного контента для прозы и рисунков, которых пока нет
function defaultContent(){
    compContainer.classList.add("default");
    compContainer.textContent = "Здесь пока ничего нет :((";
}



//функция, внедряющая стихотворение в HTML разметку
function poemToHTML(poem){
    poem.releaseDate = poem.releaseDate.substring(0, 10);
    let isLike = poem.meLiked ? `<i class="fa-solid fa-heart" style="color: #e60f0f;"></i>` :
        `<i class="fa-regular fa-heart" style="color: #e60f0f;"></i>`;
    compContainer.insertAdjacentHTML("beforeend",
        `<div class="poem_box">
                    <div class="poem_box_header">
                        <div class="poem_header">
                            <div class="inner_poem_header${poem.id} ph">${poem.header}</div>
                        </div>
                        <div class="author_block">
                            <img src="/upload/${poem.pathToAvatar}" class="avatar" alt="аватар автора">
                            <h2 class="author-name">${poem.firstName} ${poem.lastName}</h2>
                        </div>
                    </div>

                    <div class="poem_box_body">
                        <img src="/upload/${poem.fileName}" class="poem-image" alt="тематическая картинка">
                        <div class="poem_content">
                            ${poem.poemPreview}
                        </div>
                           <a href="/main/poem/${poem.id}"><div class="full_reading">
                            читать полностью
                        </div></a>
                    </div>

                    <div class="poem_box_footer">
                        <div class="like_comment">
                            ${isLike}
                            <span class="digit">${poem.likes}</span>
                            <img src="/img/comments.png" class="comment" alt="комментарий">
                            <span class="digit">${poem.comments}</span>
                        </div>
                        <div class="time-stamp">
                            ${poem.releaseDate}
                        </div>
                    </div>
             </div>`)
}

// функция, внедряющая музыку в HTML разметку
function musicToHTML(album){
    album.releaseDate = album.releaseDate.substring(0, 10);
    let isLike = album.meLiked ? `<i class="fa-solid fa-heart" style="color: #e60f0f;"></i>` :
        `<i class="fa-regular fa-heart" style="color: #e60f0f;"></i>`;
    compContainer.insertAdjacentHTML("beforeend",
        `<div class="album_box">
                <div class="album_box_header">
                    <div class="album_header">
                        <div class="inner_album_header${album.id} ah">${album.header}</div>
                    </div>
                    <div class="author_block">
                        <img src="/upload/${album.pathToAvatar}" class="avatar" alt="аватар автора">
                        <h2 class="author-name">${album.firstName} ${album.lastName}</h2>
                    </div>
                </div>


                <div class="album_box_body">
                    <img src="/upload/${album.fileName}" class="album-image" alt="тематическая картинка">
                    <div class="album_content">
                       <div class="player">
                            <div class="first_song_title">${album.song.header}</span></div>
                            <div class="meta-data">
                                <div class="current_time">00:00</div>
                                <audio class="audio" src="/upload/${album.song.urlToMusicFile}" preload="metadata" data-status="pause"></audio>
                                <div class="buttons">
                                    <div class="btn play"><img src="/img/musicButtons/play.png" class="img_src" alt="play png"></div>
                                </div>
                                <div class="duration">${album.song.duration}</div>
                            </div>
                            <div class="progress_container">
                                <div class="progress"></div>
                            </div>
                        </div>
                    </div>
                    <a href="/main/music/${album.id}">
                        <div class="full_learning">слушать полностью</div>
                    </a>
                </div>

                <div class="album_box_footer">
                    <div class="like_comment">
                        ${isLike}
                        <span class="digit">${album.likes}</span>
                            <img src="/img/comments.png" class="comment" alt="комментарий">
                        <span class="digit">${album.comments}</span>
                    </div>
                    <div class="time-stamp">
                        ${album.releaseDate}
                    </div>
                </div>
            </div>`)
}

