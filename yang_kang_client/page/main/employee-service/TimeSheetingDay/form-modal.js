var UrlPosition = 'http://localhost:8004/api/position/'

$(function () {
    $('#form-modal-btn-create').on('click', function (event) {
        const date = $('#date-input').val();
        const positionId = $('input[name="position-lists[]"]:checked').val();
        $.ajax({
            method: 'POST',
            url: UrlTimeSheetingDay+'create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                personCode: $('#form-personCode').val(),
                dateCheck: date,
                positionId: positionId,
                day: $('#form-day').val(),
                source: $('#form-source').val(),
                note: $('#form-note').val()
            }),
            success: function (data) {
                loadTimeSheetingDays();
                $('#timesheetingday-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        const date = $('#date-input').val();
        const positionId = $('input[name="position-lists[]"]:checked').val();
        const personCode = $('#form-personCode').val();
        const dateCheck = $('#date-input').val();

        $.ajax({
            method: 'PUT',
            url: UrlTimeSheetingDay+'update',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                personCode: personCode,
                dateCheck: dateCheck,
                positionId: positionId,
                day: $('#form-day').val(),
                source: $('#form-source').val(),
                note: $('#form-note').val()
            }),
            success: function (data) {
                loadTimeSheetingDays();
                $('#timesheetingday-form').trigger("reset");
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
            }
        });
    }
    
    );
});

    $(document).ready(function () {
        loadPosition()
    })
    function loadPosition() {
        $.ajax({
            method: 'GET',
            url: UrlPosition+'findAll',
            contentType: 'application/json; charset=utf-8',
            beforeSend: () => showLoading(),
            success: function(data) {
                var contents = data.responseData;
                showPosition(contents);
            }
        })
    }

    function showPosition(content) {
        const positionids = $('#positionTableBody');
        positionids.empty();
        const rows = [];
        for (let i = 0; i < content.length; i++) {
          const position = content[i];
          const checkbox = `<input type="radio" class="btn-check" id="${position.position}" name="position-lists[]" value="${position.id}">`;
          const label = `<label class="btn btn-outline-primary" for="${position.position}">${position.position}</label>`;
          const td = `<td>${checkbox}${label}</td>`;
          if (i % 3 == 0) {
            rows.push('<tr>');
          }
          rows.push(td);
          if ((i + 1) % 3 == 0) {
            rows.push('</tr>');
          }
        }
        positionids.append(rows.join(''));
      }