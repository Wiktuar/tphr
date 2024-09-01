//https://doka.guide/js/form-data/
import {player} from "./simplePlayer.js";
import {toMinAndSec, getAttentionDiv} from "./utils.js";
import {album, single} from "./addHTML.js";
import {addCanvas} from "./editCover.js";


// по событию загрузки файла или его отмене происходит либо добавение аудиоплеера
// или его удаление
function createMusicPlayers(){
   const addSongBlock = document.querySelector(".add_songs_block");
   const files = addSongBlock.querySelectorAll("input[type=file]");
   files.forEach(file => {
      file.addEventListener("change", () => {
         // случай, когда файл добавляется
         if(file.files[0] !== undefined){
            // случай, когда в плеере уже есть файл и его надо обновить
            if(file.parentNode.querySelector(".player") !== null){
               const audio = file.parentNode.querySelector(".audio");
               audio.src = URL.createObjectURL(file.files[0]);
               audio.addEventListener("loadedmetadata", () => {
                  file.parentNode.querySelector(".duration").textContent = toMinAndSec(audio.duration);
               })
               file.parentNode.querySelector(".progress").style.width = "0px";
               pauseSong(audio.parentNode.querySelector(".img_src"), audio);
            } else {
               // случай, когда мы создаем новый плеер и внего загружаем аудиофайл
               file.parentNode.insertAdjacentHTML('beforeend', player);
               const progress = file.parentNode.querySelector(".progress");
               const audio = file.parentNode.querySelector(".audio");
               audio.src = URL.createObjectURL(file.files[0]);
               audio.addEventListener("loadedmetadata", () => {
                  file.parentNode.querySelector(".duration").textContent = toMinAndSec(audio.duration);
               })
               playAndPause(file.parentNode, audio, playSong, pauseSong);
               workWithProgressAudio(file.parentNode, audio);
               audio.addEventListener("timeupdate", e => updateProgress(audio, progress));
            }
         } else {
            // случай, когда пользователь ничего не выбрал
            const player = file.parentNode.querySelector(".player");
            file.parentNode.removeChild(player);
         }
      })
   });
}

// функция, запускающие проигрывание и остановку музыки.
function playAndPause(element, audio, playSong, pauseSong){
    const playBtn = element.querySelector(".btn");
    const imgSrc = element.querySelector(".img_src");
    playBtn.addEventListener("click", ()=>{
      let isPlaying = audio.dataset.status === "play";
      !isPlaying?playSong(imgSrc, audio, pauseSong) : pauseSong(imgSrc, audio);
   });
}

//play
function playSong(imgSrc, audio, pauseSong){
   audio.dataset.status = "play";
   // со страницы получаются все имеющиеся теги audio, а потом
   // в цикле, у тех тегов, что не равны тому, на которомм происходит работв, останавливается звук
   const audios = document.getElementsByTagName("audio");
   for (const audio1 of audios) {
      if(audio1 !== audio){
         pauseSong(audio1.parentNode.querySelector(".img_src"), audio1);
      }
   }
   imgSrc.src = "/img/musicButtons/pause.png";
   audio.play();
}

//pause
function pauseSong(imgSrc, audio){
   audio.dataset.status = "pause";
   imgSrc.src = "/img/musicButtons/play.png";
   audio.pause();
}


function workWithProgressAudio(element, audio){
   const progressContainer = element.querySelector(".progress_container");;
   progressContainer.addEventListener("click", e => setProgress(e, audio, progressContainer));
}

//функция для перемотки аудио
function setProgress(e, audio, pc){
   const width = pc.clientWidth;
   let clickX = e.offsetX;
   let duration = audio.duration;
   audio.currentTime = clickX / width * duration;
}

// функция дял отображения прогрееса звучания аудио
function updateProgress(audio, progress){
   let duration = audio.duration;
   let currentTime = audio.currentTime;
   let progressPercent = (currentTime / duration) * 100;
   progress.style.width = `${progressPercent}%`;
   audio.previousElementSibling.textContent = toMinAndSec(audio.currentTime);
}


// добавление формы отправки музыки в зависимости от ттого
// что пользователю нужен сингл или альбом.
function addForm(button, html){
    button.addEventListener("click", () => {
       const fc = document.querySelector(".form_container");
       if(fc.childNodes.length){
         fc.innerHTML ="";
       }

      fc.insertAdjacentHTML("beforeend", html);
      createMusicPlayers();
      addCanvas();
      document.querySelector(".send_audio_btn")
          .addEventListener("click", sendForm);
    })
}

// метод отправки формы на сервер
function sendForm(){
   let hasMistakes = checkCompletionForm(document.querySelectorAll(".add_song"));
   if(hasMistakes) return;
   console.log("Форма отправлена на сохранение");
    
   const formData = new FormData(document.getElementById("music_form"));
   fetch("/savemusic", {
       method: "POST",
       body: formData
   })
       .then(response => {
            if(response.status === 200) window.location.reload();
            else if(response.status === 422){
                let form = document.getElementById("music_form");
                const sendBtn = document.querySelector(".send_audio_btn");
                form.insertBefore(getAttentionDiv("Вы eже ранее создавали альбом с таким названием!"), sendBtn);
            }
            else console.log("Не удалось сохранить альбом");
       }).catch(error => console.log(error));

}

//функция проверки запонения форммы на сайте
function checkCompletionForm(element){
    let form = document.getElementById("music_form");
    const sendBtn = document.querySelector(".send_audio_btn");
    const headerText = document.querySelector(".header_image input[type=text]");
    let hasMistakes = false;
    let emptyForm = true;
    let emptySong = false;

    // удаление имеющихся предупреждений
    let attentionList = document.getElementsByClassName("attention");
    Array.from(attentionList)
        .forEach(el => el.remove());

    //проверка заголовка на заполнение
    if(!headerText.value){
        form.insertBefore(getAttentionDiv("Вы не добавили заголовок для альбома"), sendBtn);
        hasMistakes = true;
        return hasMistakes;
    }

    Array.from(element)
        .forEach(el => {
            const text = el.querySelector("input[type=text]");
            const file = el.querySelector("input[type=file]");

            // удаление имеющихся предупреждений
            if(el.firstChild.classList !== undefined){
                el.removeChild(el.firstChild);
            }

            if((text.value && !file.files.length) || (!text.value && file.files.length)){
                if(el.firstChild.classList === undefined){
                    const div = document.createElement('div');
                    div.className = "alert";
                    div.innerHTML = `<img src="/img/music/attention.png" class="alert_img" alt="Восклицательный знак">`;
                    el.prepend(div);
                    emptySong = true;
                    hasMistakes = true;
                }
            }

            if(text.value || file.files.length) emptyForm = false;
        });

    if(emptyForm) {
        form.insertBefore(getAttentionDiv("Вы не добавили ни одной песни в альбом!"), sendBtn);
        hasMistakes = true;
    }

    if(emptySong) {
        form.insertBefore(getAttentionDiv("Вы где-то забыли добавить название песни или сам трек!"), sendBtn);
        hasMistakes = true;
    }

    return hasMistakes;
}

// функции для работы с карточками уже имеющихся альбомов
export function createMusicPlayersForAlbums(container){
    const players = container.querySelectorAll(".player");
    players.forEach( pl => {
        const progress = pl.querySelector(".progress")
        const audio = pl.querySelector(".audio");
        playAndPause(pl, audio, playSong, pauseSong);
        workWithProgressAudio(pl, audio);
        audio.addEventListener("timeupdate", e => updateProgress(audio, progress));
        audio.addEventListener("ended", ()=> {
            progress.style.width = "0%";
            pauseSong(pl.querySelector(".img_src"), audio);
        })
    })
}

function runFunction(){
    if(window.location.href === "http://localhost:8070/cabinet/music"){
        createMusicPlayersForAlbums(document.querySelector(".albums-container"));
        addForm(document.querySelector(".create-single"), single);
        addForm(document.querySelector(".create-album"), album);
    }
}

runFunction();

