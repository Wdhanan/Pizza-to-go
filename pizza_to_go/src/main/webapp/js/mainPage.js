const registerFields = new Map();
const priceFromEachPizza = new Map();

window.onload = async function () {
    // all Eventlisteners
    collectRegisterFields();
    checkToppings();
    if (JSON.parse(sessionStorage.getItem("cart")))
        displayOrder(JSON.parse(sessionStorage.getItem("cart")));

    registerLink.addEventListener("click", showRegisterView, false); // show registerfield hyperlink
    closeButton.addEventListener("click", showLoginView, false); // close registerfield
    registerBtn.addEventListener("click", registerUser, false);
    loginBtn.addEventListener("click", loginUser, false);
    logoutBtn.addEventListener("click", logoutUser, false);
    addToShoppingCartBtn.addEventListener("click", writeOrderInShippingCard, false);
    deleteOrderBtn.addEventListener("click", deleteAllOrders, false);
    orderBtn.addEventListener("click", order, false);

    let token = sessionStorage.getItem('loginToken');
    if (token != null) {
        showLoggedInView();
    } else {
        showLoginView();
    }
    updateButtons();


}

function updateButtons() {
    if (priceFromEachPizza.size == 0) {
        document.querySelector("#orderBtn").disabled = true;
        document.querySelector("#deleteOrderBtn").disabled = true;
    } else {
        document.querySelector("#orderBtn").disabled = false;
        document.querySelector("#deleteOrderBtn").disabled = false;
    }
}

async function loginUser() {
    let username = document.querySelector("#login-username").value;
    let password = document.querySelector("#login-password").value;

    let token = await login(username, password);
    console.log("Login Token " + token);
    sessionStorage.setItem('loginToken', token);
    if (sessionStorage.getItem('loginToken').length == 36) {
        sessionStorage.setItem('username', username);
        await getAllUserInformations();
        showLoggedInView();
        checkCurrentPrice();
        await fetchShoppingCart();
    }
}


async function logoutUser() {
    await logout();
    showLoginView();
}

async function registerUser() {
    let username = document.getElementById("userOwnName").value;
    let password = document.getElementById("registerPasswd").value;
    let firstname = document.getElementById("vname").value;
    let lastname = document.getElementById("nname").value;
    let email = document.getElementById("email").value;
    let telefon = document.getElementById("telphone").value;
    let street = document.getElementById("street").value;
    let streetNumber = document.getElementById("housenumber").value;
    let zip = document.getElementById("postcode").value;
    let city = document.getElementById("city").value;
    let token = await register(username, password, firstname, lastname, email, telefon, street, streetNumber, zip, city);
    sessionStorage.setItem('loginToken', token);
    if (sessionStorage.getItem('loginToken').length == 36) {
        alert("Register erfolgreich!");
        sessionStorage.setItem('username', username);
        await getAllUserInformations();
        showLoggedInView()
        clearRegisterFields();
    }
}