function zebraRows(selector) {
	$(selector).each(function(index) {
		if (index % 2 == 0) {
			$(this).removeClass("odd").toggleClass("even", true);
			
		} else {
			$(this).removeClass("even").toggleClass("odd", true);
		}
	});
}
