<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sens Photo</title>
</head>
<body>
<#--    <h1>Upload files with fetch API</h1>-->
        <form method="post" action="/photo" enctype="multipart/form-data">
            <input type="text" id="myTextId" name="myTextName">
            <input type="file" id="imgFile" name="myFile" value="Выбери файл">
            <input type="hidden" id="inp_img" name="img" value="">
            <button type="submit">Отправить</button>
        </form>
        <img src="" id="img" height="300">
        <img src="" id="resultimg" height="300">

<canvas id="canvas"></canvas>

    <script>
        let image = document.getElementById("img");
        let resultImage = document.getElementById("resultimg");
        let canvas = document.getElementById("canvas");
        let ctx = canvas.getContext("2d");

        document.getElementById('imgFile')
        addEventListener('change', e => {
            let files = e.target.files;
            let fr = new FileReader();
            fr.onloadend = function() {
                image.src = fr.result;
                setTimeout(() =>{
                    init(image);
                    resultImage.src = canvas.toDataURL("image/jpeg");
                    document.getElementById('inp_img').value = canvas.toDataURL("image/jpeg");
                }, 200 );
            };
            fr.readAsDataURL(files[0]);
        });


        function init(image) {
            canvas.width = image.width;
            canvas.height = image.height;
            drawImage(image);
        }


        function drawImage(image){
            ctx.drawImage(image,
                0, 0,
                150, 150,
                0, 0,
                200, 200);
        }


        const myForm = document.getElementById("myorm");
        const inpFile = document.getElementById("inpFile");

        myForm.addEventListener("submit", e => {
            e.preventDefault();

            const endPoint = "/photo";
            const formData = new FormData();

            formData.append("inpFile", inpFile.files[0]);

            fetch(endPoint, {
                method: "post",
                body: formData
            }).catch(console.error);
        });

    </script>
</body>
</html>