$(function() {
	$.get("/config/general/menu", function(data) {
		$("#menuHolder").html(data);
	});
});