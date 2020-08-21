// variables created for dataservice
let ds = new DataService();
let moneyIn = 0;
let itemId = 0;

// functions that interact with your page
function updateMoney(message) {
    let cashInput = $("#cash-in-system");
    cashInput.val(message);
}

//functions for your on clicks
function onAddDollarClicked(e) {
    // stop the browser from reloading the page
    e.preventDefault();
    moneyIn += 1;
/* toFixed(2) - Returns a string representing a number in fixed-point notation.
@param fractionDigits — Number of digits after the decimal point. */ 
    updateMoney(moneyIn.toFixed(2));
}

function onAddQuarterClicked(e) {
    e.preventDefault();
    moneyIn += 0.25;
    updateMoney(moneyIn.toFixed(2));
}

function onAddDimeClicked(e) {
    e.preventDefault();
    moneyIn += 0.10;
    updateMoney(moneyIn.toFixed(2));
}

function onAddNickelClicked(e) {
    e.preventDefault();
    moneyIn += 0.05;
    updateMoney(moneyIn.toFixed(2));
}

function onItemClicked(e) {
    e.preventDefault(); 
    let item = $(this);
    itemId = item.data("itemid");
    $("#item-number").val(itemId);
}

function onMakePurchaseClicked(e) {
    e.preventDefault();
    ds.getItemById(itemId, moneyIn, onGetItemByIdSuccess, logError);
    $("#cash-in-system").val(moneyIn.toFixed(2));
}

function onReturnChangeClicked(e) {
    e.preventDefault();
    moneyIn = 0;
    itemId = 0;

    resetForms();

    ds.getAllItems(refreshItems, logError);

}

//gather items from VM and have their properties display & added to the div in html
function refreshItems(items) {
    //empty() method removes all child nodes and content from the selected elements
    $('#items').empty();

    for (let i = 0; i < items.length; i++) {
        const item = items[i]
        let itemFormat =

        `<div class="selection" data-itemId ="${items[i].id}">
        <p class="text-left">${items[i].id}</p>
        <p class="text-center">${items[i].name}</p>
        <p class="text-center">$ ${items[i].price.toFixed(2)}</p>
        <p class="text-center">Quantity Left: ${items[i].quantity}</p>
        </div>`;
        // will add the format created above
        $("#items").append(itemFormat);
    }

}
//successful transaction - thank you message and change returned displayed
function onGetItemByIdSuccess(items) {
    $("#message-text").val("Thank You!!!");
    $("#change-returned").val(
        items.quarters + " quarters, " + items.dimes + " dimes, " + items.nickels + " nickels, " + items.pennies + " pennies"
    );

}

//error message
function logError(err) {
    $("#message-text").val(err.responseJSON.message);
    calculateChange(moneyIn);

}




//change calculation
function calculateChange(moneyIn) {
    // Math.floor returns the largest integer less than or equal to a given number
    var quarters = Math.floor(moneyIn / 25);
    moneyIn = moneyIn - (quarters * 25);

    var dimes = Math.floor(moneyIn / 10);
    moneyIn = moneyIn - (dimes * 10);

    var nickels = Math.floor(moneyIn / 5);
    moneyIn = moneyIn - (nickels * 5);

    var pennies = Math.floor(moneyIn / 1);
    moneyIn = moneyIn - (pennies * 1);

    $("#change-returned").val(quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels, " + pennies + " pennies");

}

// forms are cleared
function resetForms() {
    $("#cash-in-system").val("");
    $("#message-text").val("");
    $("#item-number").val("");
    $("#change-returned").val("");
}

// page setup
$(document).ready(function () {
    moneyId = 0;
    itemId = 0;
    resetForms();
    //Wire up all the clicks
    ds.getAllItems(refreshItems, logError);
    $(document).on('click', '.selection', onItemClicked);
    $(document).on('click', '#add-dollar', onAddDollarClicked);
    $(document).on('click', '#add-quarter', onAddQuarterClicked);
    $(document).on('click', '#add-dime', onAddDimeClicked);
    $(document).on('click', '#add-nickel', onAddNickelClicked);
    $(document).on('click', '#make-purchase', onMakePurchaseClicked);
    $(document).on('click', '#return-change', onReturnChangeClicked);
});