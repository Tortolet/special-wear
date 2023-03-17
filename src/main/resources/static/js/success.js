$('#info').html(
    `<main class="flex-shrink-0">
                      <div class="container">
                        <h1 class="mt-5">Поздравляем!</h1>
                        <p class="lead">Ваш заказ успешно создан.</p>
                      </div>
              </main>`)

fetch('http://localhost:8080/api/remove_all_items', {
    method: 'DELETE'
})
    .then(response => response.text())
    .then(data => console.log(data))
