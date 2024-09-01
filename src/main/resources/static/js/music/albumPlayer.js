//функция получат все песни альбоа по его ID
import {toMinAndSec} from "./utils.js";
import {removeAdditionalClass} from "./utils.js";

async function getAllSongs(id){
    const res = await fetch(`/cabinet/songs/${id}`);
    return await res.json();
}

const audioPlayer = {
    state: {
        songs: [],
        current: {},
        isPlaying: false
    },

    init(){
        this.initVariables();
    },

     // функция инициализации переменных
     initVariables(){
        this.title = document.querySelector(".title");
        this.audio = document.querySelector(".audio");
        this.currentTime = document.querySelector(".current_time");
        this.fullTime = document.querySelector(".full_time");
        this.imgPlayPause = document.querySelector(".playing");
        this.progressContainer = document.querySelector(".progress_container");
        this.progress = document.querySelector(".progress");
        this.prevBtn = document.querySelector(".prev");
        this.playBtn = document.querySelector(".play");
        this.nextBtn = document.querySelector(".next");
        this.songList = document.querySelector(".song_list");
        
        getAllSongs(compID).then(songs => {
            songs.forEach(song => {
                this.state.songs.push(song);
            });
            this.state.current = this.state.songs[0];
        }).then(() => {
            this.renderAudios();
            this.setCurrentSong(this.state.current);
            this.initEvents();
        });
    },

//  функция получает песню, добавляет их в html и контейнер для песен
    loadAudioData(audio){
        const item =
            `<div class="item" data-id="${audio.id}">
                <div class="song_title">${audio.header}</div>
                <div class="song_duration">${audio.duration}</div>
            </div>`;

        this.songList.innerHTML += item;
    },

//  метод итерирует массив songs и вызывает метод, который отображет в html имеющиеся песни
    renderAudios(){
        this.state.songs.forEach(song => this.loadAudioData(song));
    },

    setCurrentSong(current){
       let {id, header, urlToMusicFile, duration} = current;
       this.title.textContent = header;
       this.audio.src = `/music/${urlToMusicFile}`;
       this.currentTime.textContent = "00:00";
       this.fullTime.textContent = duration;
       const currentSong = document.querySelector(`.item[data-id="${id}"]`);
       currentSong.classList.add("active");
    },

    renderCurrentItem(id){
        this.state.current = this.state.songs.find(song => song.id === +id);
        this.setCurrentSong(this.state.current);
        this.progress.style.width = "0%";
        this.imgPlayPause.src = "/img/music/buttons/pause.png";
        this.audio.play();
    },

    playAndPause(){
        if(!this.state.isPlaying){
            this.audio.play();
            this.imgPlayPause.src = "/img/music/buttons/pause.png";
            this.state.isPlaying = true;
        } else {
            this.audio.pause();
            this.imgPlayPause.src = "/img/music/buttons/play.png";
            this.state.isPlaying = false;
        }
    },

    // функция дял отображения прогрееса звучания аудио
    updateProgressHandler(audio){
        let duration = audio.duration;
        let currentTime = audio.currentTime;
        let progressPercent = (currentTime / duration) * 100;
        this.progress.style.width = `${progressPercent}%`;
        this.currentTime.textContent = toMinAndSec(audio.currentTime);
    },

    setProgress(e){
        const width = this.progressContainer.clientWidth;
        let clickX = e.offsetX;
        let duration = this.audio.duration;
        this.audio.currentTime = clickX / width * duration;
    },

    handleSong(e){
        console.log(this.state.isPlaying);
        let id = e.target.dataset.id;
        if(!id) id = e.target.parentNode.dataset.id;
        if(+id === this.state.current.id && this.state.isPlaying){
            this.state.isPlaying = false;
            this.imgPlayPause.src = "/img/music/buttons/play.png";
            this.audio.pause();
            return;
        }

        if(+id === this.state.current.id && !this.state.isPlaying){
            this.state.isPlaying = true;
            this.imgPlayPause.src = "/img/music/buttons/pause.png";
            this.audio.play();
            return;
        }
        removeAdditionalClass(".item", "active");
        this.renderCurrentItem(id);
    },

    playNextSong() {
        let index = this.state.songs.indexOf(this.state.current);
        if(index === this.state.songs.length - 1) return;
        removeAdditionalClass(".item", "active");
        let {id} = this.state.songs[++index];
        this.renderCurrentItem(id);
    },

    playPrevSong(){
        let index = this.state.songs.indexOf(this.state.current);
        if(index === 0) return;
        removeAdditionalClass(".item", "active");
        let {id} = this.state.songs[--index];
        this.renderCurrentItem(id);
    },

    initEvents(){
        // метод обработки play и pause
        this.playBtn.addEventListener("click", this.playAndPause.bind(this));
        this.nextBtn.addEventListener("click", this.playNextSong.bind(this));
        this.prevBtn.addEventListener("click", this.playPrevSong.bind(this));

        this.audio.addEventListener("timeupdate", (e) => {
            this.updateProgressHandler(e.target);
        });

        this.audio.addEventListener("ended", this.playNextSong.bind(this));

        this.progressContainer.addEventListener("click",  this.setProgress.bind(this));

        this.songList.addEventListener("click", this.handleSong.bind(this));
    }
}

audioPlayer.init();