let ADMIN_SERVICE_RESPONSE_TYPE;
let GET_BY_ID_RESPONSE = 1;
let CREATE_ADMIN_RESPONSE = 2;
let GET_BY_LOGIN_RESPONSE = 3;
let GET_BY_LOGIN_AND_PASS_RESPONSE = 4;
let DELETE_BY_LOGIN_RESPONSE = 5;
let GET_ALL_ADMINS_RESPONSE = 6;
let RENAME_ADMIN_NAME_RESPONSE = 7;
let identificationDataAdmin = {};
let identificationDataClient = {};

let httpRequest;

function getById() {

    ADMIN_SERVICE_RESPONSE_TYPE = 1;

    let commandQuery = "getById";
    let queryName = document.formAdminID.findRequestQueryID.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

function createAdmin() {

    ADMIN_SERVICE_RESPONSE_TYPE = 2;

    let commandQuery = "createAdmin";
    let queryAdminName = document.formAdminCreate.createRequestAdminName.value;
    let queryAdminLogin = document.formAdminCreate.createRequestAdminLogin.value;
    let queryAdminPass = document.formAdminCreate.createRequestAdminPassword.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryAdminName=" + queryAdminName + "&" + "queryAdminLogin=" + queryAdminLogin + "&" + "queryAdminPass=" + queryAdminPass;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

function getByLogin() {

    ADMIN_SERVICE_RESPONSE_TYPE = 3;

    let commandQuery = "getByLogin";
    let queryName = document.formAdminLogin.findRequestQueryLogin.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

function getByLoginAndPass(login, pass) {

    ADMIN_SERVICE_RESPONSE_TYPE = 4;

    let commandQuery = "getByLoginAndPass";
    let queryName = login;
    let queryPass = pass;
    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryPass=" + queryPass;

    if (localStorage.getItem('identificationDataAdmin') != null) {
        identificationDataAdmin = JSON.parse(localStorage.getItem('identificationDataAdmin'));

        if ((identificationDataAdmin['login'] === queryName) && (identificationDataAdmin['pass'] === queryPass)) {
            location.href = "/views/htmlPages/adminPage.html";
        } else {
            sendRequest();
        }
    } else if (localStorage.getItem('identificationDataClient') != null) {
        identificationDataClient = JSON.parse(localStorage.getItem('identificationDataClient'));

        if ((identificationDataClient['login'] === queryName) && (identificationDataClient['pass'] === queryPass)) {
            location.href = "/views/htmlPages/clientPage.html";
        } else {
            sendRequest();
        }
    } else {
        sendRequest();
    }

    //Sending request to servlet
    function sendRequest() {
        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }

        httpRequest.open('GET', '/AdminController' + "?" + params, true);
        httpRequest.responseType = 'json';
        httpRequest.send();
        httpRequest.onreadystatechange = responseFunctionForAdmin;
    }
}

function deleteByLogin(login) {

    ADMIN_SERVICE_RESPONSE_TYPE = 5;

    let commandQuery = "deleteByLogin";
    let queryName = login;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

function getAllAdmins() {

    ADMIN_SERVICE_RESPONSE_TYPE = 6;

    let commandQuery = "getAllAdmins";
    let queryName = "getAllAdmins";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

function renameAdminName() {

    ADMIN_SERVICE_RESPONSE_TYPE = 7;

    let commandQuery = "renameAdminName";
    let queryName = document.getElementById('adminDataUser').value;
    let queryNewName = document.formDataAdmin.queryAdminNewName.value;

    identificationDataAdmin['name'] = queryNewName;
    localStorage.setItem('identificationDataAdmin', JSON.stringify(identificationDataAdmin));

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryNewName=" + queryNewName;
    httpRequest.open('GET', '/AdminController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForAdmin;
}

//Handling response from servlet
function responseFunctionForAdmin() {
    switch (ADMIN_SERVICE_RESPONSE_TYPE) {
        case GET_BY_ID_RESPONSE: {
            handleGetByIdResponse();
            break;
        }
        case CREATE_ADMIN_RESPONSE: {
            handleCreateAdminResponse();
            break;
        }
        case GET_BY_LOGIN_RESPONSE: {
            handleGetByLoginResponse();
            break;
        }
        case GET_BY_LOGIN_AND_PASS_RESPONSE: {
            handleGetByLoginAndPassResponse();
            break;
        }
        case DELETE_BY_LOGIN_RESPONSE: {
            handleDeleteByLoginResponse();
            break;
        }
        case GET_ALL_ADMINS_RESPONSE: {
            handleGetAllAdminsResponse();
            break;
        }
        case RENAME_ADMIN_NAME_RESPONSE: {
            handleRenameNameAdminResponse();
            break;
        }
        default: {
            alert("Something went wrong...!!!");
            location.href = "/views/htmlPages/logPage.html";
            break;
        }
    }
}

function handleGetByIdResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTable');
                clearRowsFromTable('contentTable');
                createTableOnViewAdmin(array, table);
                break;
            }
            case 404: {
                alert("Admin with this ID not found!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.reload();
                break;
            }
        }
    }
}

function handleCreateAdminResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Create Admin Successful!");
                getAllAdmins();
                break;
            }
            case 404: {
                alert("Unable to create admin!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.reload();
                break;
            }
        }
    }
}

function handleGetByLoginResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTable');
                clearRowsFromTable('contentTable');
                createTableOnViewAdmin(array, table);
                break;
            }
            case 404: {
                alert("Admin with this login not found!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.reload();
                break;
            }
        }
    }
}

function handleGetByLoginAndPassResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {

            //Response for admin
            case 200: {
                let array = httpRequest.response;
                for (let i = 0; i < array.length; i++) {

                    let id = array[i].id;
                    let name = array[i].name;
                    let pass = array[i].pass;
                    let login = array[i].login;

                    identificationDataAdmin['id'] = id;
                    identificationDataAdmin['name'] = name;
                    identificationDataAdmin['login'] = login;
                    identificationDataAdmin['pass'] = pass;

                    localStorage.setItem('identificationDataAdmin', JSON.stringify(identificationDataAdmin));
                    location.href = "/views/htmlPages/adminPage.html";
                }
                break;
            }

            //Response for client
            case 201: {
                let array = httpRequest.response;
                for (let i = 0; i < array.length; i++) {

                    let clientId = array[i].clientId;
                    let clientName = array[i].clientName;
                    let clientLogin = array[i].clientLogin;
                    let clientPass = array[i].clientPass;
                    let clientBList = array[i].clientBList;

                    identificationDataClient['id'] = clientId;
                    identificationDataClient['name'] = clientName;
                    identificationDataClient['login'] = clientLogin;
                    identificationDataClient['pass'] = clientPass;
                    identificationDataClient['blackList'] = clientBList;

                    localStorage.setItem('identificationDataClient', JSON.stringify(identificationDataClient));
                    location.href = "/views/htmlPages/clientPage.html";
                }
                break;
            }
            case 404: {
                alert("User with this login and password not found!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.href = "/views/htmlPages/logPage.html";
                break;
            }
        }
    }
}

function  handleDeleteByLoginResponse(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                localStorage.removeItem('identificationDataAdmin');
                alert("Admin successful deleted");
                location.href = "/views/htmlPages/logPage.html";
                break;
            }
            case 404: {
                alert("Unable to delete admin!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.href = "/views/htmlPages/logPage.html";
                break;
            }
        }
    }
}

function  handleGetAllAdminsResponse(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTable');

                clearRowsFromTable('contentTable');
                createTableOnViewAdmin(array, table);

                break;
            }
            case 404: {
                alert("Admins not Found!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.reload();
                break;
            }
        }
    }
}

function handleRenameNameAdminResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Name successful update");
                location.reload();
                break;
            }
            case 404: {
                alert("Admin name has not changed!");
                location.reload();
                break;
            }
            default: {
                alert("Something went wrong...!");
                location.reload();
                break;
            }
        }
    }
}

//Func get data admin in localStorage
function getDataAdmin() {

    if (localStorage.getItem('identificationDataAdmin') != null) {
        identificationDataAdmin = JSON.parse(localStorage.getItem('identificationDataAdmin'));

        document.getElementById("putAdminId").innerText = identificationDataAdmin['id'];
        document.getElementById("putAdminName").innerText = identificationDataAdmin['name'];
        document.getElementById("putAdminLogin").innerText = identificationDataAdmin['login'];
        document.getElementById("putAdminPass").innerText = identificationDataAdmin['pass'];

    } else {
        alert("Something went wrong..!!");
        location.href = "/views/htmlPages/logPage.html";
    }
}

// func create table
function createTableOnViewAdmin(array, table) {
    for (let i = 0; i < array.length; i++) {

        let row = table.insertRow(table.rows.length);

        let colID = row.insertCell(0);
        let colName = row.insertCell(1);
        let colLogin = row.insertCell(2);

        let adminId = document.createTextNode(array[i].id);
        let adminName = document.createTextNode(array[i].name);
        let adminLogin = document.createTextNode(array[i].login);

        colID.appendChild(adminId);
        colName.appendChild(adminName);
        colLogin.appendChild(adminLogin);
    }
}