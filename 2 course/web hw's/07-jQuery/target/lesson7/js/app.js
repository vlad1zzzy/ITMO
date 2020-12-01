window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function (data, fillPage) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: function (response) {
            fillPage(response);
            if (response["redirect"])
                location.href = response["redirect"];
        }
    });
}

let change;
