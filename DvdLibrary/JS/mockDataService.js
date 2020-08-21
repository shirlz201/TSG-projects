var DataService = function () {
  var self = this;
  let counter = 2;
  let dvdList = [
    {
      dvdId: 1,
      title: 'Matrix',
      releaseYear: '1999',
      director: 'The Wachowski Brothers',
      rating: 'R'
    }
  ];

  self.getDvds = function (callback, error) {
    setTimeout(function () {
      callback(dvdList);
    }, 2000);
  }
  self.addDvd = function (dvd, callback, error) {
    dvd.dvdId = ++counter;
    dvdList.push(dvd);
    setTimeout(function () {
      callback();
    }, 2000);
    // error('invalid dvd');
  }
  self.removeDvd = function (dvdId, callback) {
    let dList = [];
    for (let i = 0; i < dvdList.length; i++) {
      const dvd = dvdList[i];
      if (dvd.dvdId != dvdId) {
        dList.push(dvd);
      }
    }
    dvdList = dList;
    setTimeout(function () {
      callback();
    }, 2000);
  }
  self.getDvdById = function (dvdId, callback, error) {
    setTimeout(function () {
      for (let i = 0; i < dvdList.length; i++) {
        const dvd = dvdList[i];
        if (dvd.dvdId == dvdId) {
          callback(dvd);
          return;
        }
      }
      error('Dvd was not found');
    }, 2000);

  }
  self.updateDvd = function (dvd, callback, error) {
    setTimeout(function () {
      for (let i = 0; i < dvdList.length; i++) {
        if (dvdList[i].dvdId == dvd.dvdId) {
          dvdList[i] = dvd;
          callback();
          return;
        }
      }
      error('Dvd was not found');
    }, 2000);
  }
}