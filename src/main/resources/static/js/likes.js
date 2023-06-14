

// функция добавления или удаления лайков
async function addOrRemoveLike(id){
    let res = await fetch(`/poem/likes/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'Application/json'
        }
    })

    res.json().then(obj => console.log(obj.status));
}

document.querySelector(".like_btn")
    .addEventListener("click", () => addOrRemoveLike(poemID));




