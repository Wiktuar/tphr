const attentionWindow = $.modal({
    title: "Ошибка заполнения формы",
    closeable: true,
    width: "450px",
    footerButtons: [
        {
            text: "Хорошо!",
            handler() {
                attentionWindow.close();
            }
        }
    ]
});
