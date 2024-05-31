document.getElementById('loginForm').onsubmit = function (event) {
    event.preventDefault();  // 폼의 기본 제출 동작을 방지

    let formData = new FormData(this);
    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: 'user',
            password: 'pass'
        })
    }).then(response => response.json())  // 응답을 JSON 형태로 파싱
        .then(data => {
            if (data.jwt) {
                console.log('Login successful', data.jwt);
                // 로그인 성공 후 작업, 예: 페이지 리다이렉션
                window.location.href = '/contract';
            } else {
                console.error('Login failed', data);
                alert('Login failed: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error during login:', error);
        });
};
