/*
	Check every field from the registerWindow
*/

var nName;
var vName
var street;
var postalCode;
var telphone;
var city;
var houseNumber;
var email;
var password;
var userLoginName;

const checkTel = function () {
    let tel = registerFields.get("telphone").value;
    if (tel.length === 0) {
        registerFields.get("telphone").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("telphone").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }
    let nameRegEx = /^[0-9]{5,}$/;
    telphone = (!nameRegEx.test(tel) ? false : true);

    setColor("telphone", telphone);
}

const checkNName = function () {
    let nNameInput = registerFields.get("nname").value;
    if (nNameInput.length === 0) {
        registerFields.get("nname").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("nname").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    let nameRegEx = /^[A-ZÄÖÜ][a-zäöüß]+(( |-)[A-ZÄÖÜ][a-zäöüß]+)*$/;

    nName = (!nameRegEx.test(nNameInput) ? false : true);
    setColor("nname", nName);
    checkIfAllOk();
}

const checkVName = function () {
    let vNameInput = registerFields.get("vname").value;
    if (vNameInput.length === 0) {
        registerFields.get("vname").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("vname").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }


    let nameRegEx = /^[A-ZÄÖÜ][a-zäöüß]+(( |-)[A-ZÄÖÜ][a-zäöüß]+)*$/;

    vName = (!nameRegEx.test(vNameInput) ? false : true);
    setColor("vname", vName);
    checkIfAllOk();
}

const checkPostCode = function () {
    let postCode = registerFields.get("postcode").value;
    if (postCode.length === 0) {
        registerFields.get("postcode").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("postcode").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    let nameRegEx = /^[0-9]{5}$/;
    postalCode = (!nameRegEx.test(postCode) ? false : true);

    setColor("postcode", postalCode);
    checkIfAllOk();
}

const checkMail = function () {
    let emailInput = registerFields.get("email").value
    if (emailInput.length === 0) {
        registerFields.get("email").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("email").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }
    let emailRegEx = /^([A-ZAÖÜÄ]|[a-zaöüä]){4}\d{4}@stud\.(hs-kl|fh-kl)\.de$/;


    email = (!emailRegEx.test(emailInput) ? false : true);
    setColor("email", email);
    checkIfAllOk();
}

const checkHouseNumber = function () {
    let numberInput = registerFields.get("housenumber").value;
    if (numberInput.length === 0) {
        registerFields.get("housenumber").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("housenumber").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    let houseNumberRegEx = /^\d{1,}([A-Z]|[a-z])?$/;

    houseNumber = (!houseNumberRegEx.test(numberInput) ? false : true);
    setColor("housenumber", houseNumber);
    checkIfAllOk();

}

const checkStreet = function () {
    let streetInput = registerFields.get("street").value;
    if (streetInput.length === 0) {
        registerFields.get("street").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("street").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    let streetRegEx = /^[A-ZÄÖÜ][a-zäöüß]+(( |-)[A-ZÄÖÜ][a-zäöüß]+)*$/;

    street = (!streetRegEx.test(streetInput) ? false : true);
    setColor("street", street);
    checkIfAllOk();

}

const checkCity = function () {
    let cityInput = registerFields.get("city").value;
    if (cityInput.length === 0) {
        registerFields.get("city").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("city").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    let cityRegEx = /^[A-ZÄÖÜ][a-zäöüß]+(( |-)[A-ZÄÖÜ][a-zäöüß]+)*$/;

    city = (!cityRegEx.test(cityInput) ? false : true);
    setColor("city", city);
    checkIfAllOk();

}

async function checkUserName () {
    let userNameInput = registerFields.get("userOwnName").value;
    if (userNameInput.length === 0) {
        registerFields.get("userOwnName").style.borderColor = "rgba(255, 255, 255, 0.08)";
        registerFields.get("userOwnName").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    if (userNameInput.length > 4) {
        userLoginName = await checkIsAvailable(userNameInput);
        console.log(userLoginName);
    } else {
        userLoginName = false;
    }
    setColor("userOwnName", userLoginName);

    checkIfAllOk();
}

const checkIfAllOk = function () {

    let allOk = vName && nName && postalCode && city && email && houseNumber && street && userLoginName && password;
    if (allOk) {
        document.querySelector("#registerBtn").disabled = false;
    } else {
        document.querySelector("#registerBtn").disabled = true;
    }
}

const checkPassword = function () {
    var passwdInput = document.querySelector("#registerPasswd").value;
    var len = passwdInput.length;
    var regExHasNumber = /\d/;
    var regExHasUpperCase = /[A-Z]/;
    var regExHasLowerCase = /[a-z]/;
    var regExHasSpecialSign = /[!?§$%&#*+-.;]/;
    var hasNumber = regExHasNumber.test(passwdInput);
    var hasUpperCase = regExHasUpperCase.test(passwdInput);
    var hasLowerCase = regExHasLowerCase.test(passwdInput);
    var hasSpecialSign = regExHasSpecialSign.test(passwdInput);
    var size = 0;

    if (passwdInput.length === 0) {
        document.querySelector("#registerPasswd").style.borderColor = "rgba(255, 255, 255, 0.08)";
        document.querySelector("#registerPasswd").style.borderColor = "rgba(255, 255, 255, 0.4)";
        return;
    }

    // TODO: Passwortfeld wird rot für schlecht, orange für geht so, grün für geht klar
    if (len >= 5) {
        password = true;
        size += 5;
        document.querySelector("#registerPasswd").style.borderColor = "rgb(255,114,20)";
        if (hasUpperCase && hasLowerCase) {
            size += 10;
            if (hasNumber && hasSpecialSign) {
                size += 10;
                document.querySelector("#registerPasswd").style.borderColor = "rgb(255,231,75)";
                if (len > 7) {
                    size += 5;
                    document.querySelector("#registerPasswd").style.borderColor = "rgb(149,255,128)";
                }
            }
        }
    } else {
        password = false;
        document.querySelector("#registerPasswd").style.borderColor = "rgb(255,51,51)";
    }
    checkIfAllOk();
};

function setColor(field, correct) {
    if (correct == true) {
        registerFields.get(field).style.borderColor = "rgb(149,255,128)";
    } else {
        registerFields.get(field).style.borderColor = "rgb(255,51,51)";
    }
}

function collectRegisterFields() {
    registerFields.set('vname', document.getElementById("vname"));
    registerFields.set('nname', document.getElementById("nname"));
    registerFields.set('street', document.getElementById("street"));
    registerFields.set('housenumber', document.getElementById("housenumber"));
    registerFields.set('postcode', document.getElementById("postcode"));
    registerFields.set('city', document.getElementById("city"));
    registerFields.set('email', document.getElementById("email"));
    registerFields.set('telphone', document.getElementById("telphone"));
    registerFields.set('userOwnName', document.getElementById("userOwnName"));
    registerFields.set('registerPasswd', document.getElementById("registerPasswd"));
}

function setAllBoolsFalse(){
    vName = false;
    nName = false;
    street = false;
    houseNumber = false;
    postalCode = false;
    city = false;
    email = false;
    telphone = false;
    userLoginName = false;
    password = false;
}