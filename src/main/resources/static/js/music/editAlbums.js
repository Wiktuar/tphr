// обновление и удаление стихотворений. Событие клика мышки вешается на контейнер
import {single, album} from "./addHTML.js";
import {sendForm, playSong, pauseSong, playAndPause, workWithProgressAudio, updateProgress, createMusicPlayers} from "./music.js";
import {addCanvas} from "./editCover.js";
import {createPlayer} from "./simplePlayer.js";
import {queryFileSize} from "./utils.js";

const albumsContainer = document.querySelector(".albums-container");

albumsContainer.addEventListener("click", e => {
    if(e.target.classList.length === 2){
        if(e.target.classList[0] === "delete_link"){
            deleteAlbum(e.target.classList[1]);
        }

        if(e.target.classList[0] === "update_link"){
            updateAlbum(e.target.classList[1]);
        }
    }
});

//функция подтверждающая удаление стихотворения
function deleteAlbum(id){
    const albumHeader = document.querySelector(`.inner_album_header${id}`);

    $.confirm({
        title: "Вы уверены?",
        content: `<p>Вы удаляете альбом <br><span style="font-weight: bold;">&laquo;${albumHeader.textContent}&raquo;</span></p>`,
    }).then(() => {
        document.location = `/cabinet/delete/album/${id}`;
    })
}

// метод получение данных для обновления с сервера
async function updateAlbum(id){
    const res = await fetch(`/cabinet/update/album/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    res.json().then(albom => {
        const fc = document.querySelector(".form_container");
        if(albom.songs.length === 1) addForm(albom, single);
        else addForm(albom, album);
    })

    window.scroll({
        top : 0,
        behavior: "smooth"
    });
}

// метод добавления формы редактирования альбома
async function addForm(album, html){
    const fc = document.querySelector(".form_container");
    if(fc.childNodes.length){
        fc.innerHTML ="";
    }

    fc.insertAdjacentHTML("beforeend", html);
    const id = document.getElementById("id");
    const albumHeader = document.querySelector("input[type=text]");
    const oldAlbumHeader = document.querySelector("input[name=old_album_header]");
    const image = document.querySelector(".cover");
    const defaultText = document.querySelector(".default_cover");
    const resultCovver = document.getElementById("result_cover_input");
    const releaseDate = document.getElementById("releaseDate");
    const listSongsBlock = document.querySelectorAll(".add_song");

    id.value = album.id;
    albumHeader.value = album.header;
    oldAlbumHeader.value = album.header;
    image.src = `/upload/${album.fileName}`;
    defaultText.textContent = "";
    resultCovver.value = album.fileName;
    releaseDate.value = album.releaseDate;
    const listOfSongs = album.songs;

    let totalFileSize = 0;

    for(let i = 0; i < listOfSongs.length; i++){
        const targetDiv = listSongsBlock[i];
        const song = listOfSongs[i];
        targetDiv.querySelector("input[type=hidden]").value = song.id;
        targetDiv.querySelector("input[type=text]").value = song.header;
        targetDiv.querySelector("input[name=song_url]").value = song.urlToMusicFile;

        // запрашиваем размер файла загруженной песни.
        let fileSize = queryFileSize(song.urlToMusicFile);

        await fileSize.then(data => totalFileSize = totalFileSize + +data);

        const fileInput = targetDiv.querySelector("input[type=file]");

        // объект создания текстового файла и добавления его в файловый инпут, чтобы понять на бэкенде,
        // был ли изменен файл песни или нет.
        const myFile = new File(['Hello World!'], `${song.urlToMusicFile}`, {
            type: 'text/plain',
            lastModified: new Date(),
        });

        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(myFile);
        fileInput.files = dataTransfer.files;

        // добавление музыкального плеера
        targetDiv.insertBefore(createPlayer(), targetDiv.querySelector("input[type=file]").previousElementSibling);
        const audio = targetDiv.querySelector(".audio");
        targetDiv.querySelector(".duration").textContent = song.duration;
        targetDiv.querySelector("input[name=duration]").value = song.duration;

        audio.src = `/upload/${song.urlToMusicFile}`;
        playAndPause(targetDiv, audio, playSong, pauseSong);
        audio.addEventListener("timeupdate", e => updateProgress(audio, targetDiv.querySelector(".progress")));
        workWithProgressAudio(targetDiv, audio);
    }

    addCanvas();
    createMusicPlayers();
    addFileListener();
    document.querySelector(".sizeofFiles")
        .textContent = `Размер загруженных файлов ${(totalFileSize / 1000000).toFixed(2)} из 75мб`;
    document.querySelector(".send_audio_btn")
        .addEventListener("click", sendForm);
}

function addFileListener(){
    const addSongs = document.querySelector(".add_songs_block");
    const fileList = addSongs.querySelectorAll("input[type=file]")
    fileList.forEach(file => file.addEventListener("change", getSileSizeUpdatedFiles))
}


// функция определяющая общий размер загруженных файлов
function getSileSizeUpdatedFiles(){
    let total = 0;
    const addSongs = document.querySelectorAll(".add_song");
    addSongs.forEach(async songBlock => {
       const file = songBlock.querySelector("input[type=file]");
       if(file.files[0]){
            if(file.files[0].type === "text/plain"){
                let songUrl = songBlock.querySelector('input[name=song_url]');

                let fileSize = queryFileSize(songUrl.value);

                await fileSize.then(data => total = total + +data);
                document.querySelector(".sizeofFiles")
                    .textContent = `Размер загруженных файлов ${(total / 1000000).toFixed(2)} из 75мб`;
            } else {
                console.log("total " + total);
                total = total + file.files[0].size;
            }
       }
    });

    document.querySelector(".sizeofFiles")
        .textContent = `Размер загруженных файлов ${(total / 1000000).toFixed(2)} из 75мб`;
}