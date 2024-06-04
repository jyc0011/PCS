document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();  // Prevent the form from submitting via the browser

        const formData = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        };

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    window.location.href = '/dashboard';  // Redirect to dashboard if login is successful
                } else {
                    alert('Login failed: ' + data.message);  // Show error message
                }
            })
            .catch(error => {
                console.error('Error during login:', error);
            });
    });
});
