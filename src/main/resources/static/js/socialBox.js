(function(){
    const btn = document.querySelector(".additionalBtn");
    const additional = document.querySelector(".additional");

    btn.addEventListener("click", function () {
        if(+additional.style.maxHeight === 0) {
            additional.style.maxHeight = additional.scrollHeight + 'px';
            additional.style.marginTop = '45px';
            additional.style.marginBottom = '45px';
        } else {
            additional.style.maxHeight = "";
            additional.style.marginTop = '';
            additional.style.marginBottom = '';
        }
    });
})();
