<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Managing library set</title>
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<script src="<c:url value="/resources/js/common.js" />"></script>
	<script src="<c:url value="/resources/js/filtering.js" />"></script>
	<script src="<c:url value="/resources/js/accounting.js" />"></script>
	
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/hoverbox.css" />" />
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	<fmt:setLocale value="fr_CA" scope="session"/>
	
	<div id="filter">
		<fieldset>
			<legend>
				Filters
				<img id="resetfilter" src="" alt="Reset"/>
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
				<input type="checkbox" name="quantities">With quantity</input>
			</div>
		</fieldset>
	</div>
	
	<form:form commandName="form" action="editSetLibrary.html">
		<img id="save" src="" alt="Save"/>
		<img id="cancel" src="" alt="Cancel"/>
		
		<form:hidden path="id" />
		
		<table class="listing hoverbox">
			<thead>
				<tr>
					<th colspan="9"><h1>${form.referencedSet.name}</h1></th>
					<c:set var="currSetName" value="${form.referencedSet.name}" />
				</tr>
				<tr>
					<th><fmt:message key="edit.set.library.form.label.card.number" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.name" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.type" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.cost" bundle="${bundle}"/></th>
					<th><fmt:message key="edit.set.library.form.label.card.rarity" bundle="${bundle}"/></th>
					<th colspan="2" class="center"><fmt:message key="edit.set.library.form.label.card.quantities" bundle="${bundle}"/></th>
					<th colspan="2" class="center">
						<fmt:message key="edit.set.library.form.label.card.prices" bundle="${bundle}"/>
						<img id="price-fetching" src="<c:url value="/resources/img/spinning-gold-dollar-symbol.gif" />" style="display: none; height: 25%;"/>
					</th>
				</tr>
				<tr>
					<th colspan="5">&nbsp;</th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.finish.normal" bundle="${bundle}"/></th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.finish.foil" bundle="${bundle}"/></th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.finish.normal" bundle="${bundle}"/></th>
					<th class="center"><fmt:message key="edit.set.library.form.label.card.finish.foil" bundle="${bundle}"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currCard" items="${form.cards}" varStatus="status">
					<tr>
						<td>
							<form:hidden path="cards[${status.index}].id"/>
							<form:hidden path="cards[${status.index}].referencedCard.id"/>
							<form:hidden path="cards[${status.index}].referencedCard.name"/>
							
							<c:out value="${currCard.referencedCard.number}"/>
						</td>
						<td>
							<span class="preview">
								<c:choose>
								    <c:when test="${empty form.referencedSet.code}">
								    	<c:set var="code" value="${fn:toLowerCase(form.referencedSet.gatherercode)}" />
								        
								    </c:when>
								    <c:otherwise>
								        <c:set var="code" value="${fn:toLowerCase(form.referencedSet.code)}" />
								        
								    </c:otherwise>
								</c:choose>
							
								<a href="http://magiccards.info/${code}/en/${currCard.referencedCard.number}.html" target="_blank">
									<c:out value="${currCard.referencedCard.name}"/>
								</a>
								<img src="#" class="preview" multiverseid="${currCard.referencedCard.multiverseid}"/>
							</span>
						</td>
						<td>
							<c:out value="${currCard.referencedCard.type}"/>
						</td>
						<td>
							<c:forEach var="cost" items="${currCard.referencedCard.manaCostElements}">
								<img src="http://gatherer.wizards.com/Handlers/Image.ashx?size=small&name=${cost}&type=symbol" alt="${cost}" />
								
							</c:forEach>
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
						<td style="text-align: right;">
							&nbsp;
						</td>
						<td style="text-align: right;">
							&nbsp;
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

	zebraRows($("table.listing tbody tr"));

	$("img#save").click(function() {
		$("form#form").submit();
	});

	$("img#cancel").click(function() {
		window.location.href = "<c:url value='../editLibrary.html' />";
	});

	$("span.preview").mouseenter(function() {
		var multiverseId = $("img", $(this)).attr("multiverseid");
		$("img", $(this)).attr("src", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseId + "&type=card");
	});

	$.ajax({
        url : '<c:url value="/pricelistmgt/getPrices.html" />',
        data : "setName=${currSetName}",
        beforeSend: function() {
            $("img#price-fetching").show();
        },
        success : function(data) {
        	var options = {
       			symbol : "$",
       			decimal : ",",
       			thousand: " ",
       			precision : 2,
       			format: "%v %s"
       		};
			$("table.listing tbody tr").each(function () {
				var cardName = $("td:nth-child(2)", $(this)).text().trim();
				var priceObj = data[cardName];

				if (typeof priceObj !== "undefined") {
					var price = priceObj.price;
					var foilPrice = priceObj.foilPrice;

					var qty = $("input[id^='Qty']", $(this)).val();
					var foilQty = $("input[id^='FoilQty']", $(this)).val();

					if (qty > 0) {
						$("td:nth-child(8)", $(this)).text(accounting.formatMoney(price, options));
					}

					if (foilQty > 0) {
						$("td:nth-child(9)", $(this)).text(accounting.formatMoney(foilPrice, options));
					}
				}
			});

			$("img#price-fetching").hide();
        }
    });
});

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