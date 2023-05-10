var KEY_ENTER = 13;
var sort = null;
var UrlTimeSheetingDay = 'http://localhost:8004/api/timeSheetingDay/'




$('#btn-search').on('click',searchForm)

    function searchForm()   {

        $.ajax({
            method: 'POST',
            url: UrlTimeSheetingDay+'search-form',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                search: $('#form-timesheetingday').val(),
                minPrice: $('#form-minPrice').val(),
                maxPrice: $('#form-maxPrice').val()
            }),
            success: function (data) {
                showTimeSheetingDays(data.responseData);
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/employee-service/TimeSheetingDay/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            $.ajax({
                method: 'DELETE',
                url: UrlTimeSheetingDay+'delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .id').toArray().map(id => id.innerText)),
                beforeSend: () => showLoading(),
                success: data => loadTimeSheetingDays(),
                complete: () => hideLoading()
            });
            bootstrap.Modal.getOrCreateInstance($('#delete-modal')).hide();
        });
    });

    // if (storage.getItem('key_role') != 'ADMIN') {
    //     $('#btn-add').hide();
    //     $('#btn-edit').hide();
    //     $('#btn-delete').hide();
    // }

    addListeners();
    loadTimeSheetingDays();
});

function addListeners() {
    $( '#btn-refresh').on('click', event => loadTimeSheetingDays());

    // Khi người dùng thay đổi page size
    $('#page-size').on('change', event => loadTimeSheetingDays());

    // Khi người dùng thay đổi page number và nhấn ENTER
    $('#page-number').on('keypress', event => {
        if (event.which == KEY_ENTER) {
            loadTimeSheetingDays();
        }
    });

    $('#timesheetingday-tbody').on('click', 'tr', function (event) {
        if (event.ctrlKey) {
            $(this).toggleClass('selected');
        } else {
            $(this).addClass('selected').siblings().removeClass('selected');
        }
        updateStatus();
    });

    $('#timesheetingday-thead').on('click', 'th', function (event) {
        $(this).siblings().find('i').removeClass('fa-sort-up fa-sort-down').addClass('fa-sort');

        const i = $(this).find('i');
        if (i.hasClass('fa-sort')) {
            i.removeClass('fa-sort').addClass('fa-sort-up');
        } else {
            i.toggleClass('fa-sort-up fa-sort-down');
        }

        let type = i.hasClass('fa-sort-up') ? 'asc' : 'desc';
        sort = `${$(this).attr('key')},${type}`
        loadTimeSheetingDays();
    });

    $('#btn-add').on('click', event => {
       
        $('#timesheetingday-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm Ngày công');
    });

    $('#btn-edit').on('click', event => {
        $('#timesheetingday-form').trigger('reset');
        $('#form-modal-btn-create').hide();
        $('#form-modal-btn-update').show();
        $('#form-id-container').show();
        $('#form-modal-title').text('Cập nhật Ngày công');

        const row = $('.selected');
        $('#form-personCode').val(row.find('.personCode').attr('value'));
        $('#date-input').val(row.find('.dateCheck').attr('value'));
    });

    $('#btn-delete').on('click', function (event) {
        $('#delete-modal-title').text('Xóa Ngày công');
        const message = `Bạn chắc chắn muốn xóa ${$('.selected').length} Ngày công?`;
        $('#delete-modal-body').text(message);
    });
}
$('#timesheetingday-tbody').on('dblclick', 'tr', function () {
    $(this).removeClass('selected');
    updateStatus();
});
function updateStatus() {
    const length = $('.selected').length;
    if (length == 0) {
        $('#btn-edit').attr('disabled', 'disabled');
        $('#btn-delete').attr('disabled', 'disabled');
    } else if (length == 1) {
        $('#btn-edit').removeAttr('disabled');
        $('#btn-delete').removeAttr('disabled');
    } else {
        $('#btn-edit').attr('disabled', 'disabled');
        $('#btn-delete').removeAttr('disabled');
    }
}

function loadTimeSheetingDays() {
    const searchParams = new URLSearchParams();

    const params = {
        page: $('#page-number').val(),
        size: $('#page-size').val(),
        sort: sort
    }
    for (const key in params) {
        if (params[key]) {
            searchParams.set(key, params[key]);
        }
    }

    $.ajax({
        method: 'GET',
        url: UrlTimeSheetingDay+'findPage?' + searchParams,
        beforeSend: () => showLoading(),
        success: function (data) {
            var showPage = data.responseData;
            var contents = data.responseData.content;
            showPageInfo(showPage);
            showTimeSheetingDays(contents);
            updateStatus();
        },
        error: () => location.replace('/common/error/404-not-found.html'),
        complete: () => hideLoading()
    });
}

function showPageInfo(data) {
    const start = data.pageable.offset;
    $('#page-info').text(`Showing ${start + 1} to ${start + data.numberOfElements} of ${data.totalElements} rows.`);
    $('#page-number').attr('max', data.totalPages);
    if (data.last) {
        $('#next').addClass('disabled');
    } else {
        $('#next').removeClass('disabled');
    }
    if (data.first) {
        $('#previous').addClass('disabled');
    } else {
        $('#previous').removeClass('disabled');
    }
}



function showTimeSheetingDays(content) {
    const tbody = $('#timesheetingday-tbody');
    tbody.empty();
    var number = 1;
    for (const timesheetingday of content) {
        tbody.append(`
            <tr>
                <th class='stt' value='${number}' scope="row">${number++}</th>
                <td class='personCode' value='${timesheetingday.personCode}'>${timesheetingday.personCode}</td>
                <td class='dateCheck' value='${timesheetingday.dateCheck}'>${timesheetingday.dateCheck}</td>
                <td class='position' value='${timesheetingday.positionResponse.position}'>${timesheetingday.positionResponse.position}</td>
                <td class='day' value='${timesheetingday.day}'>${timesheetingday.day}</td>
                <td class='source' value='${timesheetingday.source}'>${timesheetingday.source}</td>
                <td class='note' value='${timesheetingday.note}'>${timesheetingday.note}</td>
            </tr>
        `);
    }
}

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

function changePageNumberBy(value) {
    const page = $('#page-number');
    page.val(+page.val() + value);
    loadTimeSheetingDays();
}

$('#btn-upload').on('click',uploadFile)
console.log(hi);
    function uploadFile()   {
        var fileInput = document.getElementById('fileInput');
        var file = fileInput.files[0];
        var formData = new FormData();
        formData.append('file', file);
        $.ajax({
            method: 'POST',
            url: UrlTimeSheetingDay + 'import',
            contentType: false, // Sử dụng false để cho phép gửi dữ liệu dạng FormData
            processData: false, // Sử dụng false để không xử lý dữ liệu trước khi gửi
            data: formData,
            success: function(data) {
                alert(data.responseData);
                loadTimeSheetingDays(data.responseData);
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText); // In ra lỗi chi tiết trong console để kiểm tra
            }
        });
}

