function isEmpty(str) {
    return (str == null) || (str.length === 0);
}

//Validation of input fields
function isValidateFieldLogin() {

    let queryLogin = document.formLogin.findLogin.value;
    let queryPass = document.formLogin.findPass.value;
    let messageLog = "";
    let hasNoErrorLog = true;

    if (isEmpty(queryLogin) && isEmpty(queryPass)) {
        messageLog = messageLog + "You have not entered anything, fill in all the fields, please!" + "\n";

        document.formLogin.findLogin.style.background = "red";
        document.formLogin.findPass.style.background = "red";

        hasNoErrorLog = false;
    } else {

        if (isEmpty(queryLogin)) {
            messageLog = messageLog + "Enter login!" + "\n";
            hasNoErrorLog = false;
        } else {
            document.formLogin.findLogin.style.backgroundColor = "#369";
        }

        if (isEmpty(queryPass)) {
            messageLog = messageLog + "Enter password!" + "\n";
            hasNoErrorLog = false;
        } else {
            document.formLogin.findPass.style.backgroundColor = "#369";
        }

        if (!isEmpty(queryPass) && queryPass.length < 8) {
            messageLog = messageLog + "Password incorrect!" + "\n" + "(Password at least 8 characters!)" + "\n";
            hasNoErrorLog = false;
        } else {
            document.formLogin.findPass.style.backgroundColor = "#369";
        }
    }

    if (hasNoErrorLog) {
        getByLoginAndPass(queryLogin,queryPass);
        document.getElementById("dataUser").hidden=false;
        document.getElementById("btnExit").hidden=false;
        document.formLogin.hidden=true;
        document.getElementById("divTop").hidden=true;
    } else {
        document.getElementById("divTop").innerText = messageLog;
    }
}