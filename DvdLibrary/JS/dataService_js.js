var DataService = function () {
  var self = this;

  self.getDvds = function (callback, myErrorFunc) {
    $.ajax({
      url: 'http://localhost:8080/dvds',
      method: 'GET',
      success: callback,
      error: myErrorFunc
    });
  }
  self.addDvd = function (dvd, callback, myErrorFunc) {
    $.ajax({
      url: 'http://localhost:8080/dvd',
      method: 'POST',
      data: JSON.stringify(dvd),
      "Content-Type": "application/json",
      success: callback,
      error: myErrorFunc
    });
  }
  self.removeDvd = function (dvdId, callback) {
    $.ajax({
      url: 'http://localhost:8080/dvd/' + dvdId,
      method: 'DELETE',
      success: callback,
      error: myErrorFunc
    });
  }
  self.getDvdById = function (dvdId, callback, myErrorFunc) {
    $.ajax({
      url: 'http://localhost:8080/dvd/' + dvdId,
      method: 'GET',
      success: callback,
      error: myErrorFunc
    });
  }
// POST - Create
// GET - Read
// PUT - Update
// DELETE - Delete

  self.updateDvd = function (dvd, callback, myErrorFunc) {
    $.ajax({
      "url": "http://localhost:8080/dvd/1",
      "method": "PUT",
      "headers": {
        "Content-Type": "application/json",
        "Accept": "*/*",
      },
      "data": JSON.stringify(dvd),

      success:callback,
      error:myErrorFunc
    });
   
    
    // $.ajax(settings).done(callback).fail(myErrorFunc);
  }
}