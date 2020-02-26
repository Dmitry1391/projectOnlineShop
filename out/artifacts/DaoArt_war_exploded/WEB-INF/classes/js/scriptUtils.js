//The function of deleting rows in tables before updating it
function clearRowsFromTable(tableName) {
    let table = document.getElementById(tableName);
    var tableRows = table.rows;
    var rowCount = tableRows.length;
    for (var x = rowCount - 1; x > 0; x--) {
        table.deleteRow(x)
    }
}