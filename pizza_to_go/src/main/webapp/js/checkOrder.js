var numberOfCheckedItems = 0;

/**
 *
 * check if the user already choose 5 toppings
 * */
const checkToppings = async function () {

    let checkboxes = document.getElementsByName("topping");
    numberOfCheckedItems = 0;
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked)
            numberOfCheckedItems++;
    }
    if (numberOfCheckedItems > 5) {
        alert("Du kannst nicht mehr als 5 Beläge auswählen!");
        return false;
    }
    checkCurrentPrice();


}

const checkCurrentPrice = function () {
    let currentSize = document.querySelector(".pizzaSize:checked").value;
    let currentPrice = 0;

    if (currentSize === "small") {
        currentPrice += 4;
        currentPrice += (0.4 * numberOfCheckedItems);
    } else if (currentSize === "medium") {
        currentPrice += 5;
        currentPrice += (0.5 * numberOfCheckedItems);
    } else {
        currentPrice += 6;
        currentPrice += (0.6 * numberOfCheckedItems);
    }

    document.querySelector("#div-current-pizza-price").innerHTML = "Aktueller Preis beträgt " + currentPrice.toFixed(2) + " €";

}

/**
 * Infos Bestellungen:
 * Klein = 4€ --> pro Belag = 40ct
 * Mittel = 5€ --> pro Belag = 50ct
 * Groß = 6€ --> pro Belag = 60ct
 */