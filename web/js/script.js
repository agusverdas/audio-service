var audios = $('audio');
audios.bind('timeupdate', function () {
    if (this.currentTime >= 30) this.pause();
});

$(document).ready(function() {
    $('#example').DataTable({
        pageLength : 10,
        lengthChange: false,
        searching: false,
        ordering: false
    });
} );