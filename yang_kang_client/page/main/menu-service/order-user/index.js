var KEY_ENTER = 13;
var sort = null;
var UrlMenu = 'http://localhost:8000/api/menu/'
var UrlCombos = 'http://localhost:8000/api/combo/'
var UrlForms = 'http://localhost:8002/api/restaurant/form/'
var UrlOrder = 'http://localhost:8000/api/order/'



$('#btn-search').on('click',searchForm)

    function searchForm()   {
        const orderCode = $('#form-orderCode').val();
        console.log(orderCode);
        $.ajax({
            method: 'GET',
            url: UrlOrder+'orderCode/' +orderCode,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log(data.responseData);
                showOrders(data.responseData);
                hideLoading();
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/menu-service/order-user/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            $.ajax({
                method: 'DELETE',
                url: UrlMenu+'delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .id').toArray().map(id => id.innerText)),
                beforeSend: () => showLoading(),
                success: data => loadMenus(),
                complete: () => hideLoading()
            });
            bootstrap.Modal.getOrCreateInstance($('#delete-modal')).hide();
        }
        
        );
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

    $('#order-tbody').on('click', 'tr', function (event) {
        if (event.ctrlKey) {
            $(this).toggleClass('selected');
        } else {
            $(this).addClass('selected').siblings().removeClass('selected');
        }
        updateStatus();
    });

    $('#order-thead').on('click', 'th', function (event) {
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
        $('#order-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm Đơn Hàng Mới');
    });

    $('#btn-edit').on('click', event => {
        $('#order-form').trigger('reset');
        $('#form-modal-btn-create').hide();
        $('#form-modal-btn-update').show();
        $('#form-id-container').show();
        $('#form-modal-title').text('Cập nhật Đơn Hàng');

        const row = $('.selected');
        $('#form-id').val(row.find('.id').attr('value'));
        $('#form-personCode').val(row.find('.personCode').attr('value'));
        
    });

    $('#btn-delete').on('click', function (event) {
        $('#delete-modal-title').text('Xóa danh mục sản phẩm');
        const message = `Bạn chắc chắn muốn xóa ${$('.selected').length} danh mục sản phẩm?`;
        $('#delete-modal-body').text(message);
    });
}
$('#order-tbody').on('click', '.approval-button', function (event) {
    // event.stopPropagation();
    const row = $(this).closest('tr');
    const id = row.find('.id').attr('value');
    console.log("Id:", id);
    if (confirm("Bạn có chắc chắn muốn approve?")) {
        $.ajax({
            method: 'PUT',
            url: UrlOrder+'updateApproved/' + id,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                loadOrders();
            }
        });
    }
});

$('#order-tbody').on('click', '.refuse-button', function (event) {
// event.stopPropagation();
const row = $(this).closest('tr');
const id = row.find('.id').attr('value');
console.log("Id:", id);
if (confirm("Bạn có chắc chắn muốn refuse?")) {
    $.ajax({
        method: 'PUT',
        url: UrlOrder+'updateRefuse/' + id,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            loadOrders();
        }
    });
}
});


$('#order-tbody').on('dblclick', 'tr', function () {
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

function loadOrder() {
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

    // $.ajax({
    //     method: 'GET',
    //     url: UrlOrder+'orderCode' + searchParams,
    //     beforeSend: () => showLoading(),
    //     success: function (data) {
    //         var showPage = data.responseData;
    //         var contents = data.responseData.content;
    //         showPageInfo(showPage);
    //         showOrders(contents);
    //         updateStatus();
    //     },
    //     error: () => location.replace('/common/error/404-not-found.html'),
    //     complete: () => hideLoading()
    // });
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




function showOrders(order) {
    const tbody = $('#order-tbody');
    console.log(order);
    tbody.empty();
    var number = 1;
  
        const menus = order.menus;
        const combos = order.combos;
        const forms = order.forms;
        const menuNames = menus.map(menu => menu.name); 
        const comboNames = combos.map(combo => combo.name);
        const formCodes = forms.map(form => form.formCode);

        const hourUpdate = new Date(order.hour).toLocaleTimeString();
        tbody.append(`
            <tr>
                <th class='stt' value='${number}' scope="row">${number++}</th>
                <td class='id' value='${order.id}'>${order.id}</td>
                <td class='orderCode' value='${order.orderCode}'>${order.orderCode}</td>
                <td class='personCode' value='${order.personResponses.personCode}'>${order.personResponses.personCode}</td>
                <td class='menus' value='${menuNames}'>${menuNames}</td>
                <td class='combos' value='${comboNames}'>${comboNames}</td>
                <td class='forms' value='${formCodes}'>${formCodes}</td>
                <td class='people' value='${order.people}'>${order.people}</td>
                <td class='hour' value='${hourUpdate}'>${hourUpdate}</td>
                <td class='description' value='${order.description}'>${order.description}</td>
                <td class='type' value='${order.type}'>${order.type}</td>
                <td class='totalAmount' value='${order.totalAmount}'>${order.totalAmount}</td>
                <td class='totalPrice' value='${order.totalPrice}'>${order.totalPrice.toLocaleString('vi-VN')}₫</td>
                <td class='status' value='${order.status}'>${order.status}</td>
            </tr>
        `);
    
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



