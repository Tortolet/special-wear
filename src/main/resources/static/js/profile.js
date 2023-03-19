
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
            employeeInfo.innerHTML =
                `<div class="card mb-4 border-bottom border-top border-start border-end border-primary shadow">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Full Name</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">Johnatan Smith</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Email</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">example@example.com</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Phone</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">(097) 234-5678</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Mobile</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">(098) 765-4321</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <p class="mb-0">Address</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="text-muted mb-0">Bay Area, San Francisco, CA</p>
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
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3">${username}</h5>
                        <p class="text-muted mb-1">${roles}</p>
                        <p class="text-muted mb-4" id="info">${info}</p>
                        <div class="d-flex justify-content-center mb-2">
                            <button type="button" class="btn btn-primary">Редактировать</button>
                        </div>
                    </div>
            </div>`

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