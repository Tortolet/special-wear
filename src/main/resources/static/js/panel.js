let selectedDepartment

fetch('http://localhost:8080/api/get_departments')
    .then(response => response.json())
    .then(data => {
        console.log(data)
        for (let i = 0; i < data.length; i++) {
            let depId = data[i].id
            let depName = data[i].name
            let selectedDep = document.getElementById('department')

            selectedDep.innerHTML += `<option value="${depId}">${depName}</option>`
        }
        let selectedDep = document.getElementById('department')
        selectedDep.addEventListener('change', (event) => {
            selectedDepartment = event.target.value
            console.log(selectedDepartment)
        })
    })

function editEmployee() {
    fetch('http://localhost:8080/api/update_employee', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'depId': selectedDepartment,
            'employeeId': document.getElementById('idEmployee').value
        },
        body: JSON.stringify(
            {
                "firstName" : document.getElementById('firstName').value,
                "secondName" : document.getElementById('secondName').value,
                "lastName" : document.getElementById('lastName').value,
                "email" : document.getElementById('email').value,
                "posts" : [
                    document.getElementById('post').value
                ],
                "sale" : document.getElementById('sale').value
            })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)
            location.reload()
        })
}

function deleteEmployee(){
    fetch('http://localhost:8080/api/delete_employee', {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'employeeId': document.getElementById('idEmployee').value
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data)
            location.reload();
        });

}

document.addEventListener("DOMContentLoaded", function(event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) =>{
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

// Validate that all variables exist
        if(toggle && nav && bodypd && headerpd){
            toggle.addEventListener('click', ()=>{
// show navbar
                nav.classList.toggle('show')
// change icon
                toggle.classList.toggle('bx-x')
// add padding to body
                bodypd.classList.toggle('body-pd')
// add padding to header
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle','nav-bar','body-pd','header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink(){
        if(linkColor){
            linkColor.forEach(l=> l.classList.remove('active'))
            this.classList.add('active')
        }
    }

    function dataInit() {
        if(linkColor){
            linkColor.forEach(l => {
                let table = document.getElementById('table')
                if(l.id === document.getElementById('employees').id && l.classList.contains('active')){
                    let id
                    let email;
                    let firstName;
                    let lastName;
                    let secondName;
                    let sale;
                    let departments;
                    let post;

                    document.getElementById('naming').textContent = 'Сотрудники'

                    table.innerHTML =
                        `<div class="container" id="table">
                            <table id="selectedColumn" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th class="th-sm">ID
                                    </th>
                                    <th class="th-sm">Фамилия
                                    </th>
                                    <th class="th-sm">Имя
                                    </th>
                                    <th class="th-sm">Отчество
                                    </th>
                                    <th class="th-sm">Email
                                    </th>
                                    <th class="th-sm">Цех
                                    </th>
                                    <th class="th-sm">Должность
                                    </th>
                                    <th class="th-sm">Скидка
                                    </th>
                                    <th class="th-sm">Действие
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="rows">
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>ID
                                    </th>
                                    <th>Фамилия
                                    </th>
                                    <th>Имя
                                    </th>
                                    <th>Отчество
                                    </th>
                                    <th>Email
                                    </th>
                                    <th>Цех
                                    </th>
                                    <th>Должность
                                    </th>
                                    <th>Скидка
                                    </th>
                                    <th>Действие
                                    </th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>`

                    fetch('http://localhost:8080/api/get_employees')
                        .then(response => response.json())
                        .then(data => {
                            for (let i = 0; i < data.length; i++) {

                                const rows = document.getElementById('rows')

                                id = data[i].id
                                email = data[i].email;
                                firstName = data[i].firstName;
                                lastName = data[i].lastName
                                secondName = data[i].secondName
                                sale = data[i].sale
                                departments = data[i].departments.name
                                post = data[i].posts[0]

                                rows.innerHTML +=
                                    `<tr>
                                        <td>${id}</td>
                                        <td>${lastName}</td>
                                        <td>${firstName}</td>
                                        <td>${secondName}</td>
                                        <td>${email}</td>
                                        <td>${departments}</td>
                                        <td>${post}</td>
                                        <td>${sale}</td>
                                        <td>
                                            <div class="d-flex">
                                                <button class="btn btn-warning p-2 flex-fill me-2" id="edit-${id}" data-bs-toggle="modal" data-bs-target="#employeeEdit">Редактировать</button>
                                                <button class="btn btn-danger p-2 flex-fill" id="delete-${id}" data-bs-toggle="modal" data-bs-target="#deleteEmployee">Удалить</button>
                                            </div>
                                        </td>
                                    </tr>`

                            }
                            $(document).ready(function () {
                                $('#selectedColumn').DataTable({
                                    "aaSorting": [],
                                    columnDefs: [{
                                        orderable: false,
                                        targets: 3
                                    }]
                                });
                                $('.dataTables_length').addClass('bs-select');
                            });

                            const editButtons = document.querySelectorAll('.btn-warning')
                            for (let i = 0; i < data.length; i++) {
                                editButtons[i].addEventListener("click", function () {
                                    document.getElementById('lastName').value = data[i].lastName
                                    document.getElementById('firstName').value = data[i].firstName
                                    document.getElementById('secondName').value = data[i].secondName
                                    document.getElementById('email').value = data[i].email
                                    document.getElementById('sale').value = data[i].sale
                                    document.getElementById('post').value = data[i].posts[0]
                                    document.getElementById('idEmployee').value = data[i].id
                                }, true)
                            }

                            const submitEditButton = document.getElementById('saveButton')
                            submitEditButton.addEventListener('click', () => {
                                editEmployee()
                            })

                            const deleteButton = document.querySelectorAll('.btn-danger')
                            for (let i = 0; i < data.length; i++) {
                                deleteButton[i].addEventListener("click", function () {
                                    document.getElementById('idEmployee').value = data[i].id
                                }, true)
                            }

                            const deleteButtonModal = document.getElementById('deleteButton')
                            deleteButtonModal.addEventListener('click', () => {
                                deleteEmployee()
                            })
                        })
                }

            })
        }
    }

    linkColor.forEach(l=> l.addEventListener('click', colorLink))

    linkColor.forEach(l=> l.addEventListener('click', dataInit))
});