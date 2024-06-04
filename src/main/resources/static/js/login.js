document.getElementById('loginForm').onsubmit = function (event) {
    event.preventDefault();
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    }).then(response => {
        console.log("HTTP status code:", response.status);
        if (!response.ok) throw new Error('Login failed: ' + response.statusText);
        return response.json();
    }).then(data => {
        console.log("Response data:", data);
        if (data.jwt) {
            console.log('Login successful', data.jwt);
            window.location.href = '/contract';
        } else {
            alert('Login failed: ' + data.message);
        }
    }).catch(error => {
        console.error('Error during login:', error);
        alert(error.message);
    });
};
