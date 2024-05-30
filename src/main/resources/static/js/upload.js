document.getElementById('uploadForm').onsubmit = function (event) {
    event.preventDefault();
    let formData = new FormData(this);

    console.log("폼 데이터 디버깅:");
    for (var pair of formData.entries()) {
        console.log(pair[0]+ ', ' + pair[1]);
    }

    fetch('/contract/execute', {
        method: 'POST',
        body: formData
    }).then(response => response.json())
        .then(data => {
            // 이미지 처리
            let imageUrl = `data:image/png;base64,${data.image}`;
            document.getElementById('preview').innerHTML = `<img src="${imageUrl}" style="width: 100%; max-width: 1000px;" alt="생성된 이미지"/>`;

            // PDF 처리
            let pdfData = `data:application/pdf;base64,${data.pdf}`;
            let downloadLink = document.createElement('a');
            downloadLink.href = pdfData;
            downloadLink.download = "contract.pdf";
            downloadLink.text = "Download PDF";
            downloadLink.style.display = "block";
            document.getElementById('preview').appendChild(downloadLink);
        })
        .catch(error => console.error('Error:', error));
}