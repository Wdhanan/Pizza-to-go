function showLoginView() {

    setVisibility("div-login", true);
    setVisibility("div-registration", false);
    setVisibility("div-right-bar", false);
    setVisibility("div-logged-in", false);
    setVisibility("div-overlay", false);
    setVisibility("div-logged-in-view", false);

    clearRegisterFields();

    document.querySelector("#login-username").value = "";
    document.querySelector("#login-password").value = "";

    resetAllFieldsFromShoppingCard();
    numberOfCheckedItems = 0;
}

function showRegisterView() {
    setVisibility("div-login", false);
    setVisibility("div-registration", true);
    setVisibility("div-right-bar", false);
    setVisibility("div-logged-in", false);
    setVisibility("div-overlay", true);
    setVisibility("div-logged-in-view", false);

    document.querySelector("#login-username").value = "";
    document.querySelector("#login-password").value = "";

}

function showLoggedInView() {
    setVisibility("div-login", false);
    setVisibility("div-registration", false);
    setVisibility("div-right-bar", true);
    setVisibility("div-logged-in", true);
    setVisibility("div-overlay", false);
    setVisibility("div-logged-in-view", true);
    setUserLabel();
    setDeliveryAddress();

    document.querySelector("#login-username").value = "";
    document.querySelector("#login-password").value = "";
}

function setVisibility(elementId, visible) {
    const element = document.getElementById(elementId);
    if (visible === true) {
        element.classList.remove("hidden");
    } else {
        element.classList.add("hidden")
    }
}

function setUserLabel() {
    let user = JSON.parse(sessionStorage.user);

    document.querySelector("#current-name").innerHTML = user.firstname + " " + user.lastname;
}

function setDeliveryAddress(){
    let user = JSON.parse(sessionStorage.user);

    document.querySelector("#table-data-first-and-lastname").innerHTML = user.firstname + " " + user.lastname;
    document.querySelector("#table-data-street-housenumber").innerHTML = user.street + " " + user.streetNumber;
    document.querySelector("#table-data-my-zip-city").innerHTML = user.zip + " " + user.city;
}

function clearRegisterFields() {
    registerFields.forEach((values)=>{
        values.value = "";
        values.style.borderColor = "rgba(255, 255, 255, 0.4)";
        values.style.boxShadow = "";
    })
    setAllBoolsFalse();
    checkIfAllOk();
}
