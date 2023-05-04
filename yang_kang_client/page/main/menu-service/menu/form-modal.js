$(function () {
    $('#form-modal-btn-create').on('click', function (event) {
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8000/menu/create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                name: $('#form-name').val(),
                price: $('#form-price').val(),
                imgUrl: $('#form-imgUrl').val(),
                description: $('#form-description').val(),
                comboId: $('#form-comboId').val(),
            }),
            success: function (data) {
                loadMenus();
                $('#menu-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        const id = $('#form-id').val();
        $.ajax({
            method: 'PUT',
            url: 'http://localhost:8000/menu/update/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                id: id,
                name: $('#form-name').val(),
                price: $('#form-price').val(),
                imgUrl: $('#form-imgUrl').val(),
                description: $('#form-description').val(),
                comboId: $('#form-comboId').val()
            }),
            success: function (data) {
                loadMenus();
                $('#menu-form').trigger("reset");
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
            }
        });
    }
    
    );
});

    // $(document).ready(function () {
    //     loadCombo()
    // })
    // function loadCombo() {
    //     $.ajax({
    //         method: 'GET',
    //         url: 'http://localhost:8000/combo/findPage',
    //         contentType: 'application/json; charset=utf-8',
    //         beforeSend: () => showLoading(),
    //         success: function(data) {
    //             var contents = data.responseData.content;
    //             showCombo(contents);
    //         }
    //     })
    // }

    // function showCombo(content) {
    //     const comboId = $('#form-comboId');
    //     comboId.empty();
    //     for(const combo of content) {
    //         comboId.append(`
    //         <option class="name"  value='${combo.id}'>${combo.name}</option>
    //         `);
    //     }
    // }