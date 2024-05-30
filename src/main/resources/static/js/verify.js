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
            let resultMessage = data.match ? 'Contract is valid.' : 'Contract is invalid.';
            console.log(data.match);
            console.log(resultMessage);
            document.getElementById('verificationResult').innerText = resultMessage;
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('verificationResult').innerText = 'Failed to verify the contract.';
        });
};
