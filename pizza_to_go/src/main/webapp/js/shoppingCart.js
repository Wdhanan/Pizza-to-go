var index = 1;
var price;

class Pizza {
    constructor() {
        this.topping = [];
        this.size = "large";
    }
}

function getPrice(size) {
    switch (size) {
        case "small":
            return 4;
        case "medium":
            return 5;
        default:
            return 6;
    }
}

async function fetchShoppingCart() {
    return await fetch('/data/cart', {
        method: 'get',
        headers: {
            'Content-type': 'application/json',
            'loginToken': sessionStorage.getItem('loginToken')
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.pizza[0]) {
                if (data.pizza[0].size) {
                    displayOrder(data);
                    sessionStorage.setItem('cart', JSON.stringify(data));
                }
            }
        })
        .catch((error) => {
            console.error("Error", error)
        });
}

async function order() {
    let hasAccess = await checkAccess();
    if (hasAccess) {
        fetch('data/order', {
            method: 'post',
            headers: {
                'Content-type': 'application/json',
                'loginToken': sessionStorage.getItem('loginToken'),
            },
            body: sessionStorage.getItem('cart')
        }).then(() => {
            deleteAllOrders();
            alert("Bestellung aufgegeben!");
        }).catch((error) => {
            console.error("Error", error)
        });
    } else {
        await logoutUser();
        alert("Sitzung abgelaufen");
    }
}

function resetAllFieldsFromShoppingCard() {
    document.querySelector("#div-all-orders").innerHTML = "";
    priceFromEachPizza.clear();
    setCurrentPrice();
    document.querySelector("#orderBtn").disabled = true;
    document.querySelector("#deleteOrderBtn").disabled = true;
    uncheckEachElement();
}

async function deleteAllOrders() {
    let hasAccess = await checkAccess();
    if (hasAccess) {
        resetAllFieldsFromShoppingCard();
        sessionStorage.removeItem("cart");
        fetch('data/cart', {
            method: 'delete',
            headers: {
                'Content-type': 'application/json',
                'loginToken': sessionStorage.getItem('loginToken')
            }
        })
            .catch((error) => {
                console.error("Error", error)
            });
    } else {
        await logoutUser();
        alert("Sitzung abgelaufen");
    }

}

async function writeOrderInShippingCard() {
    await insertUserInformation();
}

const uncheckEachElement = function () {
    let checkboxesTopping = document.getElementsByName("topping");
    for (let i = 0; i < checkboxesTopping.length; i++) {
        if (checkboxesTopping[i].checked) {
            document.getElementById(checkboxesTopping[i].id).checked = false;
        }
    }
    numberOfCheckedItems = 0;
    checkCurrentPrice();
}

/*
set inside the row the userdata
*/
const insertUserInformation = async function () {
    let hasAccess = await checkAccess();
    if (hasAccess) {
        let pizza = new Pizza;
        let checkboxesSize = document.getElementsByName("pizzaSize");
        checkboxesSize.forEach((item) => {
            if (item.checked) pizza.size = item.value;
        });

        let checkboxesTopping = document.getElementsByName("topping");
        checkboxesTopping.forEach((checkbox) => {
            if (checkbox.checked) pizza.topping.push(checkbox.value);
        });
        let order;
        try {
            order = JSON.parse(sessionStorage.getItem("cart"));
        } catch {
        }
        if (!order) {
            order = {'pizza': []}
        }
        order.pizza.push(pizza);
        displayOrder(order);
        fetch('data/cart', {
            method: 'post',
            headers: {
                'Content-type': 'application/json',
                'loginToken': sessionStorage.getItem('loginToken')
            },
            body: JSON.stringify(order)
        }).catch((error) => {
            console.error("Error", error)
        });
        sessionStorage.setItem("cart", JSON.stringify(order));
        uncheckEachElement();
    } else {
        await logoutUser();
        alert("Sitzung abgelaufen");
    }

}

function setCurrentPrice() {
    let tmpPrice = 0 * 1;
    priceFromEachPizza.forEach((value) => {
        tmpPrice = +value + tmpPrice;
    })
    document.getElementById("div-over-all-price").innerHTML = tmpPrice.toFixed(2) + " €";
}

function displayOrder(order) {
    resetAllFieldsFromShoppingCard();
    order.pizza.forEach(insertPizzaIntoTable);
    updateButtons();
}

function insertPizzaIntoTable(pizza) {

    let field = document.createElement("fieldset");
    field.style.margin = "1em";

    let label = document.createElement("legend");
    label.style.fontSize = "25px";

    let div = document.querySelector("#div-all-orders");

    let table = document.createElement("table");
    table.style.backgroundColor = "#1D1D1D";

    table.style.fontSize = "20px";
    table.id = index;
    table.setAttribute('class', 'table-for-order');
    let tableRow = table.insertRow();
    let tableData = tableRow.insertCell();
    field.appendChild(table);
    div.append(field);

    //insert cell into table
    tableData = tableRow.insertCell();
    tableData.id = "tdInfo" + index;

    let basePrice = getPrice(pizza.size);
    let currentPrice = basePrice;
    label.style.fontWeight = 600;
    switch (pizza.size) {
        case "small":
            label.innerHTML = "Pizza Klein"
            break;
        case "medium":
            label.innerHTML = "Pizza Mittel"
            break;
        default:
            label.innerHTML = "Pizza Groß"
    }
    let li = document.createElement("li");
    field.appendChild(label);

    if (pizza.topping.length) {
        let tableData = document.getElementById("tdInfo" + index);
        let ul = document.createElement("ul");
        tableData.appendChild(document.createTextNode("Beläge"));
        tableData.appendChild(document.createElement("br"));
        pizza.topping.forEach((item) => {
            let li = document.createElement("li");
            li.innerHTML = item;
            ul.appendChild(li);
            tableData.appendChild(ul);
            currentPrice += basePrice / 10;
        });
    }
    priceFromEachPizza.set("pizza" + index, currentPrice.toFixed(2));
    setCurrentPrice();
    tableData.appendChild(document.createTextNode("Preis " + currentPrice.toFixed(2) + "€"));
    table.insertRow();

    index++;
}

