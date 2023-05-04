const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

const tabs = $$('.tab-item');
const pains = $$('.tab-pane');
const tabActive = $('.tab-item.active');
const line = $('.line');
line.style.left = tabActive.offsetLeft + "px";
line.style.width = tabActive.offsetWidth + "px";

tabs.forEach((tab, index) => {
    const pain = pains[index];

    tab.onclick = function () {
        console.log(pain);

        $('.tab-item.active').classList.remove('active');
        $('.tab-pane.active').classList.remove('active');

        line.style.left = this.offsetLeft + "px";
        line.style.width = this.offsetWidth + "px";

        this.classList.add('active');
        pain.classList.add('active');

    }
});

document.getElementById("form-submit-menu").addEventListener("click", function() {
    
  });

