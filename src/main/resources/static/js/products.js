const URL = "http://localhost:8080";

fetch('http://localhost:8080/api/get_wears')
    .then(response => response.json())
    .then(data => {
        console.log(data);

        const item = document.getElementById("products");

        for(let i = 0; i < data.length; i++){

            let cost = data[i].cost;
            let dateEnd = data[i].dateEnd;
            let dateStart = data[i].dateStart;
            let desc = data[i].desc;
            let filename = data[i].filename;
            let id = data[i].id;
            let typeSpecialWear = data[i].typeSpecialWear[0];
            let wearName = data[i].wearName;
            let wear_period_month = data[i].wear_period_month;

            item.innerHTML +=
                `<div class="col">
                    <div class="card bg-dark h-100">
                        <a class="ref-product" href="${URL}/products/product?id=${id}">
                            <img class="card-img-top w-100 d-block" src="/image/${filename}" alt="${wearName}"/>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title text-white"><b>${wearName}</b></h5>
                                <p class="card-text">${desc}</p>
                                <p><b class="text-white">${cost}₽</b></p>
                        </a>
<!--                                <button id="cart" class="btn btn-primary mt-auto" type="button">Добавить в корзину</button>-->
                            </div>
                        
                    </div>
                </div>`
        }
        // const elems = document.querySelectorAll('#cart');
        // for(let j = 0; j < data.length; j++){
        //     elems[j].addEventListener("click", function () { console.log(data[j].id); }, true)
        // }
    });