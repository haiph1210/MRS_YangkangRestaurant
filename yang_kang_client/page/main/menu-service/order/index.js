var KEY_ENTER = 13;
var sort = null;


$('#btn-search').on('click',searchForm)

    function searchForm()   {
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8000/order/search-form',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                search: $('#form-order').val(),
                minPrice: $('#form-minPrice').val(),
                maxPrice: $('#form-maxPrice').val()
            }),
            success: function (data) {
                showOrders(data.responseData);
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/order-service/order/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            $.ajax({
                method: 'DELETE',
                url: 'http://localhost:8000/order/delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .id').toArray().map(id => id.innerText)),
                beforeSend: () => showLoading(),
                success: data => loadOrders(),
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
    loadOrders();
});

function addListeners() {
    $( '#btn-refresh').on('click', event => loadOrders());

    // Khi người dùng thay đổi page size
    $('#page-size').on('change', event => loadOrders());

    // Khi người dùng thay đổi page number và nhấn ENTER
    $('#page-number').on('keypress', event => {
        if (event.which == KEY_ENTER) {
            loadOrders();
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
    
    $('#order-tbody').on('click', '.approval-button', function (event) {
        event.stopPropagation();
        if (confirm("Bạn có chắc chắn muốn approve?")) {
            toastr.success('Approved');
            console.log("callAPI"); // viết API vào đây
        }
  });

  $('#order-tbody').on('click', '.refuse-button', function (event) {
    event.stopPropagation();
    if (confirm("Bạn có chắc chắn muốn refuse?")) {
        toastr.success('Refuse');
        console.log("callAPI"); // viết API vào đây
    }
});


//   $('#order-tbody').on('click', '.approval-button', function (event) {
//     event.stopPropagation();
//    console.log("callAPI");
   
//    $('.approval-button').click(function() {
//     Toastify({
//       text: "Bạn có chắc chắn muốn Approve?",
//       close: true,
//       gravity: "bottom", // vị trí hiển thị của toast
//       position: "left", // vị trí hiển thị của toast
//       backgroundColor: "#17a2b8", // màu nền của toast
//       stopOnFocus: true, // dừng hiển thị toast khi focus vào trang
//       onClick: function() {
//         // Gọi API approve tại đây
//       }
//     }).showToast();
//   });

// });

  $('#order-tbody').on('click', '.refuse-button', function (event) {
        event.stopPropagation();
        // call API refuse
  });

    $('#order-tbody').on('dblclick', 'tr', function () {
        $(this).removeClass('selected');
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
        loadOrder();
    });

    $('#btn-add').on('click', event => {
        $('#order-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm danh mục sản phẩm');
    });

    $('#btn-edit').on('click', event => {
        $('#order-form').trigger('reset');
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

function loadOrders() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8000/api/order/findAll',
        beforeSend: () => showLoading(),
        success: function (data) {
            var contents = data.responseData.content;

            showPageInfo(contents);
            showOrders(contents);
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



function showOrders(content) {
    const tbody = $('#order-tbody');
    console.log(content);
    tbody.empty();
    var number = 1;
    for (const order of content) {
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
                <td class='isApproved ${order.status === "PENDING" ? "can-approve" : ""}'>
  <button class="approval-button" ${order.status !== "PENDING" ? "disabled" : ""}>
    <i class="fas fa-check approve-icon" title="APPROVED"></i>
  </button>
  <button class="refuse-button" ${order.status !== "PENDING" ? "disabled" : ""}>
    <i class="fas fa-times refuse-icon" title="Refuse"></i>
  </button>
</td>

            </tr>
        `);
    }
}
{/* <td class='isApproved' >
                <button class="approval-button">
                  <i class="fas fa-check approve-icon" title="Approve"></i>
                  </button>
                  <button class="refuse-button">
                  <i class="fas fa-times refuse-icon" title="Refuse"></i>
                </button>
              </td> */}


function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

function changePageNumberBy(value) {
    const page = $('#page-number');
    page.val(+page.val() + value);
    loadOrders();
}



