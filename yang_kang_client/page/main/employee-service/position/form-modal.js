
$(function () {
    $('#form-modal-btn-create').on('click', function (event) {

        $.ajax({
            method: 'POST',
            url: UrlPosition+'create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                positionEmpl: $('#form-positionEmpl').val(),
            }),
            success: function (data) {
                loadPositions();
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
                $('#position-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        $.ajax({
            method: 'PUT',
            url: UrlPosition+'update/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                positionEmpl: $('#form-positionEmpl').val(),
            }),
            success: function (data) {
                loadPositions();
                $('#position-form').trigger("reset");
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
            }
        });
    }
    
    );
});



     