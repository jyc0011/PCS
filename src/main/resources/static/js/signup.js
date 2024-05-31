document.getElementById('signupForm').onsubmit = function(event) {
    event.preventDefault();
    let formData = new FormData(this);

    fetch('/signup', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('signupResult').innerText = data.message;
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('signupResult').innerText = 'Failed to sign up.';
        });
};
