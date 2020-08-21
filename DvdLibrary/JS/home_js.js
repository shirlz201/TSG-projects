var ds = new DataService();

function formatRow(dvd) {
  return `<tr>
  <td>${dvd.title}</td>
  <td>${dvd.releaseYear}</td>
  <td>${dvd.director}</td>
  <td>${dvd.rating}</td>
  <td>
    <a href="#" data-dvdid='${dvd.dvdId}' class='deleteDvd'><i class="fa fa-trash"></i></a>
    <a href="#" data-dvdid='${dvd.dvdId}' class='editDvd'><i class="fa fa-pencil" aria-hidden="true"></i></a>
  </td>
</tr>`;
}
function refreshTable(dvds) {
  let dvdTable = $("#dvdTable>tbody");
  dvdTable.empty();
  for (let i = 0; i < dvds.length; i++) {
    const dvd = dvds[i];
    $(dvdTable).append(formatRow(dvd));
  }
}

function onSubmitAddDvd(e) {
  e.preventDefault(); // stop the browser from reloading the page
  let form = $(this);

  let dvd = {
    dvdId: 0,
    title: $("#title").val(),
    releaseYear: $("#releaseYear").val(),
    director: $("#director").val(),
    rating: $("#rating").val()
  };

  ds.addDvd(dvd, function(){
    ds.getDvds(refreshTable);
  }, alertError);
  
  $(form)[0].reset();
}
function onSubmitEditDvd(e) {
  e.preventDefault(); // stop the browser from reloading the page
  let form = $(this);
  let dvd = {
    dvdId: $("#editDvdId").val(),
    title: $("#editTitle").val(),
    releaseYear: $("#editReleaseYear").val(),
    director: $("#editDirector").val(),
    rating: $("#editRating").val()
  };
  ds.updateDvd(dvd, function(){
    ds.getDvds(refreshTable);
    $(form)[0].reset();
    $("#editDvdModule").modal('hide');
  });
  
}
function onClickEditDvd(e) {
  e.preventDefault();
  var dvdId = $(this).data('dvdid');
  ds.getDvdById(dvdId, function(dvd){
    if (dvd) {
      $("#editDvdId").val(dvd.dvdId);
      $("#editTitle").val(dvd.title);
      $("#editReleaseYear").val(dvd.releaseYear);
      $("#editDirector").val(dvd.director);
      $("#editRating").val(dvd.rating)
      $("#editDvdModule").modal('show');
    }
  }, alertError);
  
}
function onClickDeleteDvd(e) {
  e.preventDefault();
  var dvdId = $(this).data('dvdid');
  ds.removeDvd(dvdId, function () {
    ds.getDvds(refreshTable);
  });

}
function alertError(msg) {
  alert(msg);
}
$(document).ready(function () {
  // alert('test');
  ds.getDvds(refreshTable);
  $(document).on("submit", "#addDvd", onSubmitAddDvd);
  $(document).on("submit", "#editDvd", onSubmitEditDvd);
  $(document).on("click", ".editDvd", onClickEditDvd);
  $(document).on('click', ".deleteDvd", onClickDeleteDvd);
});