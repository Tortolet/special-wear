const URL = "http://localhost:8080";

const item = document.getElementById('cartItems')
const summary = document.getElementById('summary')

let role;
let sale;

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
        role = data.roles[0]
        sale = data.employee.sale

        getItems()
    })
    .catch(response => {
        let error = JSON.parse(response.message);
        console.log(`Тест: ${error.message}`);
        document.getElementById('cartEmpty').textContent = 'Необходимо авторизоваться'
    })

function getItems() {
    fetch('http://localhost:8080/api/get_cart_items')
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.json()
        })
        .then(data => {
            console.log(data)

            for (let i = 0; i < data.length; i++) {

                let size = data[i].size
                let cost = data[i].specialWears.cost
                let dateEnd = data[i].specialWears.dateEnd
                let dateStart = data[i].specialWears.dateStart
                let desc = data[i].specialWears.desc
                let filename = data[i].specialWears.filename
                let id = data[i].specialWears.id
                let typeSpecialWear = data[i].specialWears.typeSpecialWear[0]
                let wearName = data[i].specialWears.wearName
                let wear_period_month = data[i].specialWears.wear_period_month

                if(filename.length <= 0){
                    filename = 'default.jpg'
                }

                if(sale > 0) {
                    if (role !== 'ROLE_USER') {
                        let fullPro = 100 - sale
                        cost = cost * fullPro / 100
                    }
                }

                item.innerHTML +=
                    `<div class="row border-bottom mb-4">
                        <div class="col-md-2 mb-4 mb-md-0">
                            <div class="bg-image ripple rounded-5 mb-4 overflow-hidden d-block" data-ripple-color="light">
                                <a href="${URL}/products/product?id=${id}">
                                    <img src="/image/${filename}" class="w-100" alt=""/>
                                </a>
                                <a href="#!">
                                    <div class="hover-overlay">
                                        <div class="mask" style="background-color: hsla(0, 0%, 98.4%, 0.2)"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-8 mb-4 mb-md-0">
                            <a href="${URL}/products/product?id=${id}" class="text-white">
                                <p class="fw-bold">${wearName}</p>
                            </a>
                            <p class="mb-1">
                                <span class="text-muted me-2">Размер:</span><span>${size}</span>
                            </p>
                            <p class="mb-4">
                                <a href="" class="text-muted pe-3" id="removeLink">
                                    <small>
                                        <i class="fas fa-trash"></i>Убрать
                                    </small>
                                </a>
                            </p>
                        </div>
                        <div class="col-md-2 mb-4 mb-md-0">
                            <h5 class="mb-2">
                                <span class="align-middle"><b>${cost}₽</b></span>
                            </h5>
                        </div>
                </div>`
                let temp = parseInt(cost, 10)
                sum += temp;
            }

            if(data.length <= 0){
                document.getElementById('cartEmpty').textContent = 'Корзина пуста'
            }

            if(data.length >= 1) {
                summary.innerHTML =
                    `<h5 class="mb-5">Общая сумма</h5>

                    <div class="d-flex justify-content-between mb-3">
                        <span>Товары </span>
                        <span>${sum}₽</span>
                    </div>
                    <div class="d-flex justify-content-between">
                        <span>Доставка </span>
                        <span>Бесплатно</span>
                    </div>
                    <hr class="my-4" />
                    <div class="d-flex justify-content-between fw-bold mb-5">
                        <span>Конечная стоимость </span>
                        <span>${sum}₽</span>
                    </div>
                    
                    <a href="${URL}/success">
                        <button type="button" class="btn btn-primary btn-rounded w-100">
                            Заказать
                        </button>
                    </a>`
            }

            const elems = document.querySelectorAll('#removeLink');
            for(let j = 0; j < data.length; j++){
                elems[j].addEventListener("click", function () {
                    removeItem(data[j].specialWears.id, data[j].size);
                }, true)
            }
        })
        .catch(response => {
            let error = JSON.parse(response.message);
            console.log(`Тест: ${error.message}`);
            document.getElementById('cartEmpty').textContent = 'Необходимо авторизоваться'
        })
}


function removeItem(id, size){
    fetch('http://localhost:8080/api/remove_item_from_cart', {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'specialWearId': id,
            'size' : size
        }
    })
        .then(response => response.json())
        .then(data => console.log(data))
}