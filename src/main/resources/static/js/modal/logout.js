const logoutWindow = $.modal({
    title: "Поздравляем!",
    closeable: true,
    width: "450px",
    footerButtons: [
        {
            text: "Хорошо!",
            handler() {
                attentionWindow.close();
                document.location = "/logout";
            }
        }
    ]
});