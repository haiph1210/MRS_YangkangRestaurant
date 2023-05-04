var KEY_ENTER = 13;
var sort = null;


$('#btn-search').on('click',searchForm)

    function searchForm()   {
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8000/combo/search-form',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                search: $('#form-combo').val(),
                minPrice: $('#form-minPrice').val(),
                maxPrice: $('#form-maxPrice').val()
            }),
            success: function (data) {
                showCombos(data.responseData);
            }
        });
}
$(function () {
    $('#form-modal-container').load('/page/main/menu-service/combo/form-modal.html');
    $('#delete-modal-container').load('/common/modal/delete-modal.html', null, function () {
        $('#delete-modal-btn-remove').on('click', function (event) {
            const selectedComboId = $('.selected .combo-name').attr('value').split('.')[0];
            $.ajax({
                method: 'DELETE',
                url: 'http://localhost:8000/combo/delete',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify($('.selected .combo-name').toArray().map(id => selectedComboId)),
                beforeSend: () => showLoading(),
                success: data => loadCombos(),
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
    loadCombos();
});

function addListeners() {
    $( '#btn-refresh').on('click', event => loadCombos());

    // Khi người dùng thay đổi page size
    $('#page-size').on('change', event => loadCombos());

    // Khi người dùng thay đổi page number và nhấn ENTER
    $('#page-number').on('keypress', event => {
        if (event.which == KEY_ENTER) {
            loadCombos();
        }
    });

    $('.main').on('click', '.card', function (event) {
        if (event.ctrlKey) {
            $(this).toggleClass('selected');
        } else {
            $(this).addClass('selected').siblings().removeClass('selected');
        }
        updateStatus();
    });

    $('.main').on('dblclick', '.card', function () {
        $(this).removeClass('selected');
        updateStatus();
    });
    
    $('#combo-thead').on('click', 'th', function (event) {
        $(this).siblings().find('i').removeClass('fa-sort-up fa-sort-down').addClass('fa-sort');

        const i = $(this).find('i');
        if (i.hasClass('fa-sort')) {
            i.removeClass('fa-sort').addClass('fa-sort-up');
        } else {
            i.toggleClass('fa-sort-up fa-sort-down');
        }

        let type = i.hasClass('fa-sort-up') ? 'asc' : 'desc';
        sort = `${$(this).attr('key')},${type}`
        loadCategories();
    });

    $('#btn-add').on('click', event => {
        $('#combo-form').trigger('reset');
        $('#form-id-container').hide();
        $('#form-modal-btn-update').hide();
        $('#form-modal-btn-create').show();
        $('#form-modal-title').text('Thêm danh mục sản phẩm');
    });

    $('#btn-edit').on('click', event => {
        const selectedComboId = $('.selected .combo-name').attr('value').split('.')[0];
        const selectedComboName = $('.selected .combo-name').attr('value').split('.')[1];
        const selectedDescription = $('.selected .combo-description').attr('value');
      
        $('#combo-form').trigger('reset');
        $('#form-modal-btn-create').hide();
        $('#form-modal-btn-update').show();
        $('#form-id-container').show();
        $('#form-modal-title').text('Cập nhật danh mục sản phẩm');

        const row = $('.selected');
        $('#form-id').val(selectedComboId);
        $('#form-name').val(selectedComboName);
        $('#form-description').val(selectedDescription);
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

function loadCombos() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8000/combo/findPage',
        beforeSend: () => showLoading(),
        success: function (data) {
            var contents = data.responseData.content;
            showPageInfo(contents);
            showCombos(contents);
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



function showCombos(content) {
    const main = $('.main');
    main.empty();
    console.log(main);
  
    for (let i = 0; i < content.length; i++) {
      if (i % 3 === 0) {
        // Thêm một hàng mới sau mỗi 3 combo
        main.append('<div class="row"></div>');
      }
  
      const combo = content[i];
  
      // Thêm combo vào cột
      $('.row:last-child').append(`
        <div class="col-md-4">
          <div class="card mb-4">
          
            <div id="carouselExampleIndicators_${combo.id}" class="carousel slide" data-bs-ride="carousel">
              <ol class="carousel-indicators">
                ${combo.imgUrl.map((imgUrl, index) => `
                  <li data-bs-target="#carouselExampleIndicators_${combo.id}" data-bs-slide-to="${index}" ${index === 0 ? 'class="active"' : ''}></li>
                `).join('')}
              </ol>
              <div class="carousel-inner">
                ${combo.imgUrl.map((imgUrl, index) => `
                  <div class="carousel-item ${index === 0 ? 'active' : ''}">
                    <img src="${imgUrl}" class="d-block w-100" alt="...">
                  </div>
                `).join('')}
              </div>
              <a class="carousel-control-prev" href="#carouselExampleIndicators_${combo.id}" role="button" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </a>
              <a class="carousel-control-next" href="#carouselExampleIndicators_${combo.id}" role="button" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </a>
            </div>   
  
            <div class="card-body combo-body">
              <h5 class="card-title combo-name" value= '${combo.id}.${combo.name}'>${combo.id}.${combo.name}</h5>
              <p class="card-text combo-description" value= '${combo.description}' >${combo.description}</p>
              <p class="card-text combo-price">${combo.price.toLocaleString('vi-VN')}₫</p>
              <button type="button" class="btn btn-primary combo-detail" data-bs-toggle="modal" data-bs-target="#combo-details" data-id="${combo.id}">
                Chi Tiết Sản Phẩm
              </button>
            </div>
  

        <div class="modal fade" id="combo-details" tabindex="-1">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="combo-details-title"></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                    <div class="combo-price"></div>
                    <div class="combo-description"></div>
                    <div class="combo-img"></div>
                  <hr>
                    <div class="menu-list"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
        `);
    }
}

$('.main').on('click', '.combo-detail', function (event) {
    event.preventDefault();
    const comboId = $(this).data('id');
    showComboDetail(comboId)
});

function showComboDetail(id) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8000/combo/id/' + id,
        beforeSend: () => showLoading(),
        success: function (data) {
            var combo = data.responseData;
            const modal = $('#combo-details');
            const imgs = combo.imgUrl;
            modal.find('.modal-title').text("Tên Combo: " +combo.name);
            modal.find('.combo-price').text("Giá: "+combo.price.toLocaleString('vi-VN') + '₫');
            modal.find('.combo-description').text("Thông tin: "+ combo.description);

            // Hiển thị menu
            const menuList = modal.find('.menu-list');
            menuList.empty();
            combo.menus.forEach((menu) => {
                const menuHtml = `

                    <div class="menu-item">
                        <div class="menu-detail">
                            <h7>Tên Menu: ${menu.name}</h7>
                            <img src="${menu.imgUrl}" class="d-block w-50" alt="${menu.name}">
                            <p class="menu-price">Giá: ${menu.price.toLocaleString('vi-VN')}₫</p>
                            <p class="menu-description">Thông tin: ${menu.description}</p>
                            <hr>
                        </div>
                    </div>
                `;
                menuList.append(menuHtml);
            });

            // Mở modal
            modal.modal('show');
        },
        error: () => location.replace('/common/error/404-not-found.html'),
        complete: () => hideLoading()
    });
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
    loadCombos();
}



