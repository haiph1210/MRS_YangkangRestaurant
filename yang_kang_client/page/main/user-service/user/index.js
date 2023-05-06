var KEY_ENTER = 13;
var sort = null;
var UrlUser = 'http://localhost:8001/api/person/'


$('#btn-search').on('click',searchForm)

    function searchForm()   {

        $.ajax({
            method: 'POST',
            url: UrlUser+'search-form',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                search: $('#form-user').val(),
                minPrice: $('#form-minPrice').val(),
                maxPrice: $('#form-maxPrice').val()
            }),
            success: function (data) {
                showUsers(data.responseData);
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/user-service/user/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            $.ajax({
                method: 'DELETE',
                url: UrlUser+'delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .id').toArray().map(id => id.innerText)),
                beforeSend: () => showLoading(),
                success: data => loadUsers(),
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
    loadUsers();
});

function addListeners() {
    $( '#btn-refresh').on('click', event => loadUsers());

    // Khi người dùng thay đổi page size
    $('#page-size').on('change', event => loadUsers());

    // Khi người dùng thay đổi page number và nhấn ENTER
    $('#page-number').on('keypress', event => {
        if (event.which == KEY_ENTER) {
            loadUsers();
        }
    });

    $('#user-tbody').on('click', 'tr', function (event) {
        if (event.ctrlKey) {
            $(this).toggleClass('selected');
        } else {
            $(this).addClass('selected').siblings().removeClass('selected');
        }
        updateStatus();
    });

    $('#user-thead').on('click', 'th', function (event) {
        $(this).siblings().find('i').removeClass('fa-sort-up fa-sort-down').addClass('fa-sort');

        const i = $(this).find('i');
        if (i.hasClass('fa-sort')) {
            i.removeClass('fa-sort').addClass('fa-sort-up');
        } else {
            i.toggleClass('fa-sort-up fa-sort-down');
        }

        let type = i.hasClass('fa-sort-up') ? 'asc' : 'desc';
        sort = `${$(this).attr('key')},${type}`
        loadUsers();
    });

    $('#btn-add').on('click', event => {
        $('#user-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm danh mục sản phẩm');
    });

    $('#btn-edit').on('click', event => {
        $('#user-form').trigger('reset');
        $('#form-modal-btn-create').hide();
        $('#form-modal-btn-update').show();
        $('#form-id-container').show();
        $('#form-modal-title').text('Cập nhật danh mục sản phẩm');

        const row = $('.selected');
        $('#form-id').val(row.find('.id').attr('value'));
        $('#form-name').val(row.find('.name').attr('value'));
    });

    $('#btn-delete').on('click', function (event) {
        $('#delete-modal-title').text('Xóa danh mục sản phẩm');
        const message = `Bạn chắc chắn muốn xóa ${$('.selected').length} danh mục sản phẩm?`;
        $('#delete-modal-body').text(message);
    });
}

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

function loadUsers() {
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
        url: UrlUser+'findPageUser?' + searchParams,
        beforeSend: () => showLoading(),
        success: function (data) {
            var showPage = data.responseData;
            var contents = data.responseData.content;
            showPageInfo(showPage);
            showUsers(contents);
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



function showUsers(content) {
    const tbody = $('#user-tbody');
    tbody.empty();
    var number = 1;
    for (const user of content) {
        tbody.append(`
            <tr>
                <th class='stt' value='${number}' scope="row">${number++}</th>
                <td class='personCode' value='${user.personCode}'>${user.personCode}</td>
                <td class='personCode' value='${user.fullName}'>${user.fullName}</td>
                <td class='imgUrl' value='${user.imgUrl}'><img src="${user.imgUrl}" width="96"> </td>
                <td class='email' value='${user.email}'>${user.email}</td>
                <td class='phoneNumber' value='${user.phoneNumber}'>${user.phoneNumber}</td>
                <td class='address' value='${user.address}'>${user.address}</td>
                <td class='gender' value='${user.gender}'>${user.gender}</td>
                <td class='status' value='${user.status}'>${user.status}</td>
                <td class='role' value='${user.role}'>${user.role}</td>
                <td class='createDate' value='${user.createDate}'>${user.createDate}</td>
                <td class='userType' value='${user.userType}'>${user.userType}</td>
                <td class='countLogin' value='${user.countLogin}'>${user.countLogin}</td>
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
    loadUsers();
}



