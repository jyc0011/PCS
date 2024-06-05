document.getElementById('verifyForm').onsubmit = function (event) {
    event.preventDefault();
    let formData = new FormData(this);

    fetch('/check/verify', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Response data:', data); // 추가하여 응답 데이터를 출력
            let resultMessage = data.match ? '계약서가 원본과 동일합니다' : '계약서와 원본이 일치하지 않습니다';
            console.log(data.match);
            alert(resultMessage);
            // console.log(resultMessage);
            // document.getElementById('verificationResult').innerText = resultMessage;
        })
        .catch(error => {
            console.error('Error:', error);
            alert("계약서를 읽어오는데 실패했습니다");
            // console.log(error);
            // document.getElementById('verificationResult').innerText = '계약서를 읽어오는데 실패했습니다';
        });
};
