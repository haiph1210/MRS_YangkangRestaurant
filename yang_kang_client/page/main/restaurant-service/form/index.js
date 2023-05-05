UrlForm = "http://localhost:8002/api/restaurant/form/"
$(document).ready(function () {
    loadForm()
})

function loadForm() {
    $.ajax({
        method: 'GET',
        url: UrlForm+'findAll' ,
        beforeSend: () => showLoading(),
        success: function (data) {
            showForm(data.responseData.content);
            // updateStatus();
            console.log("data",data.responseData.content);
        },
        error: () => location.replace('/common/error/404-not-found.html'),
        complete: () => hideLoading()
    });
}

function showForm(content) {
    const formIds = $('#main');
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
    //   const label = `<label class="form-check-label" for="${form.formCode}">${button}</label>`;
      const td = `<td>${button}</td>`;
      const hi = `<div>haha</div>`
      if (i % 5 == 0) {
        rows.push('<tr>');
      }
      rows.push(td);
      if ((i + 1) % 5 == 0) {
        rows.push('</tr>');
      }
    }
    formIds.append(rows.join(''));
}

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

