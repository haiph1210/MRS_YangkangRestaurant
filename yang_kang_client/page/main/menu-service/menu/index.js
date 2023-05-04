var KEY_ENTER = 13;
var sort = null;


$('#btn-search').on('click',searchForm)

    function searchForm()   {

        

        $.ajax({
            method: 'POST',
            url: 'http://localhost:8000/menu/search-form',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                search: $('#form-menu').val(),
                minPrice: $('#form-minPrice').val(),
                maxPrice: $('#form-maxPrice').val()
            }),
            success: function (data) {
                showMenus(data.responseData);
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/menu-service/menu/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            $.ajax({
                method: 'DELETE',
                url: 'http://localhost:8000/menu/delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .id').toArray().map(id => id.innerText)),
                beforeSend: () => showLoading(),
                success: data => loadMenus(),
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
    loadMenus();
});

function addListeners() {
    $( '#btn-refresh').on('click', event => loadMenus());

    // Khi người dùng thay đổi page size
    $('#page-size').on('change', event => loadMenus());

    // Khi người dùng thay đổi page number và nhấn ENTER
    $('#page-number').on('keypress', event => {
        if (event.which == KEY_ENTER) {
            loadMenus();
        }
    });

    $('#menu-tbody').on('click', 'tr', function (event) {
        if (event.ctrlKey) {
            $(this).toggleClass('selected');
        } else {
            $(this).addClass('selected').siblings().removeClass('selected');
        }
        updateStatus();
    });

    $('#menu-thead').on('click', 'th', function (event) {
        $(this).siblings().find('i').removeClass('fa-sort-up fa-sort-down').addClass('fa-sort');

        const i = $(this).find('i');
        if (i.hasClass('fa-sort')) {
            i.removeClass('fa-sort').addClass('fa-sort-up');
        } else {
            i.toggleClass('fa-sort-up fa-sort-down');
        }

        let type = i.hasClass('fa-sort-up') ? 'asc' : 'desc';
        sort = `${$(this).attr('key')},${type}`
        loadMenus();
    });

    $('#btn-add').on('click', event => {
        $('#menu-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm danh mục sản phẩm');
    });

    $('#btn-edit').on('click', event => {
        $('#menu-form').trigger('reset');
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

function loadMenus() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8000/menu/findPage',
        beforeSend: () => showLoading(),
        success: function (data) {
            var contents = data.responseData.content;
            showPageInfo(contents);
            showMenus(contents);
            updateStatus();
        },
        error: () => location.replace('/common/error/404-not-found.html'),
        complete: () => hideLoading()
    });
}

function showPageInfo(data) {
    const start = data.pageable;
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



function showMenus(content) {
    const tbody = $('#menu-tbody');
    tbody.empty();
    var number = 1;
    for (const menu of content) {
        tbody.append(`
            <tr>
                <th class='stt' value='${number}' scope="row">${number++}</th>
                <td class='id' value='${menu.id}'>${menu.id}</td>
                <td class='name' value='${menu.name}'>${menu.name}</td>
                <td class='price' value='${menu.price}'>${menu.price.toLocaleString('vi-VN')}₫</td>
                <td class='imgUrl' value='${menu.imgUrl}'><img src="${menu.imgUrl}" width="96"> </td>
                <td class='description' value='${menu.description}'>${menu.description}</td>
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
    loadMenus();
}



