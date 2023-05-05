UrlInfo = "http://localhost:8002/api/restaurant/info/"
$(document).ready(function () {
    loadInfo()
})

function loadInfo() {
    $.ajax({
        method: 'GET',
        url: UrlInfo+'findAll' ,
        beforeSend: () => showLoading(),
        success: function (data) {
            showInfo(data.responseData.content);
            // updateStatus();
            console.log("data",data.responseData.content);
        },
        error: () => location.replace('/common/error/404-not-found.html'),
        complete: () => hideLoading()
    });
}

function showInfo(contents) {
    const main = $('#main');
    main.empty();
    for(let info of contents) {
        main.append(`
        <div class="container mt-5">
        <div class="row">
            <div class="col-md-6">
                <img src="${info.imgUrl}" alt="${info.imgUrl}" class="img-fluid">
            </div>
            <div class="col-md-6">
                <h2>${info.name}</h2>
                <p>${info.description}</p>
                <p>Rating:  ${createStarRating(info.star)}   (1234 votes)</p>
                <ul class="list-unstyled">
                    <li><strong>Email:</strong> ${info.email}</li>
                    <li><strong>Address:</strong> ${info.address}</li>
                    <li><strong>Phone:</strong> ${info.phoneNumber}</li>
                    <li><strong>HostLine:</strong> ${info.hostline}</li>
                    <li><button class="nav-link "><a
                    href="/first-index/index-input.html" class="text-decoration-none"> Menu</a></button></li>
                </ul>
            </div>
        </div>
    </div>
        `);
}
}

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

function createStarRating(rating) {
    const roundedRating = Math.round(rating * 2) / 2;
    const stars = [];
    for (let i = 0; i < 5; i++) {
      if (roundedRating >= i + 0.5) {
        stars.push('<span class="fa fa-star checked" style="color: yellow;"></span>');
      } else if (roundedRating >= i) {
        stars.push('<span class="fa fa-star-half-o checked" style="color: yellow;"></span>');
      } else {
        stars.push('<span class="fa fa-star-o" style="color: yellow;"></span>');
      }
    }
    return stars.join('');
  }