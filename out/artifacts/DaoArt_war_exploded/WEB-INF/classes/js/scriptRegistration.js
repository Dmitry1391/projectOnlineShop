//Validation of input fields
function isValidateFieldRegister() {
    let valueRegName = document.formRegistration.findRegName.value;
    let valueRegLogin = document.formRegistration.findRegLogin.value;
    let valueRegPass = document.formRegistration.findRegPass.value;
    let message = "";
    let hasNoError = true;

    if (isEmpty(valueRegName) && isEmpty(valueRegLogin) && isEmpty(valueRegPass)) {
        message = message + "You have not entered anything, fill in all the fields, please!" + "\n";

        document.formRegistration.findRegName.style.background = "red";
        document.formRegistration.findRegLogin.style.background = "red";
        document.formRegistration.findRegPass.style.background = "red";

        hasNoError = false;
    } else {

        if (isEmpty(valueRegName)) {
            document.formRegistration.findRegName.style.background = "red";
            message = message + "Enter name!" + "\n";
            hasNoError = false;
        } else {
            document.formRegistration.findRegName.style.backgroundColor = "#369";
        }
        if (isEmpty(valueRegLogin)) {
            document.formRegistration.findRegLogin.style.background = "red";
            message = message + "Enter login!" + "\n";
            hasNoError = false;
        } else {
            document.formRegistration.findRegLogin.style.background = "#369";
        }
        if (!isEmpty(valueRegLogin) && !validateLogin(valueRegLogin)) {
            document.formRegistration.findRegLogin.style.background = "red";
            message = message + "Incorrect login!" + "\n";
            hasNoError = false;
        } else {
            document.formRegistration.findRegLogin.style.background = "#369";
        }
        if (isEmpty(valueRegPass)) {
            document.formRegistration.findRegPass.style.background = "red";
            message = message + "Enter password!" + "\n";
            hasNoError = false;
        } else {
            document.formRegistration.findRegPass.style.background = "#369";
        }
        if (!isEmpty(valueRegPass) && valueRegPass.length < 8) {
            document.formRegistration.findRegPass.style.background = "red";
            message = message + "Password incorrect!" + "\n" + "(Password at least 8 characters!)" + "\n";
            hasNoError = false;
        } else {
            document.formRegistration.findRegPass.style.background = "#369";
        }
    }

    if (hasNoError) {
        document.formRegistration.findRegName.style.background = "#369";
        document.formRegistration.findRegLogin.style.background = "#369";
        document.formRegistration.findRegPass.style.background = "#369";
        document.getElementById("divTop").style.display = "none";
        register();
    } else {
        document.getElementById("divTop").style.display = "";
        document.getElementById("divTop").innerText = message;
    }
}

function validateLogin(login) {
    if (/^[a-zA-Z1-9]+$/.test(login) === false) {
        return false;
    }
    return !(login.length < 4 || login.length > 20);
}

function register() {

    document.getElementById("divTop").style.display = "none";
    document.getElementById("divToLoginForm").style.display = "none";
    document.getElementById("divToRegForm").style.display = "none";

    let commandQuery = "createClient";
    let queryRegName = document.formRegistration.findRegName.value;
    let queryRegLogin = document.formRegistration.findRegLogin.value;
    let queryRegPass = document.formRegistration.findRegPass.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    let params = "commandQuery=" + commandQuery + "&" + "queryRegName=" + queryRegName + "&" + "queryRegLogin=" + queryRegLogin + "&" + "queryRegPass=" + queryRegPass;

    httpRequest.open('GET', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = dataRegFilling;
}

function dataRegFilling() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("You are successfully registered" + "\n" +"Please enter your username and password!");
                location.href="/views/htmlPages/logPage.html";
                break;
            }
            case 404: {
                alert("This user is already registered!" + "\n" + "Please come up with a new username and password!" + "\n" + "(Password at least 8 characters!)" + "\n");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!!!");
                location.reload();
                break;
            }
        }
    }
}