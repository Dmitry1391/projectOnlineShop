let PRODUCT_SERVICE_RESPONSE_TYPE;
let GET_PRODUCT_BY_ID_RESPONSE = 1;
let GET_ALL_PRODUCTS_RESPONSE = 2;
let CREATE_PRODUCT_RESPONSE = 3;
let UPDATE_PRODUCT_RESPONSE = 4;
let DELETE_PRODUCT_RESPONSE = 5;

function getProductByID() {

    PRODUCT_SERVICE_RESPONSE_TYPE = 1;

    let commandQuery = "getProductByID";
    let queryName = document.formProductById.queryProductById.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForProductView;
}

function getAllProducts() {

    PRODUCT_SERVICE_RESPONSE_TYPE = 2;

    let commandQuery = "getAllProducts";
    let queryName = "getAllProducts";

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName;
    httpRequest.open('GET', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForProductView;
}

function create() {

    PRODUCT_SERVICE_RESPONSE_TYPE = 3;

    let commandQuery = "create";
    let queryName = document.createProduct.queryName.value;
    let queryPrice = document.createProduct.queryPrice.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryPrice=" + queryPrice;
    httpRequest.open('GET', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForProductView;
}

function update() {

    PRODUCT_SERVICE_RESPONSE_TYPE = 4;

    let commandQuery = "updateProduct";
    let queryOldId = document.updateProduct.queryId.value;
    let queryNewName = document.updateProduct.queryNewName.value;
    let queryNewPrice = document.updateProduct.queryNewPrice.value;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryOldId=" + queryOldId + "&" + "queryNewName=" + queryNewName + "&" + "queryNewPrice=" + queryNewPrice;
    httpRequest.open('GET', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForProductView;
}

function deletePr(productIdInTable) {

    PRODUCT_SERVICE_RESPONSE_TYPE = 5;

    let commandQuery = "delete";
    let queryPrId = productIdInTable;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryPrId=" + queryPrId;
    httpRequest.open('GET', '/ProductController' + "?" + params, true);
    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = responseFunctionForProductView;
}

function responseFunctionForProductView() {
    switch (PRODUCT_SERVICE_RESPONSE_TYPE) {
        case GET_PRODUCT_BY_ID_RESPONSE: {
            handleGetProductByIDResponse();
            break;
        }
        case GET_ALL_PRODUCTS_RESPONSE: {
            handleGetAllProductsResponse();
            break;
        }
        case CREATE_PRODUCT_RESPONSE: {
            handleCreateResponse();
            break;
        }
        case UPDATE_PRODUCT_RESPONSE: {
            handleUpdateResponse();
            break;
        }
        case DELETE_PRODUCT_RESPONSE: {
            handleDeleteResponse();
            break;
        }
        default: {
            alert("Something went wrong...!!!");
            break;
        }
    }
}

function handleGetProductByIDResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTable');
                clearRowsFromTable('contentTable');
                createTableOnViewProduct(array, table);
                break;
            }
            case 404: {
                alert("Product by id not found");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleGetAllProductsResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                let array = httpRequest.response;
                let table = document.getElementById('contentTable');
                clearRowsFromTable('contentTable');
                createTableOnViewProduct(array, table);
                break;
            }
            case 404: {
                alert("Unable to get all products!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleCreateResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Product successful create");
                getAllProducts();
                break;
            }
            case 404: {
                alert("Product not create");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleUpdateResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Product successful update");
                getAllProducts();
                break;
            }
            case 404: {
                alert("Product not update!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

function handleDeleteResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Product successful deleted");
                getAllProducts();
                break;
            }
            case 404: {
                alert("Unable to delete product!");
                break;
            }
            default: {
                alert("Something went wrong...!");
                break;
            }
        }
    }
}

// Func create table
function createTableOnViewProduct(array, table) {
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
        button.setAttribute('value', 'Delete in DB');
        button.setAttribute('onclick', 'removeRow(this)');
        button.setAttribute('productId', array[j].id);
        var colIN = row.insertCell(3);
        colIN.appendChild(button);
    }
}

// Func DELETE TABLE ROW
function removeRow(oButton) {
    var productIdInTable = oButton.getAttribute('productId');
    var empTab = document.getElementById('contentTable');
    empTab.deleteRow(oButton.parentNode.parentNode.rowIndex);       // BUTTON -> TD -> TR.
    deletePr(productIdInTable);
}