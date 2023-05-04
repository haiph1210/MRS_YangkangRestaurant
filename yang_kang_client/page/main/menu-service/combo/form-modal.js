$(function () {
    $('#form-modal-btn-create').on('click', function (event) {
        const menuIds = $('input[name="menu-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();
        const imgUrlArray = $('#form-imgUrl').val().split(",").filter(url => url.trim() !== "");

        $.ajax({
            method: 'POST',
            url: UrlCombo +'create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                name: $('#form-name').val(),
                description: $('#form-description').val(),
                imgUrl: imgUrlArray,
                menuIds: menuIds
            }),
            success: function (data) {
                loadCombos();
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
            url: UrlCombo+'update/' + id,
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
            url: UrlMenu+'findPage',
            contentType: 'application/json; charset=utf-8',
            beforeSend: () => showLoading(),
            success: function(data) {
                var contents = data.responseData.content;
                console.log(contents);
                showMenu(contents);
            }
        })
    }

    function showMenu(content) {
        const menuIds = $('#menuTableBody');
        menuIds.empty();
        const rows = [];
        for (let i = 0; i < content.length; i++) {
          const menu = content[i];
          const checkbox = `<input type="checkbox" class="btn-check" id="${menu.name}" name="menu-lists[]" value="${menu.id}">`;
          const label = `<label class="btn btn-outline-primary" for="${menu.name}">${menu.name}</label>`;
          const td = `<td>${checkbox}${label}</td>`;
      
          if (i % 3 == 0) {
            rows.push('<tr>');
          }
      
          rows.push(td);
      
          if ((i + 1) % 3 == 0) {
            rows.push('</tr>');
          }
        }
      
        menuIds.append(rows.join(''));
      }
      

    // <input type="checkbox" class="btn-check" id="${menu.name}" name="menu-lists[]" value="${menu.id}">
    // <label class="btn btn-outline-primary" for="${menu.name}">${menu.name}</label>