UrlOrder = 'http://localhost:8000/api/order/'
UrlDiscount = 'http://localhost:8000/api/discount/'
UrlPayment = 'http://localhost:8000/api/payment/'
$(function () {
    $('#form-modal-btn-create').on('click', function (event) {

        const selectedDiscountId = $('input[name="discount-lists[]"]:checked').val();
        $.ajax({
            method: 'POST',
            url: UrlPayment+'create',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                orderIds: $('#form-orderId').val(),
                discountId: selectedDiscountId,
                customerPay: $('#form-customerPay').val(),
                status: $('#form-status').val(),
            }),
            success: function (data) {
                loadPayments();
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
                $('#payment-form').trigger("reset");
            }
        });
    });

    $('#form-modal-btn-update').on('click', function (event) {
        const id = $('#form-id').val();
        const selectedDiscountId = $('input[name="discount-lists[]"]:checked').val();
        $.ajax({
            method: 'PUT',
            url: UrlPayment+'update/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                id: id,
                orderIds: $('#form-orderId').val(),
                discountId: selectedDiscountId,
                customerPay: $('#form-customerPay').val(),
                status: $('#form-status').val(),
            }),
            success: function (data) {
                loadPayments();
                $('#payment-form').trigger("reset");
                bootstrap.Modal.getOrCreateInstance($('#form-modal')).hide();
            }
        });
    }
    
    );
});

    $(document).ready(function () {
        loadOrder()
    })
    function loadOrder() {
        $.ajax({
            method: 'GET',
            url: UrlOrder+'findAllIsApproved',
            contentType: 'application/json; charset=utf-8',
            beforeSend: () => showLoading(),
            success: function(data) {
                var contents = data.responseData;
                showOrder(contents);
                uploadDataForForm(contents)
            }
        })
    }

    function showOrder(content) {
        const orderId = $('#form-orderId');
        orderId.empty();    
            for(const order of content) {
                orderId.append(`
                <option class="orderCode" value='${order.id}'>${order.orderCode}</option>
                `);
            }
    }

      $(document).ready(function () {
        loadDiscount()
    })
    function loadDiscount() {
        $.ajax({
            method: 'GET',
            url: UrlDiscount+'findAll',
            contentType: 'application/json; charset=utf-8',
            beforeSend: () => showLoading(),
            success: function(data) {
                var contents = data.responseData.content;
                showDiscount(contents);
            }
        })
    }
    function showDiscount(content) {
        const discountids = $('#discountTableBody');
        discountids.empty();
        const rows = [];
        for (let i = 0; i < content.length; i++) {
          const discount = content[i];
          const checkbox = `<input type="radio" class="btn-check" id="${discount.discountCode}" name="discount-lists[]" value="${discount.id}">`;
          const label = `<label class="btn btn-outline-primary" for="${discount.discountCode}">${discount.discountCode}</label>`;
          const td = `<td>${checkbox}${label}</td>`;
          if (i % 3 == 0) {
            rows.push('<tr>');
          }
          rows.push(td);
          if ((i + 1) % 3 == 0) {
            rows.push('</tr>');
          }
        }
        discountids.append(rows.join(''));
      }

        $('#form-orderId').on('click', event => {
            const id = $('#form-orderId').val();
            $.ajax({
                method: 'GET',
                url: UrlOrder+'findId/'+id,
                contentType: 'application/json; charset=utf-8',
                beforeSend: () => showLoading(),
                success: function(data) {
                    const order = data.responseData
                    $('#form-personCode').val(order.personResponses.personCode);
                    $('#form-totalPrice').val(order.totalPrice);
                }
            })
             
         });

     