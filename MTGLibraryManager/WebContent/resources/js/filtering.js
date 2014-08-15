if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.slice(0, str.length) == str;
  };
}

$( document ).ready(function() {
	cardNameColumnPos = $('table.listing thead tr th:contains("Card name")').index();
	typeColumnPos = $('table.listing thead tr th:contains("Type")').index();
	manaColumnPos = $('table.listing thead tr th:contains("Mana")').index();
	rarityColumnPos = $('table.listing thead tr th:contains("Rarity")').index();
	
	$('div#filter input:checkbox').click(function() {
		filterCards();
	});

	$("div#filter legend img#reversefilter").click(function() {
		$("div#filter input:checkbox").each(function() {
			if (this.checked) {
				$(this).prop("checked", false);
				
			} else {
				$(this).prop("checked", true);
			}
		});

		filterCards();
	});

	$("div#filter legend img#resetfilter").click(function() {
		$("div#filter input:checkbox").each(function() {
			$(this).prop("checked", false);
		});

		var tds = $("table.listing tbody tr");
		tds.each(function() {
			$(this).show().addClass('visible');
		});
		
		zebraRows(tds);
	});
	
	$('#cardfilter').keyup(function(event) {
		//if esc is pressed or nothing is entered
		if (event.keyCode == 27 || $(this).val() == '') {
			//if esc is pressed we want to clear the value of search box
			$(this).val('');
		}

		filterCards();
	});
});

function filterCards() {
	var rarityFilterValuesArray = $("div#filter div#rarityFilter input:checkbox:checked").map(function() {
		return $(this).attr("name");
    }).get();
	
	var colorFilterValuesArray = $("div#filter div#colorFilter div#colors input:checkbox:checked").map(function() {
		return $(this).attr("name");
    }).get();
	
	var filterOnQuantities = ($("div#filter div#quantityFilter input:checkbox[name='quantities']").is(":checked"));
	
	var filterColorRegEx = new RegExp(generateColorFilterRegExPattern(colorFilterValuesArray));
	var filterLandRegEx = new RegExp(generateLandFilterRegExPattern());
	
	var filterCardNameRegEx = new RegExp(generateCardNameFilterRegExPattern(), "i");
	
	$("table.listing tbody tr").each(function(index, tr) {
		var hideRow = false;
		
	    var lines = $("td", tr).map(function(index, td) {
	    	var textValue = $(td).text();
	    	
	    	if ($(td).find("img").length) {
	    		textValue = "";
	    		$("img", $(td)).each(function() {
	    			textValue += $(this).attr("alt");
	    		});
	    	}
	    	
	        return textValue.trim();
	    });
	    
	    if (lines[cardNameColumnPos].match(filterCardNameRegEx) == null) {
	    	hideRow = true;
		}
	    
	    if (!hideRow) {
	    	if (lines[typeColumnPos].match(filterLandRegEx) != null) {
		    	hideRow = true;
			}
	    }
	    
	    if (!hideRow) {
	    	if (lines[manaColumnPos].match(filterColorRegEx) != null) {
		    	hideRow = true;
			}
	    }
	    
	    if (!hideRow) {
	    	if (jQuery.inArray(lines[rarityColumnPos], rarityFilterValuesArray) != -1) {
		    	hideRow = true;
		    }
	    }
	    
	    if (!hideRow) {
	    	if (filterOnQuantities) {
		    	var sum = 0;
				
				$(":text", this).each(function() {
					if(!isNaN(this.value) && this.value.length != 0) {
		                sum += parseInt(this.value);
		            }
				});
				
				if (sum == 0) {
					hideRow = true;
				}
		    }
	    }
	    
	    if (hideRow) {
	    	$(tr).hide().removeClass('visible');
	    	
	    } else {
	    	$(tr).show().addClass('visible');
	    	
	    }
	});
	
	zebraRows($("table.listing tbody tr.visible"));
}

function generateColorFilterRegExPattern(colorFilterValuesArray) {
	var regExPattern = "^NO_FILTER$";
	var isColorFilter = colorFilterValuesArray.length > 0;
	var isColorlessFilter = $('div#filter div#colorFilter div#colorlesses input:checkbox[name="C"]').is(":checked");

	var filterColorRegExPattern = "";
	if (isColorFilter) {
		filterColorRegExPattern = "[" + colorFilterValuesArray.join("") + "]";
		
		regExPattern = "(" + filterColorRegExPattern + ")";
	}
	
	var filterColorlessRegExPattern = "";
	if (isColorlessFilter) {
		filterColorlessRegExPattern = "^[\\dX]+$";
		
		if (regExPattern != "") {
			regExPattern += "|";
		}
		
		regExPattern += "(" + filterColorlessRegExPattern + ")";
	}
	
	return regExPattern;
}

function generateLandFilterRegExPattern() {
	var regExPattern = "^NO_FILTER$";
	
	if ($('div#filter div#colorFilter div#colorlesses input:checkbox[name="L"]').is(":checked")) {
		regExPattern = "(^Land$)|(^Land .*)|(.* Land$)|(^.* Land .*$)";
	}
	
	return regExPattern;
}

function generateCardNameFilterRegExPattern() {
	var regExPattern = ".*";
	
	var value = $.trim($("#cardfilter").val());
	
	if (value != "") {
		regExPattern = value;
	}
	
	return regExPattern;
}
