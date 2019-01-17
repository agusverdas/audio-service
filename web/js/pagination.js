$(document).ready(function() {
    $('#songs').DataTable({
        pageLength : 10,
        lengthChange: false,
        searching: false,
        bInfo : false,
        ordering: false
    });

    $('#albums').DataTable({
        pageLength : 10,
        lengthChange: false,
        searching: false,
        bInfo : false,
        ordering: false
    });
} );

