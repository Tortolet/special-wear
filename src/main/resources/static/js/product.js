const URL = "http://localhost:8080";

const productInfo = document.getElementById("product")

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const product = urlParams.get('id')

let wearName;
let type;
let cost;
let desc;
let filename;

let sizeValue;

let role;
let sale;

fetch('http://localhost:8080/api/get_user')
    .then(response => response.json())
    .then(data => {
        console.log(data)
        role = data.roles[0]
        sale = data.employee.sale

        getWear()
    })
    .catch(errror => getWear())

function getWear(){
    fetch("http://localhost:8080/api/get_wear_by_id", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'specialWearId': product
        }
    })
        .then(response => {
            return response.json()
        })
        .then(data => {
            console.log(data);

            wearName = data.wearName;
            type = data.typeSpecialWear[0];
            cost = data.cost;
            desc = data.desc;
            filename = data.filename;


            // if(type === 'DEFAULT')
            //     type = 'Футболка'
            //
            // console.log(type)
            if(sale > 0) {
                if (role !== 'ROLE_USER') {
                    let fullPro = 100 - sale
                    cost = cost * fullPro / 100
                }
            }

            if(filename.length <= 0){
                filename = 'default.jpg'
            }

            productInfo.innerHTML =
                `<div class="reflow-product">
                <div class="ref-media">
                    <div class="ref-preview"><img class="ref-image active" src="/image/${filename}" style="height: 100%"/></div>
                </div>
                <div class="ref-product-data">
                    <h2 class="ref-name"><b>${wearName}</b></h2>
                    <div class="ref-categories"><span class="ref-category">${type}</span></div><strong class="ref-price">${cost}₽</strong><span data-reflow-type="add-to-cart" data-reflow-shoppingcart-url="shopping-cart.html" data-reflow-addtocart-text data-reflow-product="43218622" data-reflow-variant="199976733_s">
                            <div class="ref-product-controls">
                                    <div class="ref-variant">
                                        <label>
                                        <span>Размер</span></br>
                                            <select class="ref-form-control ref-field-variants" id="sizes" name="variant-state" required>
                                            </select>
                                        </label>
                                    </div>
                                </span><a class="ref-button" id="buyButton" href="#">Добавить в корзину</a>
                            </div>
                        </span>
                    <div class="ref-description mt-4">${desc}</div>
                </div>
            </div>`

            sizes()

            const but = document.getElementById('buyButton')
            const toastLive = document.getElementById('liveToast')
            const selectSize = document.getElementById('sizes')
            but.addEventListener('click', function (){
                const toast = new bootstrap.Toast(toastLive)
                toast.show()
                sizeValue = selectSize.value
                addToCart()
            })

        })
}

function sizes() {
    fetch("http://localhost:8080/api/get_wear_size_count", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'specialWearId': product
        }
    })
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.json()
        })
        .then(data => {
            console.log(data);

            const sizes = document.getElementById('sizes')
            const buyButton = document.getElementById('buyButton')
            let count = 0;
            for(let i = 0; i < data.length; i++) {
                if(data[i].count <= 0){
                    count++;
                }
                sizes.innerHTML +=
                    `<option id="size-${data[i].size}" value="${data[i].size}">${data[i].size}</option>`
                if(data[i].count <= 0){
                    let option = document.getElementById(`size-${data[i].size}`);
                    option.disabled = true;
                }
            }
            if(count === data.length){
                buyButton.style.cssText = 'pointer-events: none; box-shadow: 0 0.5rem 1rem rgb(90 27 34);'
                buyButton.classList.add('text-bg-danger')
                buyButton.textContent = 'Нет в наличии'
            }
            if(sizes.length === 0) {
                buyButton.style.cssText = 'pointer-events: none; box-shadow: 0 0.5rem 1rem rgb(90 27 34);'
                buyButton.classList.add('text-bg-danger')
                buyButton.textContent = 'Ошибка'
            }
        })
        .catch(response => {
            let test = JSON.parse(response.message);
            console.log(`Тест: ${test.message}`);
            $('#errors').html(
                `<main class="flex-shrink-0">
                      <div class="container">
                        <h1 class="mt-5">404</h1>
                        <p class="lead">${test.message}.</p>
                      </div>
              </main>`)
        })
}

function addToCart(){
    fetch("http://localhost:8080/api/add_to_cart", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'specialWearId': product,
            'size' : sizeValue
        }
    })
        .then(response => {
            if(!response.ok){
                return response.text().then(text => { throw Error(text)})
            }
            return response.text()
        })
        .then(data => {
            console.log(data);
        })
        .catch(response => {
            console.log("user is null")

            const toast = document.getElementById('liveToast')
            toast.classList.replace('text-bg-success', 'text-bg-danger')

            const toastText = document.getElementById('toastText')
            toastText.textContent = 'Вам необходимо авторизоваться!'
        })
}