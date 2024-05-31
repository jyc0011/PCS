document.getElementById('loginForm').onsubmit = function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const username = formData.get('username');
    const password = formData.get('password');

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to login');
            }
            return response.json();
        })
        .then(data => {
            console.log('Login successful:', data);
            sessionStorage.setItem('jwt', data.jwt);
            document.getElementById('message').textContent = 'Login successful!';
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('message').textContent = 'Login failed!';
        });
};
