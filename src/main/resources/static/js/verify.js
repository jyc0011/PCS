document.getElementById('verifyForm').onsubmit = function (event) {
    event.preventDefault();
    let formData = new FormData(this);
    fetch('/api/verifyContract', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            document.getElementById('verificationResult').innerText = data.isMatch ? 'Contract is valid' : 'Contract is invalid';
        })
        .catch(error => console.error('Error:', error));
};
