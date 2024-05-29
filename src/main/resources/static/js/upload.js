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
    }).then(response => {
        if (!response.ok) {
            console.error('HTTP 오류', response.status, response.statusText);
            return response.text().then(text => { throw new Error(text) });
        }
        return response.blob();
    }).then(blob => {
        console.log('성공: 이미지를 받았습니다.');
        let imageUrl = URL.createObjectURL(blob);
        document.getElementById('preview').innerHTML = `<img src="${imageUrl}" style="width: 1000px; margin: 10px 170px; max-width: 100%;" alt="생성된 이미지"/>`;
    }).catch(error => {
        console.error('Fetch 오류:', error);
    });
};
