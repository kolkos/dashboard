$(function() {
	$.get("/config/menu", function(data) {
		$("#menuHolder").html(data);
	});
});