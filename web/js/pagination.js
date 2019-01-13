$(document).ready(function() {
    $('#songs').DataTable({
        pageLength : 10,
        lengthChange: false,
        searching: false,
        ordering: false
    });

    $('#albums').DataTable({
        pageLength : 10,
        lengthChange: false,
        searching: false,
        ordering: false
    });
} );

