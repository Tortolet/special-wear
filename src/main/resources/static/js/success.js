fetch('http://localhost:8080/api/remove_all_items', {
    method: 'DELETE'
})
    .then(response => {
        if(!response.ok){
            return response.text().then(text => { throw Error(text)})
        }
        return response.text()
    })
    .then(data => {
        console.log(data)

        $('#info').html(
            `<main class="flex-shrink-0">
                      <div class="container">
                        <h1 class="mt-5">Поздравляем!</h1>
                        <p class="lead">Ваш заказ успешно создан.</p>
                        <p id="orderNumber"></p>
                      </div>
              </main>`)

        const num = document.getElementById('orderNumber')
        num.innerText = ` Номер заказа: №${data}`
    })
    .catch(response => {
        let test = JSON.parse(response.message);
        if(test.statusCode === 400) {
            console.log(`Тест: ${test.message}`);
            $('#info').html(
                `<main class="flex-shrink-0">
                      <div class="container">
                        <h1 class="mt-5">Ошибка</h1>
                        <p class="lead">Пользователь не авторизован</p>
                        <p id="orderNumber"></p>
                      </div>
              </main>`)
        }
        if(test.statusCode === 404 || test.status === 404){
            console.log(`Тест: ${test.message}`);
            $('#info').html(
                `<main class="flex-shrink-0">
                      <div class="container">
                        <h1 class="mt-5">Ошибка</h1>
                        <p class="lead">${test.message}</p>
                        <p id="orderNumber"></p>
                      </div>
              </main>`)
        }
    })
