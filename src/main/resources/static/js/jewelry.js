$('.date').datepicker();
$('#addRow').on('click', () => {
	$("#sales-table tbody tr:last-child").clone(true).appendTo("#sales-table tbody");
});

$('#removeRow').on('click', () => {
	$("#sales-table tbody tr:first-child").remove();
});

