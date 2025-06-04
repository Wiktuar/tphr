$.confirm = function(options){
    return new Promise((resolve, reject) => {
         const modal = $.modal({
             title: options.title,
             closeable: false,
             content: options.content,
             footerButtons: [
                 {
                     text: "Отмена",
                     handler(){
                         modal.close();
                         reject();
                     }
                 },
                 {
                     text: "Удалить",
                     handler() {
                         modal.close();
                         resolve();
                     }
                 },
             ]
         })

        modal.open();
    })
}