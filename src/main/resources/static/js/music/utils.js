// метод, который перевод миллисекунды с мминуты и секунды.
export const toMinAndSec = duration => {
    duration = Math.floor(duration);
    let minutes = Math.floor(duration / 60);
    let seconds = Math.floor(duration - minutes * 60);
    let minutesVal = minutes;
    let secondsVal = seconds;
    if(minutes < 10) {
        minutesVal = '0' + minutes;
    }
    if(seconds < 10) {
        secondsVal = '0' + seconds;
    }
    return minutesVal + ':' + secondsVal;
}

function confirmAvatar(){

}


// function verticalApearElemenet(element, btn){
//     btn.addEventListener("click", function () {
//         if(+element.style.maxHeight === 0) {
//             element.style.maxHeight = element.scrollHeight + 'px';
//             element.style.marginTop = '45px';
//             element.style.marginBottom = '45px';
//         } else {
//             element.style.maxHeight = "";
//             element.style.marginTop = '';
//             element.style.marginBottom = '';
//         }
//     });
// }