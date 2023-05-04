$(function () {
    $('#form-modal-btn-create').on('click', function (event) {
        const menuIds = $('input[name="menu-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();

        const comboIds = $('input[name="combo-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();

        const formIds = $('input[name="form-lists[]"]:checked').map(function () {
            return $(this).val();
        }).get();
        const datetime = $('#datetime-input').val();
        $.ajax({
            method: 'POST',
            url: UrlOrder+'create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                type: $('#form-type').val(),
                personCode: $('#form-personCode').val(),
                idMenus: menuIds,
                idCombos: comboIds,
                idForms: formIds,
                people: $('#form-people').val(),
                hour: datetime,
                description: $('#form-description').val(),
                
            }),
            success: function (data) {
                // loadMenus();
                confirm(data)
                // $('#menu-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        const id = $('#form-id').val();
        $.ajax({
            method: 'PUT',
            url: UrlMenu+'update/' + id,
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



  $(document).ready(function () {
    loadCombos()
})
function loadCombos() {
    $.ajax({
        method: 'GET',
        url: UrlCombos+'findPage',
        contentType: 'application/json; charset=utf-8',
        beforeSend: () => showLoading(),
        success: function(data) {
            var contents = data.responseData.content;
            console.log(contents);
            showCombo(contents);
        }
    })
}

function showCombo(content) {
    const comboids = $('#comboTableBody');
    comboids.empty();
    const rows = [];
    for (let i = 0; i < content.length; i++) {
      const combo = content[i];
      const checkbox = `<input type="checkbox" class="btn-check" id="${combo.name}" name="combo-lists[]" value="${combo.id}">`;
      const label = `<label class="btn btn-outline-primary" for="${combo.name}">${combo.name}</label>`;
      const td = `<td>${checkbox}${label}</td>`;
      if (i % 3 == 0) {
        rows.push('<tr>');
      }
      rows.push(td);
      if ((i + 1) % 3 == 0) {
        rows.push('</tr>');
      }
    }
    comboids.append(rows.join(''));
  }
  

  $(document).ready(function () {
    loadForms()
})
function loadForms() {
    $.ajax({
        method: 'GET',
        url: UrlForms+'findAll',
        contentType: 'application/json; charset=utf-8',
        beforeSend: () => showLoading(),
        success: function(data) {
            var contents = data.responseData.content;
            console.log("form",contents);
            showForms(contents);
        }
    })
}

function showForms(content) {
    const formIds = $('#formTableBody');
    formIds.empty();
    const rows = [];
    for (let i = 0; i < content.length; i++) {
      const form = content[i];
      const status = form.status;
      let buttonClass = '';
      let buttonLabel = '';
      switch (status) {
        case 'READY':
          buttonClass = 'btn-success';
          buttonLabel = 'Ready';
          break;
        case 'MAINTENANCE':
          buttonClass = 'btn-danger';
          buttonLabel = 'Maintenance';
          break;
        case 'BOOKED':
          buttonClass = 'btn-warning';
          buttonLabel = 'Booked';
          break;
        default:
          buttonClass = 'btn-secondary';
          buttonLabel = 'Pending';
          break;
      }
      const checkbox = `<input type="checkbox" class="form-check-input" id="${form.formCode}" name="form-lists[]" value="${form.id}">`;
      const button = `<button class="btn ${buttonClass}">${form.formCode}</button>`;
      const label = `<label class="form-check-label" for="${form.formCode}">${button}</label>`;
      const td = `<td>${checkbox}${button}</td>`;
      if (i % 3 == 0) {
        rows.push('<tr>');
      }
      rows.push(td);
      if ((i + 1) % 3 == 0) {
        rows.push('</tr>');
      }
    }
    formIds.append(rows.join(''));
  }
  