var baseEndpoint = "http://localhost:5080/";

$(document).ready(function () {
  $("#shortenerForm").submit(function (event) {
    var endpoint = baseEndpoint + "url-shortener/short";
    var shortUrlLabel = "Url Corta: ";
    var formData = {
      url: $("#url").val(),
    };

    if ($("#url").val().toLowerCase().startsWith(baseEndpoint)){
      endpoint = baseEndpoint + "url-shortener/original"; 
      shortUrlLabel = "Url Original: " 
      formData = {
        hashUrl: $("#url").val().replace(baseEndpoint, ""),
      };
    }

    $.ajax({
      type: "POST",
      url: endpoint,
      beforeSend: function(request) {
          request.setRequestHeader("Access-Control-Allow-Origin", "*");
      },
      data: formData,
      encode: true,
      contentType: "application/x-www-form-urlencoded"
    }).done(function (result) {
      if (result.isSuccess) {
        $("#short-url").removeClass("alert-danger");
        $("#short-url").addClass("alert-success");
        $("#short-url").html(
          shortUrlLabel + result.result
        );
      }
    }).fail(function(data) {
      let result = data.responseJSON;
      if (!result.isSuccess) {
        var error = result.errors[0];

        $("#short-url").removeClass("alert-success");
        $("#short-url").addClass("alert-danger");
        $("#short-url").html(
          error.message
        );
      }
    });

    event.preventDefault();
  });

  $("#url").change(function(){
    if ($("#url").val().toLowerCase().startsWith(baseEndpoint)){
      $("#shortenerButton").html('Obtener Url Original');  
    }
    else {
      $("#shortenerButton").html('Crear Url Corta');
    }
    
  });

  
});