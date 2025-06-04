const modalWindow = $.modal( {
    title: "Добавление аватара",
    closeable: true,
    content: `
        <div class="previewAvatar">
            <img src="" id="image" class="image" alt="Фото аватара">
            <canvas id="canvas" class="canvas">
                Your browser does not support JS or HTML5!
            </canvas>
        </div>
        <div class="resultAvatar">
            <h3>Ваша аватарка</h3>
            <div class="resultPhoto">
                <canvas id="myCanvas">
                    Your browser does not support JS or HTML5!
                </canvas>
            </div>
        </div>
    `,
    footerButtons: [
        {
            text: "Ok",
            handler() {
                // отображение выбранного фрагмента фотографии
                const imageAva = document.querySelector(".imageAva");
                imageAva.src = myCanvas.toDataURL("image/jpeg");

                // добавленноие строкового представления фрагмента фото в скрытый инпут
                let sendFile = document.getElementById("inp_img");
                sendFile.value = myCanvas.toDataURL("image/jpeg");

                // удаление файла в файловом инпуте формы
                const file = document.getElementById("file");
                file.value = null;
                modalWindow.close();
            }
        }
    ]
});


