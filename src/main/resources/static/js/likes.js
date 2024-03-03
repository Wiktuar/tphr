//значок лайка, который является кнопкой
const likeBtn = document.querySelector(".like_btn");
const countOfLikes = document.querySelector(".p_digit_l");

//переменная meLiked определена в разделе js в шаблоне ftl, она приходит оттуда в виле числа
isMeLiked(Boolean(meLiked));

//функция изменяющая сердечки в зависимости от того, лайкнул пользователь данное стихотворение или нет
function isMeLiked(isNeLiked){
    if(isNeLiked){
        likeBtn.innerHTML = `<i class="fa-solid fa-2x fa-heart" style="color: #e60f0f;"></i>`;
    }
    else {
        likeBtn.innerHTML = `<i class="fa-regular fa-2x fa-heart" style="color: #021027;">`;
    }
}

// функция добавления или удаления лайков
async function addOrRemoveLike(id){
    if(knownUser === 0){
        const attention = document.querySelector(".enter_for_like");
        attention.classList.toggle("visible");
        return;
    }

    let res = await fetch(`/poem/likes/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'Application/json'
        }
    })

    res.json().then(obj => {
        isMeLiked(obj.status);
        countOfLikes.textContent = obj.count;
    });
}


likeBtn.addEventListener("click", () => addOrRemoveLike(poemID));




