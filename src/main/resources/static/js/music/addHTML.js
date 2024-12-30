// код для добавления отдельной песни
export const single = ` 
            <form id="music_form" enctype="multipart/form-data">
                <div class="header_image">
                    <input type="hidden" id="id" name="id" value="0">
                    <input type="hidden" name="old_album_header" value="">
                    <input type="hidden" id="releaseDate" name="releaseDate" value="">
                    <input type="text"  name="album_header" placeholder="Название сингла" maxlength="33">
                    <img src="/img/music/album_cover.jpg" class="cover" alt="Обложка альбома">
                    <span class="default_cover">*Это изображение будет установлено по умолчанию. Лучше его заменить.</span>
                    <label for="cover_input"> Выберите обложку альбома</label>
                    <input type="file" id="cover_input" accept="image/*">
                    <input type="hidden" id="result_cover_input" name="cover" value="defaultCover.png">
                </div>
                <div class="edit_cover_container">
                
                </div>
                <div class="add_songs_block">
                    <div class="add_song">
                        <input type="hidden" name="songId" value="0">
                        <input type="hidden" name="song_url" value=" ">
                        <input type="hidden" name="duration" value="0">
                        <input type="text" name="header" placeholder="Название песни" maxlength="23">
                        <label for="file">Добавить аудиофайл</label>
                        <input type="file" id="file" name="file" accept="audio/mpeg">
                    </div>
                </div>
                
                <div class="upload_bar">
                    <div class="progress_upload"></div>
                </div>
               <p class="sizeofFiles">Размер загруженных файлов 0 из 75мб </p> 
               <button type="button" class="send_audio_btn">Загрузить</button>
            </form>`;


// код для добавления альбома
export const album = `
         <form id="music_form" enctype="multipart/form-data">
             <div class="header_image">
                <input type="hidden" id="id" name="id" value="0">
                <input type="hidden" name="old_album_header" value="">
                <input type="hidden" id="releaseDate" name="releaseDate" value="">
                <input type="text"  name="album_header" placeholder="Название альбома" maxlength="33">
                <img src="/img/music/album_cover.jpg" class="cover" alt="Обложка альбома">
                <span class="default_cover">*Это изображение будет установлено по умолчанию. Лучше его заменить.</span>
                <label for="cover_input"> Выберите обложку альбома</label>
                <input type="file" id="cover_input" accept="image/*">
                <input type="hidden" id="result_cover_input" name="cover" value="defaultCover.png">
             </div>
             <div class="edit_cover_container">
            
             </div>
        <!--Этот дополнительный блок необходимм, чтобы выделить логически для дальнейщего удобства поиска
            элементов input[type=file] не по всему документу, а только по этому блоку    -->
            <div class="add_songs_block">
                <div class="add_song">
                    <input type="hidden" name="songId" value="0">
                    <input type="hidden" name="song_url" value="">
                    <input type="hidden" name="duration" value="0">
                    <input type="text" name="header" placeholder="Название" maxlength="23">
                    <label for="file1">Добавить аудиофайл</label>
                    <input type="file" id="file1" name="file" accept="audio/mpeg">
                </div>
                <div class="add_song">
                    <input type="hidden" name="songId" value="0">
                    <input type="hidden" name="song_url" value="">
                    <input type="hidden" name="duration" value="0">
                    <input type="text" name="header" placeholder="Название" maxlength="23">
                    <label for="file2">Добавить аудиофайл</label>
                    <input type="file" id="file2" name="file" accept="audio/mpeg">
                 </div>
                 <div class="add_song">
                    <input type="hidden" name="songId" value="0">
                    <input type="hidden" name="song_url" value="">
                    <input type="hidden" name="duration" value="0">
                    <input type="text" name="header" placeholder="Название" maxlength="23">
                    <label for="file3">Добавить аудиофайл</label>
                    <input type="file" id="file3" name="file" accept="audio/mpeg">
                 </div>
                 <div class="add_song">
                    <input type="hidden" name="songId" value="0">
                    <input type="hidden" name="song_url" value="">
                    <input type="hidden" name="duration" value="0">
                    <input type="text" name="header" placeholder="Название" maxlength="23">
                    <label for="file4">Добавить аудиофайл</label>
                    <input type="file" id="file4" name="file" accept="audio/mpeg">
                </div>
                <div class="add_song">
                    <input type="hidden" name="songId" value="0">
                    <input type="hidden" name="song_url" value="">
                    <input type="hidden" name="duration" value="0">
                    <input type="text" name="header" placeholder="Название" maxlength="23">
                    <label for="file5">Добавить аудиофайл</label>
                    <input type="file" id="file5" name="file" accept="audio/mpeg">
                </div>
            </div>
            <div class="upload_bar">
                <div class="progress_upload"></div>
            </div>
           <p class="sizeofFiles">Размер загруженных файлов 0 из 75мб </p> 
            <button type="button" class="send_audio_btn">Отправить данные</button>
        </form>`;