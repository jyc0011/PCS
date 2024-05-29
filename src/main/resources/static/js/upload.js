document.getElementById('uploadForm').onsubmit = function (event) {
    event.preventDefault();
    let formData = new FormData(this);
    fetch('/api/processContract', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            document.getElementById('preview').innerHTML = `<img src="data:image/png;base64,${data.image}" />`;
        })
        .catch(error => console.error('Error:', error));
};