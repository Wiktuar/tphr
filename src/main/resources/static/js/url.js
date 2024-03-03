const login = document.querySelector(".login");
console.log(login);

login.addEventListener("click", e => {
    e.preventDefault();
    console.log("method working...")
    const formData = new FormData();
    formData.append("url", window.location.href);
    window.location = "/login";
    const res = fetch("/targetLogin", {
        method: 'POST',
        body: formData
    });
    console.log(res.status);
    if(res.status === 200){
        console.log(res.status);
    }
});


