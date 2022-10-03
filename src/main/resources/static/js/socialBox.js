(function(){
    const btn = document.querySelector(".socialNetsBtn");
    const social_box = document.querySelector(".social-box");

    btn.addEventListener("click", function () {
        if(+social_box.style.maxHeight === 0) {
            console.log("true");
            social_box.style.maxHeight = social_box.scrollHeight + 'px';
            social_box.style.marginTop = '45px';
            social_box.style.marginBottom = '45px';
        } else {
            social_box.style.maxHeight = "";
            social_box.style.marginTop = '';
            social_box.style.marginBottom = '';
            console.log("false");
        }
    });
})();
