const description = document.querySelector(".description");
const fullTextBtn = document.querySelector(".full_text_btn");
const inscription = document.querySelector(".inscription");
const buttonImg = document.querySelector(".full_text_btn img");
const socialNets = document.querySelector(".social-nets");

if(description.scrollHeight > 200){
    description.style.maxHeight = '200px';
    fullTextBtn.style.display = 'flex';
}

if(socialNets.getElementsByTagName("li").length === 0){
    const li = document.createElement("li");
    li.textContent = "Я добавлю их позже";
    socialNets.appendChild(li);
}

// функция, разворачивающая текст до полного размера
fullTextBtn.addEventListener("click", (e) => {
   fullTextBtn.classList.toggle("active");
   if(fullTextBtn.classList.contains("active")){
       inscription.textContent = "Свернуть";
       buttonImg.style.transform = 'rotate(180deg)';
       description.style.maxHeight = `${description.scrollHeight}px`;
   } else {
       inscription.textContent = "Развернуть";
       buttonImg.style.transform = 'rotate(0)';
       description.style.maxHeight = '200px';
   }
});