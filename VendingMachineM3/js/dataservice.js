var DataService = function () {
    var self = this;


    self.getAllItems = function (callback, onError) {

        $.ajax({
            url: "http://tsg-vending.herokuapp.com/items",
            method: 'GET',
            success: callback,
            error: onError
        })
    }

    self.getItemById = function (itemId, cashAmount, callback, onError) {

        $.ajax({
            url: "http://tsg-vending.herokuapp.com/money/" + cashAmount + "/item/" + itemId,
            method: "POST",
            success: callback,
            error: onError
        });
    }


}