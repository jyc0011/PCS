document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

let scrollToTopBtn = document.createElement('button');
scrollToTopBtn.innerHTML = 'Top ↑';
scrollToTopBtn.style.position = 'fixed';
scrollToTopBtn.style.bottom = '20px';
scrollToTopBtn.style.right = '20px';
scrollToTopBtn.style.display = 'none';
scrollToTopBtn.style.zIndex = '1000';
scrollToTopBtn.style.backgroundColor = 'rgb(255,255,255)'; // 녹색 배경
scrollToTopBtn.style.color = 'black';
scrollToTopBtn.style.cursor = 'pointer';
scrollToTopBtn.style.padding = '10px 15px';
scrollToTopBtn.style.border = 'none'; // 테두리 없음
scrollToTopBtn.style.borderRadius = '5px'; // 둥근 모서리
scrollToTopBtn.onclick = function () {
    window.scrollTo({ top: 0, behavior: 'smooth' });
};

scrollToTopBtn.onmouseover = function() {
    this.style.backgroundColor = 'rgb(255,237,196)';
};
scrollToTopBtn.onmouseout = function() {
    this.style.backgroundColor = 'rgb(255,255,255)';
};

document.body.appendChild(scrollToTopBtn);

window.onscroll = function () {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        scrollToTopBtn.style.display = 'block';
    } else {
        scrollToTopBtn.style.display = 'none';
    }
};