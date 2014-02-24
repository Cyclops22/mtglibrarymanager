<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />" />
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	
	<title>Managing library set</title>
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
	<div id="filter">
		<fieldset>
			<legend>
				Filters
				<input type="button" name="reversefilter" value="Reverse" />
				<input type="button" name="resetfilter" value="Reset" />
			</legend>
			<div id="rarityFilter" class="spacer">
				<input type="checkbox" name="Common">Common</input>
				<input type="checkbox" name="Uncommon">Uncommon</input>
				<input type="checkbox" name="Rare">Rare</input>
				<input type="checkbox" name="Mythic Rare">Mythic Rare</input>
			</div>
			<div id="colorFilter" class="spacer">
				<div id="colors">
					<input type="checkbox" name="W">White</input>
					<input type="checkbox" name="U">Blue</input>
					<input type="checkbox" name="B">Black</input>
					<input type="checkbox" name="R">Red</input>
					<input type="checkbox" name="G">Green</input>
				</div>
				<div id="colorlesses">
					<input type="checkbox" name="C">Colorless</input>
					<input type="checkbox" name="L">Land</input>
				</div>
			</div>
			<div id="quantityFilter" class="spacer">
				<input type="checkbox" name="quantities">Without quantity</input>
			</div>
		</fieldset>
	</div>
	
	<form:form commandName="form" action="submitSetLibrary.html">
		<input type="submit" value="Save" />
		<input type="button" value="Cancel" onclick="location.href='../editLibrary.html'" />
		
		<form:hidden path="id" />
		
		<table class="listing">
			<thead>
				<tr>
					<th colspan="7"><h1>${form.referencedSet.name}</h1></th>
				</tr>
				<tr>
					<th><fmt:message key="edit.set.library.form.label.card.number" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.name" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.type" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.cost" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.rarity" bundle="${bundle}"/></th>
					<th colspan="2" class="center"><fmt:message key="edit.set.library.form.label.card.quantities" bundle="${bundle}"/></th>
				</tr>
				<tr>
					<th colspan="5">&nbsp;</th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.quantity.normal" bundle="${bundle}"/></th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.quantity.foil" bundle="${bundle}"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currCard" items="${form.cards}" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<form:hidden path="cards[${status.index}].id"/>
							<form:hidden path="cards[${status.index}].referencedCard.id"/>
							<form:hidden path="cards[${status.index}].referencedCard.name"/>
							
							<c:out value="${currCard.referencedCard.number}"/>
						</td>
						<td>
							<a href="<c:out value='${currCard.referencedCard.url}'/>" target="_blank">
								<c:out value="${currCard.referencedCard.name}"/>
							</a>
						</td>
						<td>
							<c:out value="${currCard.referencedCard.type}"/>
						</td>
						<td>
							<c:out value="${currCard.referencedCard.mana}"/>
						</td>
						<td>
							<c:out value="${currCard.referencedCard.rarity}"/>
						</td>
						<td>
							<input type="button" value="-" onclick="removeQty(${currCard.id});"/>
							<form:input path="cards[${status.index}].quantity" id="Qty${currCard.id}" size="3"/>
							<input type="button" value="+" onclick="addQty(${currCard.id});"/>
						</td>
						<td>
							<input type="button" value="-" onclick="removeFoilQty(${currCard.id});"/>
							<form:input path="cards[${status.index}].foilQuantity" id="FoilQty${currCard.id}" size="3"/>
							<input type="button" value="+" onclick="addFoilQty(${currCard.id});"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>

<script type="text/javascript">

$( document ).ready(function() {
	$('table.listing tbody tr').hover(function() {
		$(this).toggleClass('hover');
	});

	$('div#filter div#rarityFilter input:checkbox').click(function() {
		filterOnRarity();
		toggleFiltered();
		
	});

	$('div#filter div#colorFilter input:checkbox').click(function() {
		filterOnColor();
		toggleFiltered();
		
	});

	$("div#filter div#quantityFilter input:checkbox[name='quantities']").click(function() {
		filterOnQuantity();
		toggleFiltered();
	});

	$("div#filter legend input:button[name='reversefilter']").click(function() {
		$("div#filter input:checkbox").each(function() {
			if (this.checked) {
				$(this).prop("checked", false);
				
			} else {
				$(this).prop("checked", true);
			}
		});

		filterOnRarity();
		filterOnColor();
		filterOnQuantity();
		
		toggleFiltered();
	});

	$("div#filter legend input:button[name='resetfilter']").click(function() {
		$("div#filter input:checkbox").each(function() {
			$(this).prop("checked", false);
		});

		filterOnRarity();
		filterOnColor();
		filterOnQuantity();
		
		toggleFiltered();
	});
	

});

function filterOnRarity() {
	$("div#filter div#rarityFilter input:checkbox").each(function() {
		var rarity = $(this).attr("name");

		$("table.listing tbody tr").filter(function() {
			return $("td:nth-child(5)", this).text().trim() == rarity;
		}).toggleClass("rarity_filtered", this.checked);
	});
}

function filterOnColor() {
	$("div#filter div#colorFilter div#colors input:checkbox").each(function(index) {
		var color = $(this).attr("name");
		filterColorRegEx = new RegExp(".*" + color + ".*");

		$("table.listing tbody tr").filter(function() {
			return $("td:nth-child(4)", this).text().trim().match(filterColorRegEx) != null;
		}).toggleClass("color_filtered", this.checked);
	});

	$("div#filter div#colorFilter div#colorlesses input:checkbox").each(function() {
		if ($(this).attr("name") == "C") {
			$("table.listing tbody tr").filter(function() {
				return $("td:nth-child(4)", this).text().trim().match(/^[0-9]+$/) != null;
			}).toggleClass("color_filtered", this.checked);
				
		} else if ($(this).attr("name") == "L") {
			$("table.listing tbody tr").filter(function() {
				return $("td:nth-child(3)", this).text().trim().match(/\w* ?Land$/) != null;
			}).toggleClass("color_filtered", this.checked);
			
		}
	});
}

function filterOnQuantity() {
	if ($("div#filter div#quantityFilter input:checkbox[name='quantities']").is(":checked")) {
		
		$("table.listing tbody tr").each(function(index) {
			var sum = 0;
			
			$(":text", this).each(function() {
				if(!isNaN(this.value) && this.value.length != 0) {
	                sum += parseInt(this.value);
	            }
			});

			$(this).toggleClass("quantity_filtered", sum == 0);
		});
		
	} else {
		$("table.listing tbody tr").removeClass("quantity_filtered");
		
	}
}

function toggleFiltered() {
	$("table.listing tbody tr.rarity_filtered, table.listing tbody tr.quantity_filtered, table.listing tbody tr.color_filtered").fadeOut();
	$("table.listing tbody tr:not(.rarity_filtered,.quantity_filtered,.color_filtered)").fadeIn();
}

function addQty(id) {
	incrementValue(document.getElementById("Qty".concat(id)));
}

function addFoilQty(id) {
	incrementValue(document.getElementById("FoilQty".concat(id)));
}

function removeQty(id) {
	decrementValue(document.getElementById("Qty".concat(id)));
}

function removeFoilQty(id) {
	decrementValue(document.getElementById("FoilQty".concat(id)));
}

function incrementValue(element) {
	var currValue = element.value;

	currValue++;

	element.value = currValue;
}

function decrementValue(element) {
	var currValue = element.value;

	currValue--;
	if (currValue < 0) {
		currValue = 0;
	}

	element.value = currValue;
}

</script>
</html>