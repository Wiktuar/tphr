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

// метод удаления особенного класса у массива одинаковых элементов
export function removeAdditionalClass(commonClass, uniqueClass){
   const nodes = document.querySelectorAll(commonClass);
   let item = Array.from(nodes).find(arr => arr.classList.contains(uniqueClass));
   if(item) item.classList.remove(uniqueClass);
}

// функция, создающая блок для вывода сообщений при ошибках заполнения формы отправки песен
export function getAttentionDiv(text){
    const attentionDiv = document.createElement("div");
    attentionDiv.className = 'attention';
    if (!attentionDiv.textContent) attentionDiv.textContent = text;
    return attentionDiv;
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