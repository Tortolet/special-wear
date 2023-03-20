const URL = "http://localhost:8080";
const userInfo = document.getElementById('userInfo')
const employeeInfo = document.getElementById('employeeInfo')
const historyCard = document.getElementById('history')


let id;
let avatar;
let employee;
let roles;
let username;
let info


let orderNumber;
let size;
let wearName;
let cost;


let sum = 0;

fetch('http://localhost:8080/api/get_user')
    .then(response => {
        if(!response.ok){
            return response.text().then(text => { throw Error(text)})
        }
        return response.json()
    })
    .then(data => {
        console.log(data)

        avatar = data.avatar
        employee = data.employee
        roles = data.roles[0]
        username = data.username
        info = data.info

        if(info === null){
            info = 'Пусто'
        }

        if(roles !== 'ROLE_USER'){
            let emailEmployee = data.employee.email
            let firstName = data.employee.firstName
            let secondName = data.employee.secondName
            let lastName = data.employee.lastName
            let post = data.employee.posts[0]
            let departments = data.employee.departments.name
            let sale = data.employee.sale

            employeeInfo.innerHTML =
                `<div class="card mb-4 border-bottom border-top border-start border-end border-primary shadow">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Полное имя</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">${lastName} ${firstName} ${secondName}</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Email</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">${emailEmployee}</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Цех</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">"${departments}"</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Должность</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">${post}</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Скидка</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">${sale}%</p>
                                </div>
                            </div>
                        </div>
                </div>`
        }

        if(roles === 'ROLE_USER'){
            roles = 'Пользователь'
        }

        userInfo.innerHTML =
            `<div class="card mb-4 border-bottom border-top border-start border-end border-primary shadow">
                    <div class="card-body text-center">
                        <img src="/image/${avatar}" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px; height: 150px">
                        <h5 class="my-3">${username}</h5>
                        <p class="text-muted mb-1">${roles}</p>
                        <p class="text-muted mb-4" id="info">${info}</p>
                        <div class="d-flex justify-content-center mb-2 dropdown-center">
                            <button type="button" class="btn btn-primary" data-bs-toggle="dropdown">Редактировать</button>
                                <ul class="dropdown-menu dropdown-menu-dark">
                                    <li><button class="dropdown-item" style="border-radius: 7px" data-bs-toggle="modal" data-bs-target="#usernameAndAboutMeModal">Изменить логин и "Обо мне"</button></li>
                                    <li><button class="dropdown-item" style="border-radius: 7px" data-bs-toggle="modal" data-bs-target="#passwordModal">Изменить пароль</button></li>     
                                    <li><button class="dropdown-item" style="border-radius: 7px" data-bs-toggle="modal" data-bs-target="#avatarModal">Изменить фотографию профиля</button></li>     
                                </ul>
                        </div>
                    </div>
            </div>`

        let usernameModal = document.getElementById('username')
        usernameModal.value = username

        let aboutMe = document.getElementById('aboutMe')
        aboutMe.value = info


        historyCard.innerHTML =
            `<div class="card mb-4 border-bottom border-top border-start border-end border-primary shadow">
                        <div class="card-body">
                            <div class="row mb-2">
                                <h5><b>История заказов</b></h5>
                            </div>
                            <div id="order">
                            </div>
                        </div>
            </div>`
        cartHistory()
    })
    .catch(response => {
        let error = JSON.parse(response.message)
        console.log(`${error.message}`)
        document.getElementById('error').innerText = 'Необходимо авторизоваться'
    })

function cartHistory() {
    fetch('http://localhost:8080/api/get_user_cart_history')
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.json()
        })
        .then(data => {
            const order = document.getElementById('order')
            console.log(data)

            let repeatNumbers = []

            for (let i = 0; i < data.length; i++) {

                id = data[i].id
                orderNumber = data[i].orderNumber;
                size = data[i].sizeCount.size;
                wearName = data[i].sizeCount.specialWears.wearName;
                cost = data[i].sizeCount.specialWears.cost;

                if(!repeatNumbers.includes(orderNumber) && data.length > 0){
                    order.innerHTML +=
                        `<div class="alert border mb-auto mt-3">
                    <div class="row mb-4">
                                <h5><b>№${orderNumber}</b></h5>
                            </div>
                            <div class="row">
                                <h6><b>Товары:</b></h6>
                            </div>
                            <div class="row" >
                                <ol class="ms-4" id="itemsCartHistory${id}">
                                </ol>
                            </div>
                            <div class="row" id="sum${id}">

                            </div>
                    </div>
                </div>`

                    const itemsCartHistory = document.getElementById(`itemsCartHistory${id}`)
                    for (let j = 0; j < data.length; j++) {
                        if(orderNumber === data[j].orderNumber){
                            itemsCartHistory.innerHTML +=
                                `<li class="mb-2">${wearName}
                                        </br><span>Размер: ${size}</span></br>
                                        <span>Стоимость: ${cost}₽</span>
                                    </li>`
                            sum += cost
                        }

                    }
                    const summary = document.getElementById(`sum${id}`)
                    summary.innerHTML = `<h6>
                                    <b>Общая стоимость:</b> ${sum}₽
                                </h6>`

                    sum = 0;
                    if(!repeatNumbers.includes(orderNumber)){
                        repeatNumbers.push(orderNumber)
                    }
                }

            }
            if(data.length === 0){
                order.innerHTML =
                    `<h3 class="mt-5 mb-5 text-center"><b>История заказов пуста</b></h3>`
            }

        })
        .catch(response => {
            let error = JSON.parse(response.message)
            console.log(`${error.message}`)
        })

}

function success() {
    document.getElementById('saveButton').disabled = document.getElementById("username").value === "";
}

const saveButton = document.getElementById('saveButton')
saveButton.addEventListener('click', () => {
    fetch('http://localhost:8080/api/update_user_info', {
        method: "PUT",
        headers: {
            'Accept': '*/*',
            'Content-Type': 'text/plain',
            'username': document.getElementById('username').value
        },
        body: document.getElementById('aboutMe').value
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)
            if(document.getElementById('username').value === username){
                location.reload()
            }
            if(document.getElementById('username').value !== username){
                location.href = `${URL}/logout`
            }

        })
})

const saveButtonPassword = document.getElementById('saveButtonPassword')
saveButtonPassword.addEventListener('click', () => {
    fetch('http://localhost:8080/api/update_user_password', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'oldPassword': document.getElementById('oldPass').value,
            'newPassword': document.getElementById('newPass').value,
            'newPasswordConfirm': document.getElementById('newPassConf').value
        }
    })
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.text()
        })
        .then(data => {
            console.log(data)
            const errors = document.getElementById('errors')
            errors.innerHTML = `<div class="form-control alert alert-success">${data}</div>`
        })
        .catch(response => {
            console.log(response.message)
            const errors = document.getElementById('errors')
            errors.innerHTML = `<div class="form-control alert alert-danger">${response.message}</div>`
        })
})

let formData = new FormData();
const saveButtonAvatar = document.getElementById('saveButtonFile')
saveButtonAvatar.addEventListener('click', () => {
    const fileField = document.querySelector('input[type="file"]');
    formData.append('avatar', fileField.files[0])

    fetch('http://localhost:8080/api/update_user_avatar', {
        method: "PUT",
        body: formData
    })
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            location.reload()
        })
        .catch(reason => {
            let errorJSON = JSON.parse(reason.message);
            let errorAvatar = document.getElementById("errorAvatar");

            //alert(test.message);
            console.log(`Тест: ${errorJSON.message}`);
            errorAvatar.innerHTML = `<div class="form-control alert alert-danger">${errorJSON.message}</div>`
        })
})