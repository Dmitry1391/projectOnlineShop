let numberClientView;

let CLIENT_VIEW_SERVICE_RESPONSE_TYPE;
let GET_ORDER_BY_ID_RESPONSE = 1;
let GET_ALL_ORDERS_RESPONSE = 2;
let GET_ORDER_BY_STATUS_RESPONSE = 3;
let GET_ALL_CLIENTS_RESPONSE = 4;
let GET_ALL_CLIENT_IN_BLACK_LIST_RESPONSE = 5;
let SET_BLACK_LIST_CLIENT_RESPONSE = 6;
let DELETE_CLIENT_IN_DB_RESPONSE = 7;

function getOrderById(command, id) {

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 1;

    let commandQuery = command;
    let queryName = id;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

function getAllOrders() {

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 2;

    let commandQuery = "getAllOrders";
    let queryName = "getAllOrders";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

function getOrderByStatus(status) {

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 3;

    let commandQuery = "getAllByOrderStatus";
    let queryName = status;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

function getAllClients() {

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 4;

    let commandQuery = "getAllClients";
    let queryName = "getAllClients";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

function getAllClientInBL() {

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 5;

    let commandQuery = "getAllClientsInBList";
    let queryName = "getAllClientsInBList";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

// Set Black List client
function setBList(oButton) {

    var clientIdInTable = oButton.getAttribute('clientId');
    var blackListInTable = oButton.getAttribute('blackList');

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 6;

    let commandQuery = blackListInTable;
    let queryName = clientIdInTable;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('POST', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

// Delete client in DB
function deleteClientInDB(oButton) {

    var clientIdInTable = oButton.getAttribute('clientLogin');

    CLIENT_VIEW_SERVICE_RESPONSE_TYPE = 7;

    let commandQuery = "deleteByLogin";
    let queryName = clientIdInTable;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('POST', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClientView;
}

//Handling response from servlet
function responseFunctionForClientView() {
    switch (CLIENT_VIEW_SERVICE_RESPONSE_TYPE) {
        case GET_ORDER_BY_ID_RESPONSE: {
            handleGetByIdResponse();
            break;
        }
        case GET_ALL_ORDERS_RESPONSE: {
            handleGetAllOrdersResponse();
            break;
        }
        case GET_ORDER_BY_STATUS_RESPONSE: {
            handleGetOrderByStatusResponse();
            break;
        }
        case GET_ALL_CLIENTS_RESPONSE: {
            handleGetAllClientsResponse();
            break;
        }
        case GET_ALL_CLIENT_IN_BLACK_LIST_RESPONSE: {
            handleGetAllClientsInBlackListResponse();
            break;
        }
        case SET_BLACK_LIST_CLIENT_RESPONSE: {
            handleSetClientBlackListResponse();
            break;
        }
        case DELETE_CLIENT_IN_DB_RESPONSE: {
            handleDeleteClientInDBResponse();
            break;
        }
        default: {
            alert("Something went wrong...!!!");
            location.reload();
            break;
        }
    }
}

function handleGetByIdResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTOrders');
                clearRowsFromTable('contentTOrders');
                createTableOrdersOnViewClient(array, table);
                break;
            }
            case 404: {
                alert("Order not found!");
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

function handleGetAllOrdersResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTOrders');
                clearRowsFromTable('contentTOrders');
                createTableOrdersOnViewClient(array, table);
                break;
            }
            case 404: {
                alert("Orders not found!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleGetOrderByStatusResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTOrders');
                clearRowsFromTable('contentTOrders');
                createTableOrdersOnViewClient(array, table);
                break;
            }
            case 404: {
                alert("Order by status not found!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleGetAllClientsResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTClients');
                clearRowsFromTable('contentTClients');
                createTableClientsOnViewClient(array, table);
                break;
            }
            case 404: {
                alert("ALL clients not found");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleGetAllClientsInBlackListResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTClients');
                clearRowsFromTable('contentTClients');
                createTableClientsOnViewClient(array, table);
                break;
            }
            case 404: {
                alert("All clients in black list not found");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleSetClientBlackListResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Black list set successful!");
                getAllClientInBL();
                break;
            }
            case 404: {
                alert("Unable to set black list!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleDeleteClientInDBResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Client successful deleted!");
                location.reload();
                break;
            }
            case 404: {
                alert("Unable to delete client!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

// Func create table orders
function createTableOrdersOnViewClient(array, table) {
    for (let i = 0; i < array.length; i++) {

        let row = table.insertRow(table.rows.length);

        let colClientID = row.insertCell(0);
        let colOrderID = row.insertCell(1);
        let colOrderStatus = row.insertCell(2);
        let colOrderCost = row.insertCell(3);

        let clientId = document.createTextNode(array[i].clientId);
        let oderId = document.createTextNode(array[i].orderId);
        let orderStatus = document.createTextNode(array[i].orderStatus);
        let orderCost = document.createTextNode(array[i].totalCost);

        colClientID.appendChild(clientId);
        colOrderID.appendChild(oderId);
        colOrderStatus.appendChild(orderStatus);
        colOrderCost.appendChild(orderCost);

        var button = document.createElement('input');
        button.setAttribute('type', 'button');
        button.setAttribute('value', 'In B.List');
        button.setAttribute('onclick', 'setBList(this)');
        button.setAttribute('clientId', array[i].clientId);
        button.setAttribute('blackList', 'setClientInBList');
        var colIN = row.insertCell(4);
        colIN.appendChild(button);
    }
}

// Func create table clients
function createTableClientsOnViewClient(array, table) {
    for (let i = 0; i < array.length; i++) {

        let row = table.insertRow(table.rows.length);

        let colClientID = row.insertCell(0);
        let colClientName = row.insertCell(1);
        let colClientLogin = row.insertCell(2);
        let colClientPassword = row.insertCell(3);
        let colClientBList = row.insertCell(4);

        let clientId = document.createTextNode(array[i].clientId);
        let clientName = document.createTextNode(array[i].clientName);
        let clientLogin = document.createTextNode(array[i].clientLogin);
        let clientPass = document.createTextNode(array[i].clientPass);
        let clientBList = document.createTextNode(array[i].clientBList);

        colClientID.appendChild(clientId);
        colClientName.appendChild(clientName);
        colClientLogin.appendChild(clientLogin);
        colClientPassword.appendChild(clientPass);
        colClientBList.appendChild(clientBList);

        var buttonOutBL = document.createElement('input');
        buttonOutBL.setAttribute('type', 'button');
        buttonOutBL.setAttribute('value', 'Out B.List');
        buttonOutBL.setAttribute('onclick', 'setBList(this)');
        buttonOutBL.setAttribute('clientId', array[i].clientId);
        buttonOutBL.setAttribute('blackList', 'setClientOutBList');
        var colOutBL = row.insertCell(5);
        colOutBL.appendChild(buttonOutBL);

        var buttonDeleteClient = document.createElement('input');
        buttonDeleteClient.setAttribute('type', 'button');
        buttonDeleteClient.setAttribute('value', 'Delete');
        buttonDeleteClient.setAttribute('onclick', 'deleteClientInDB(this)');
        buttonDeleteClient.setAttribute('clientLogin', array[i].clientLogin);
        var colDelClient = row.insertCell(6);
        colDelClient.appendChild(buttonDeleteClient);
    }
}