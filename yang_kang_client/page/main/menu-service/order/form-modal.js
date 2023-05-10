$(function () {
    $('#form-modal-btn-create').on('click', function (event) {
        const menuIds = $('input[name="menu-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();
        const imgUrlArray = $('#form-imgUrl').val().split(",").filter(url => url.trim() !== "");

        $.ajax({
            method: 'POST',
            url: 'http://localhost:8000/combo/create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                name: $('#form-name').val(),
                description: $('#form-description').val(),
                imgUrl: imgUrlArray,
                menuIds: menuIds
            }),
            success: function (data) {
                // loadCombos();
                $('#combo-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        const menuIds = $('input[name="menu-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();
        const imgUrlArray = $('#form-imgUrl').val().split(",").filter(url => url.trim() !== "");

        const id = $('#form-id').val();
        $.ajax({
            method: 'PUT',
            url: 'http://localhost:8000/combo/update/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                id: id,
                name: $('#form-name').val(),
                description: $('#form-description').val(),
                imgUrl: imgUrlArray,
                menuIds: menuIds
            }),
            success: function (data) {
                loadCombos();
                $('#combo-form').trigger("reset");
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
            }
        });
    }
    
    );
});

    $(document).ready(function () {
        loadMenu()
    })
    function loadMenu() {
        $.ajax({
            method: 'GET',
            url: 'http://localhost:8000/menu/findPage',
            contentType: 'application/json; charset=utf-8',
            beforeSend: () => showLoading(),
            success: function(data) {
                var contents = data.responseData.content;
                showMenu(contents);
            }
        })
    }

    function showMenu(content) {
        const menuIds = $('#form-menu-id');
        menuIds.empty();
        for(const menu of content) {
            menuIds.append(`
            <input type="checkbox" class="btn-check" id="${menu.name}" name="menu-lists[]" value="${menu.id}">
            <label class="btn btn-outline-primary" for="${menu.name}">${menu.name}</label>
            `);
        }
        
    }