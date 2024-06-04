document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('idcheck').addEventListener('click', function() {
        const userid = document.getElementById('userid').value;
        if (!userid) {
            alert("Please enter your ID!");
            return;
        }
        fetch('/idcheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userid: userid })
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.available) {
                    document.getElementById('isIdChecked').value = 'Y';
                } else {
                    document.getElementById('isIdChecked').value = 'N';
                }
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('sendcode').addEventListener('click', function() {
        const email = document.getElementById('email').value;
        fetch('/create/sendcode', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email })
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.result) {
                    document.getElementById('emailCheckDiv').style.display = '';
                }
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('signupForm').addEventListener('submit', function(event) {
        if (!checkForm()) {
            event.preventDefault(); // Prevent form from submitting if checks fail
        }
    });

    function checkForm() {
        const isIdChecked = document.getElementById('isIdChecked').value === 'Y';
        const passwordsMatch = checkPasswords();
        const emailVerified = document.getElementById('emailAuth').value === 'verified';
        const termsAccepted = document.getElementById('terms1').checked && document.getElementById('terms2').checked;

        if (!isIdChecked) alert("Please verify your ID!");
        if (!passwordsMatch) alert("Passwords do not match!");
        if (!emailVerified) alert("Please verify your email!");
        if (!termsAccepted) alert("You must agree to the terms and conditions!");

        return isIdChecked && passwordsMatch && emailVerified && termsAccepted;
    }

    function checkPasswords() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('passwordConfirm').value;
        return password === confirmPassword;
    }
});
