const URL = "http://localhost:8080";

function success() {
    document.getElementById('buttonSuccess').disabled = document.getElementById("floatingInput").value === "" || document.getElementById("floatingPassword").value === "" || document.getElementById("floatingPasswordConfirm").value === "";
}

function addUser(){
    fetch('http://localhost:8080/api/registration', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(
            {
                "username": document.getElementById("floatingInput").value,
                "password" : document.getElementById("floatingPassword").value,
                "passwordConfirm" : document.getElementById("floatingPasswordConfirm").value
            })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw Error(text)})
            }
        })
        .then(data => {
            console.log(JSON.stringify(data))
            window.location.replace(URL + "/login");
        })
        .catch(response => {
            let test = JSON.parse(response.message);
            let error = document.getElementById("error");

            //alert(test.message);
            console.log(`Тест: ${test.message}`);
            error.innerHTML = `<div class="form-control alert alert-danger">${test.message}</div>`
        });
}