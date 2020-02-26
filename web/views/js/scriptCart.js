var cart = {}; //глобально моя корзина

var allProducts;

let CART_SERVICE_RESPONSE_TYPE;
let GET_ALL_PRODUCT_IN_CART_RESPONSE = 1;
let CREATE_ORDER_IN_DB_RESPONSE = 2;

function getAllProductsInCart() {

    CART_SERVICE_RESPONSE_TYPE = 1;

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
    httpRequest.onreadystatechange = responseFunctionCartForClient;
}

function createOrderInDB(allCost, logCl) {

    CART_SERVICE_RESPONSE_TYPE = 2;

    let commandQuery = "createOrder";
    let queryName = logCl;
    let queryStatus = "awaiting";
    let queryTotalCost = allCost;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    let params = "commandQuery=" + commandQuery + "&" + "queryName=" + queryName + "&" + "queryStatus=" + queryStatus + "&" + "queryTotalCost=" + queryTotalCost;
    httpRequest.open('POST', '/OrderController' + "?" + params, true);
    httpRequest.responseType = 'json';

    var data = JSON.stringify({cart});
    httpRequest.send(data);
    httpRequest.onreadystatechange = responseFunctionCartForClient;
}

function responseFunctionCartForClient() {
    switch (CART_SERVICE_RESPONSE_TYPE) {
        case GET_ALL_PRODUCT_IN_CART_RESPONSE: {
            handleGetAllProductInCartResponse();
            break;
        }
        case CREATE_ORDER_IN_DB_RESPONSE: {
            handleCreateOrderInDbResponse();
            break;
        }
        default: {
            alert("Something went wrong...!!!");
            location.reload();
            break;
        }
    }
}

function handleGetAllProductInCartResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                // работаю дальше с товарами
                allProducts = httpRequest.response;
                jobWithGoods(allProducts);
                break;
            }
            case 404: {
                alert("Unable to get all product in cart!");
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

function handleCreateOrderInDbResponse() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        switch (httpRequest.status) {
            case 200: {
                alert("Order successful create");

                // Очищаем корзину после создания ордера
                localStorage.removeItem('cart');
                location.href = "/views/htmlPages/clientPage.html";
                break;
            }
            case 404: {
                alert("Unable to create order!");
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

function goToCreateOrder() {
    let totalCostInCart = document.getElementById('totalCostInCart').value;

    if (localStorage.getItem('identificationDataClient') != null) {
        identificationDataClient = JSON.parse(localStorage.getItem('identificationDataClient'));

        var loginClientInCart = identificationDataClient['login'];
        createOrderInDB(totalCostInCart, loginClientInCart);

    } else {
        alert("Something went wrong...!!!");
        location.reload();
    }
}

function jobWithGoods(array) {
    let goods = array; //все товары в массиве httpRequest.response
    checkCart();
    showCart(goods); //вывожу товары на страницу
}

//Cart display and fill function
function showCart(goods) {
    let table = document.getElementById('tabGoods');
    let totalCost = 0;

    if (Object.keys(cart).length === 0) {
        table.hidden = true;
        document.getElementById("totalCostOrder").hidden = true;
        document.getElementById("noGoods").innerText = "NO goods in cart";
    } else {
        table.hidden = false;
        document.getElementById("totalCostOrder").hidden = false;

        clearRowsFromTable('tabGoods');

        //Filling in the table
        for (var element in goods) {
            for (var key in cart) {
                if (key == goods[element].id) {

                    var row = table.insertRow(table.rows.length);
                    var colName = row.insertCell(0);
                    var colCount = row.insertCell(1);
                    var colPlus = row.insertCell(2);
                    var colMinus = row.insertCell(3);
                    var colCost = row.insertCell(4);
                    var colDelete = row.insertCell(5);

                    var productId = document.createTextNode(goods[element].id);
                    var productName = document.createTextNode(goods[element].name);
                    var productPrice = document.createTextNode(goods[element].price);

                    var countInCart = document.createTextNode(cart[key]);
                    var fixedCost = (cart[key] * goods[element].price).toFixed(2);

                    totalCost = totalCost + (cart[key] * goods[element].price);

                    var elementCost = document.createTextNode(fixedCost);

                    var buttonDelete = document.createElement('input');
                    buttonDelete.setAttribute('type', 'button');
                    buttonDelete.setAttribute('value', 'Delete');
                    buttonDelete.setAttribute('onclick', 'deleteGoods(this)');
                    buttonDelete.setAttribute('prodId', goods[element].id);


                    var buttonMinus = document.createElement('input');
                    buttonMinus.setAttribute('type', 'button');
                    buttonMinus.setAttribute('value', '-');
                    buttonMinus.setAttribute('onclick', 'minusGoods(this)');
                    buttonMinus.setAttribute('prodId', goods[element].id);

                    var buttonPlus = document.createElement('input');
                    buttonPlus.setAttribute('type', 'button');
                    buttonPlus.setAttribute('value', '+');
                    buttonPlus.setAttribute('onclick', 'plusGoods(this)');
                    buttonPlus.setAttribute('prodId', goods[element].id);

                    colDelete.appendChild(buttonDelete);
                    colName.appendChild(productName);
                    colMinus.appendChild(buttonMinus);
                    colCount.appendChild(countInCart);
                    colPlus.appendChild(buttonPlus);
                    colCost.appendChild(elementCost);
                }
            }
        }
    }
    updateSummaryCoast(totalCost.toFixed(2));
}

//Работа с корзиной___
//Добавление товаров в корзину
function plusGoods(btnPlus) {
    var artPlus = btnPlus.getAttribute('prodId');

    if (cart[artPlus] !== undefined && cart[artPlus] != null) {
        cart[artPlus]++;
    } else {
        cart[artPlus] = 1;
    }
    localStorage.setItem('cart', JSON.stringify(cart));// сохраняем в localStorage
    showCart(allProducts);
}

// Удаление товаров из корзины
function minusGoods(btnMinus) {
    var artMinus = btnMinus.getAttribute('prodId');

    if (cart[artMinus] > 1 && cart[artMinus] !== undefined && cart[artMinus] != null) {
        cart[artMinus]--;
    } else {
        delete cart[artMinus];
    }

    localStorage.setItem('cart', JSON.stringify(cart));// сохраняем в localStorage
    showCart(allProducts);
}

// Удаление всех товаров из корзины
function deleteGoods(btnDelete) {
    var artDelete = btnDelete.getAttribute('prodId');
    delete cart[artDelete];

    localStorage.setItem('cart', JSON.stringify(cart));// сохраняем в localStorage

    showCart(allProducts);
}

function updateSummaryCoast(totalCost) {
    document.getElementById("totalCostInCart").innerText = totalCost;
}

function checkCart() {
    //проверяю наличие корзины в localStorage;
    if (localStorage.getItem('cart') != null) {
        cart = JSON.parse(localStorage.getItem('cart'));
    }
}

//___
