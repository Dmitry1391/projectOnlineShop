var cart = {}; // моя корзина
let httpRequest;

let CLIENT_SERVICE_RESPONSE_TYPE;
let DELETE_CLIENT_ORDER_RESPONSE = 1;
let RENAME_CLIENT_NAME_RESPONSE = 2;
let DELETE_CLIENT_BY_LOGIN_RESPONSE = 3;
let GET_ALL_ORDERS_FROM_CLIENT_RESPONSE = 4;
let UPDATE_ORDER_BY_ID_RESPONSE = 5;
let GET_ALL_PRODUCT_FOR_CLIENT_RESPONSE = 6;
let GET_ORDER_DETAILS_BY_ORDER_ID_RESPONSE = 7;

function deleteClientOrder(orderIdInTable) {

    CLIENT_SERVICE_RESPONSE_TYPE = 1;

    let commandQuery = "deleteOrder";
    let queryName = orderIdInTable;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function renameClientName() {

    CLIENT_SERVICE_RESPONSE_TYPE = 2;

    let commandQuery = "renameClientName";
    let queryName = document.getElementById('clientDataUser').value;
    let queryNewName = document.formDataClient.queryClientNewName.value;

    identificationDataClient['name'] = queryNewName;
    localStorage.setItem('identificationDataClient', JSON.stringify(identificationDataClient));

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryNewName=" + queryNewName;
    httpRequest.open('GET', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}


function deleteClientByLogin() {

    CLIENT_SERVICE_RESPONSE_TYPE = 3;

    let commandQuery = "deleteByLogin";
    let queryName = document.getElementById('clientDataUser').value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/ClientController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function getAllOrdersFromClient() {

    CLIENT_SERVICE_RESPONSE_TYPE = 4;

    let commandQuery = "getAllOrdersByClient";
    let queryName = document.getElementById('clientDataUser').value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function updateOrderByID(orderIdInTable) {

    CLIENT_SERVICE_RESPONSE_TYPE = 5;

    let commandQuery = "updateOrderByID";
    let queryName = orderIdInTable;
    let queryNewStatus = "paid";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryNewStatus=" + queryNewStatus;
    httpRequest.open('GET', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function getAllProductsForClient() {

    CLIENT_SERVICE_RESPONSE_TYPE = 6;

    let commandQuery = "getAllProducts";
    let queryName = "getAllProducts";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('POST', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function getOrderDetailsByOrderId(orderIdInTable) {

    document.getElementById("divShowMyOrders").hidden = true;
    document.getElementById("divShowProducts").hidden = true;
    document.getElementById("divOrderDetailsForClient").hidden = false;

    CLIENT_SERVICE_RESPONSE_TYPE = 7;

    let commandQuery = "getOrderDetailsByOrderId";
    let queryName = orderIdInTable;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/OrderDetailsController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForClient;
}

function responseFunctionForClient() {
    switch (CLIENT_SERVICE_RESPONSE_TYPE) {
        case DELETE_CLIENT_ORDER_RESPONSE: {
            handleDeleteClientOrderResponse();
            break;
        }
        case RENAME_CLIENT_NAME_RESPONSE: {
            handleRenameClientNameResponse();
            break;
        }
        case DELETE_CLIENT_BY_LOGIN_RESPONSE: {
            handleDeleteClientByLoginResponse();
            break;
        }
        case GET_ALL_ORDERS_FROM_CLIENT_RESPONSE: {
            handleGetAllOrdersFromClientResponse();
            break;
        }
        case UPDATE_ORDER_BY_ID_RESPONSE: {
            handleUpdateOrderByIDResponse();
            break;
        }
        case GET_ALL_PRODUCT_FOR_CLIENT_RESPONSE: {
            handleGetAllProductsForClientResponse();
            break;
        }
        case GET_ORDER_DETAILS_BY_ORDER_ID_RESPONSE: {
            handleGetOrderDetailsByOrderIdResponse();
            break;
        }
        default: {
            alert("Something went wrong...!!!");
            location.reload();
            break;
        }
    }
}

function handleDeleteClientOrderResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Order successful delete!");
                showOrdersForClient();
                break;
            }
            case 404: {
                alert("Unable to delete order!");
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

function handleRenameClientNameResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Name successful update");
                subMenuSettingsMe();
                break;
            }
            case 404: {
                alert("Unable to update name!");
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

function handleDeleteClientByLoginResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                // Удаляем данные пользователя и корзины
                localStorage.clear();
                alert("Client successful deleted");
                location.href = "/views/htmlPages/logPage.html";
                break;
            }
            case 404: {
                alert("Unable to delete by login!");
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

function handleGetAllOrdersFromClientResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {

                let array = httpRequest.response;
                let table = document.getElementById('contentOrderTableForClient');

                clearRowsFromTable('contentOrderTableForClient');

                for (let j = 0; j < array.length; j++) {

                    var row = table.insertRow(table.rows.length);
                    var colID = row.insertCell(0);
                    var colStatus = row.insertCell(1);
                    var colCost = row.insertCell(2);

                    var orderId = document.createTextNode(array[j].orderId);
                    var orderStatus = document.createTextNode(array[j].orderStatus);
                    var totalCost = document.createTextNode(array[j].totalCost);

                    colID.appendChild(orderId);
                    colStatus.appendChild(orderStatus);
                    colCost.appendChild(totalCost);

                    var colSetStatus = row.insertCell(3);
                    var buttonSetStatus = document.createElement('input');
                    buttonSetStatus.setAttribute('type', 'button');
                    buttonSetStatus.setAttribute('value', 'To pay');
                    buttonSetStatus.setAttribute('onclick', 'toPayOrder(this)');
                    buttonSetStatus.setAttribute('orderId', array[j].orderId);
                    colSetStatus.appendChild(buttonSetStatus);

                    var getOrderDetails = row.insertCell(4);
                    var buttonGetOrderDetails = document.createElement('input');
                    buttonGetOrderDetails.setAttribute('type', 'button');
                    buttonGetOrderDetails.setAttribute('value', 'Order details');
                    buttonGetOrderDetails.setAttribute('onclick', 'getOrderDetails(this)');
                    buttonGetOrderDetails.setAttribute('orderId', array[j].orderId);
                    getOrderDetails.appendChild(buttonGetOrderDetails);

                    var deleteOrder = row.insertCell(5);
                    var buttonDeleteOrder = document.createElement('input');
                    buttonDeleteOrder.setAttribute('type', 'button');
                    buttonDeleteOrder.setAttribute('value', 'Delete');
                    buttonDeleteOrder.setAttribute('onclick', 'deleteOrder(this)');
                    buttonDeleteOrder.setAttribute('orderId', array[j].orderId);
                    deleteOrder.appendChild(buttonDeleteOrder);
                }
                break;
            }
            case 404: {
                alert("You have no orders!");
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

function handleUpdateOrderByIDResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Order successful update");
                showOrdersForClient();
                break;
            }
            case 404: {
                alert("Unable to update order!");
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

function handleGetAllProductsForClientResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {

                let array = httpRequest.response;
                let table = document.getElementById('contentProductTableForClient');

                clearRowsFromTable('contentProductTableForClient');

                for (let j = 0; j < array.length; j++) {

                    var row = table.insertRow(table.rows.length);
                    var colID = row.insertCell(0);
                    var colName = row.insertCell(1);
                    var colPrice = row.insertCell(2);

                    var productId = document.createTextNode(array[j].id);
                    var productName = document.createTextNode(array[j].name);
                    var productPrice = document.createTextNode(array[j].price);

                    colID.appendChild(productId);
                    colName.appendChild(productName);
                    colPrice.appendChild(productPrice);

                    var button = document.createElement('input');
                    button.setAttribute('type', 'button');
                    button.setAttribute('value', 'In cart');
                    button.setAttribute('onclick', 'addToCart(this)');
                    button.setAttribute('productId', array[j].id);
                    var colIN = row.insertCell(3);
                    colIN.appendChild(button);
                }
                break;
            }
            case 404: {
                alert("No product!");
                location.reload();
                break;
            }
        }
    }
}

function handleGetOrderDetailsByOrderIdResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentOrderDetailsTableForClient');

                clearRowsFromTable('contentOrderDetailsTableForClient');

                for (let i = 0; i < array.length; i++) {

                    document.getElementById('clientOrderNumber').innerText = array[i].orderId;

                    var prodId = array[i].productId;
                    var prodName = array[i].productName;
                    var prodCount = array[i].productCount;
                    var prodCost = array[i].productCost.toFixed(2);

                    var row = table.insertRow(table.rows.length);

                    var colProductID = row.insertCell(0);
                    var colProductName = row.insertCell(1);
                    var colProductCount = row.insertCell(2);
                    var colProductCost = row.insertCell(3);

                    var productIDText = document.createTextNode(prodId);
                    var productNameText = document.createTextNode(prodName);
                    var productCountText = document.createTextNode(prodCount);
                    var productCostText = document.createTextNode(prodCost);

                    colProductID.appendChild(productIDText);
                    colProductName.appendChild(productNameText);
                    colProductCount.appendChild(productCountText);
                    colProductCost.appendChild(productCostText);
                }
                break;
            }
            case 404: {
                alert("No products!");
                location.reload();
                break;
            }
        }
    }
}

// Работа с ордерами___
function toPayOrder(oButton) {
    var orderIdInTable = oButton.getAttribute('orderId');
    updateOrderByID(orderIdInTable);
}

function getOrderDetails(oButton) {
    var orderIdInTable = oButton.getAttribute('orderId');
    getOrderDetailsByOrderId(orderIdInTable);
}

function deleteOrder(oButton) {
    var orderIdInTable = oButton.getAttribute('orderId');
    deleteClientOrder(orderIdInTable);
}

//___

// работа с корзиной___
function addToCart(btnToCart) {

    var productIdInTable = btnToCart.getAttribute('productId');

    if (cart[productIdInTable] !== undefined) {
        cart[productIdInTable]++;
    } else {
        cart[productIdInTable] = 1;
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    console.log(cart);
    showMiniCart();
}

function checkCart() {

    //проверяю наличие корзины в localStorage;
    if (localStorage.getItem('cart') != null) {
        cart = JSON.parse(localStorage.getItem('cart'));
    }
}

function showMiniCart() {

    //показываю содержимое корзины
    var countProduct = 0;
    for (var elementCart in cart) {
        countProduct += cart[elementCart];
    }
    document.getElementById("countProductInCart").innerText = countProduct;
}

//___

function subMenuSettingsMe() {
    getDataClient();
    document.getElementById("divShowMyOrders").hidden = true;
    document.getElementById("divShowProducts").hidden = true;
    document.getElementById("divOrderDetailsForClient").hidden = true;
    document.getElementById("settingForClient").hidden = false;
}

function showProductsForClient() {
    document.getElementById("settingForClient").hidden = true;
    document.getElementById("divShowMyOrders").hidden = true;
    document.getElementById("divOrderDetailsForClient").hidden = true;
    document.getElementById("divShowProducts").hidden = false;
    getAllProductsForClient();
}

function showOrdersForClient() {
    document.getElementById("settingForClient").hidden = true;
    document.getElementById("divShowProducts").hidden = true;
    document.getElementById("divOrderDetailsForClient").hidden = true;
    document.getElementById("divShowMyOrders").hidden = false;
    getAllOrdersFromClient();
}

function goToCart() {
    location.href = "/views/htmlPages/cart.html";
}

function exitLocation() {
    localStorage.removeItem('identificationDataClient');
    location.href = "/views/htmlPages/logPage.html";
}

function getDataClient() {

    if (localStorage.getItem('identificationDataClient') != null) {
        identificationDataClient = JSON.parse(localStorage.getItem('identificationDataClient'));

        document.getElementById("putClientName").innerText = identificationDataClient['name'];
        document.getElementById("putClientLogin").innerText = identificationDataClient['login'];
        document.getElementById("putClientPass").innerText = identificationDataClient['pass'];

        if (identificationDataClient['blackList']) {
            document.getElementById("putClientBList").innerText = "YES";
        } else {
            document.getElementById("putClientBList").innerText = "NO";
        }

    } else {
        alert("Something went wrong...!!!");
        location.href = "/views/htmlPages/logPage.html";
    }
}