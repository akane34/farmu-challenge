$(function(){
        $("#imgResizeForm").on("submit", function(e){
            e.preventDefault();
            var f = $(this);
            
            var fileInput = document.getElementById('imgFile');   
            var filename = fileInput.files[0].name;
            var fileExt = filename.split('.').pop().toLowerCase();

            var formData = new FormData(document.getElementById("imgResizeForm"));
            formData.append("imgType", fileExt);

            $.ajax({
                url: "http://localhost:6080/img-resize/upload",
                type: "post",
                dataType: "html",
                data: formData,
                cache: false,
                contentType: false,
	            processData: false
            }).done(function(data){
                let result = JSON.parse(data)
                if(result.isSuccess){
                    var type = "image/" + fileExt;

                    var bytes = base64ToArrayBuffer(result.result);
                    saveByteArray("img_short." + fileExt, bytes, type);
                    var blob = new Blob([bytes], { type: type });
                    var fileURL = URL.createObjectURL(blob);

                    $("#img-result").removeClass("alert-danger");
                    $("#img-result").addClass("alert-success");
                    $("#img-result").html("Respuesta: Imagen procesada " + filename);
                }
            }).fail(function(data) {
                let result = data.responseJSON;
                if(!result.isSuccess){
                    $("#img-result").removeClass("alert-success");
                    $("#img-result").addClass("alert-danger");
                    $("#img-result").html("Respuesta: " + result.errors[0].message);   
                }
            })
        });
    });

function saveByteArray(imgName, byte, type) {
    var blob = new Blob([byte], { type: type });
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    var fileName = imgName;
    link.download = fileName;
    link.click();
};
 
//Base64 conversion
function base64ToArrayBuffer(base64) {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
    var bytes =new Uint8Array(len);
    for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
}