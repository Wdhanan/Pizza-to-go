"using strict";

async function login(username, password) {
    let userData = {
        username: username,
        password: password
    };
    return await fetch('/data/access', {
        method: 'post',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => response.json())
        .then(data => {
            return data.token;
        })
        .catch((error) => {
            console.error('Error:', error);
            sessionStorage.removeItem('loginToken');
            sessionStorage.removeItem('username');
            sessionStorage.removeItem("user");
            sessionStorage.removeItem("cart");
            alert("Anmeldedaten sind fehlerhaft!");
            clearLoginField();
        });

}

async function getAllUserInformations() {
    await fetch('data/user/' + '?token=' + sessionStorage.getItem('loginToken'), {
        method: 'get'
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let user = {
                'email': data.email,
                'telefon': data.telefon,
                'street': data.street,
                'streetNumber': data.streetNumber,
                'zip': data.zip,
                'city': data.city,
                'firstname': data.firstname,
                'lastname': data.lastname
            };
            sessionStorage.setItem("user", JSON.stringify(user));
        })
        .catch(error => console.error('Error:', error));

}

function clearLoginField() {
    document.querySelector("#login-username").value = "";
    document.querySelector("#login-password").value = "";
}

async function logout() {
    await fetch('data/access' + "?token=" + sessionStorage.getItem('loginToken'), {
        method: 'delete'
    })
        .then(response => {
            if (response.ok) {
                sessionStorage.removeItem('loginToken');
                sessionStorage.removeItem('username');
                sessionStorage.removeItem("user");
                sessionStorage.removeItem("cart");

            }

        })
        .catch((error) => {
            console.error('Error:', error);
            sessionStorage.removeItem('loginToken');
            sessionStorage.removeItem('username');
            sessionStorage.removeItem("user");
            sessionStorage.removeItem("cart");
        });
}

async function register(username, password, firstname, lastname, email, telefon, street, streetNumber, zip, city) {
    let person = {
        username: username,
        password: password,
        firstname: firstname,
        lastname: lastname,
        email: email,
        telefon: telefon,
        street: street,
        streetNumber: streetNumber,
        zip: zip,
        city: city,
    }

    return await fetch('data/user', {
        method: 'post',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(person)
    })
        .then(response => {
            if (!response.ok) {
                throw Error(response.statusText);
            }
            return response.json();
        })
        .then(data => {
            return data.token;
        })
        .catch((error) => {
            console.error('Error:', error);
            document.getElementById("userOwnName").value = "";
            document.getElementById("registerPasswd").value = "";
            password = false;
            checkIfAllOk();
            alert("Es ist ein Fehler aufgetreten!");
        });
}

async function checkIsAvailable(username) {
    return await fetch('data/user/isAvailable?username=' + username, {
        method: 'get',
        headers: {
            'Content-type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Username bereits vergeben");
        });
}

const checkAccess = async function (){
    return await fetch('data/access/checkuser', {
        method: 'get',
        headers: {
            'Content-type': 'application/json',
            'loginToken': sessionStorage.getItem('loginToken')
        },
    })
        .then(response => response.json())
        .then(async data => {

                return data

        })
        .catch(async (error) => {
            console.error('Error:', error);
            await logoutUser();
            alert("Sitzung abgelaufen");
        });
}





