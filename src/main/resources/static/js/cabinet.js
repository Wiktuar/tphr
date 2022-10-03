let attentionHeader = document.querySelector(".attention");
if(attentionHeader.textContent !== "") {
    const sbMenuItems = Array.from(document.querySelectorAll(".menu-item"));
    for(let i = 0; i < sbMenuItems.length; i++){
        sbMenuItems[i].firstChild.href = "";
        sbMenuItems[i].firstChild.className="disable";
    }
} else {
    attentionHeader.style.display ="none";
}