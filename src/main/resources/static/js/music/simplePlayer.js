export function createPlayer(){
    const player = document.createElement("div");
    player.classList.add("player");
    player.insertAdjacentHTML("afterbegin",
        `<div class="meta-data">
                  <div class="buttons">
                      <div class="btn play"><img class="img_src" src="/img/musicButtons/play.png" alt="play png"></div>
                  </div>
                  <div class="current_time">00:00</div>
                  <audio class="audio" preload="metadata" data-status="pause"></audio>
                  <div class="progress_container_simple">
                      <div class="progress"></div>
                  </div>
                  <div class="duration">07:54</div>
              </div>`);
    return player;
}


// возвращает экземпляр плеера
// export const player =
//     `<div class="player">
//          <div class="meta-data">
//          <div class="buttons">
//              <div class="btn play"><img class="img_src" src="/img/musicButtons/play.png" alt="play png"></div>
//          </div>
//              <div class="current_time">00:00</div>
//              <audio class="audio" preload="metadata" data-status="pause"></audio>
//              <div class="progress_container_simple">
//                  <div class="progress"></div>
//         </div>
//              <div class="duration">07:54</div>
//          </div>
//     </div>`;
