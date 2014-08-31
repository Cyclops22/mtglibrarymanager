<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
	
	<script src="<c:url value="/resources/js/common.js" />"></script>
	<script src="<c:url value="/resources/js/filtering.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.freezeheader.js" />"></script>
	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	
	<title>Managing ${form.name}</title>
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
				<div>
					<label for="cardfilter">Filter</label>
					<input type="text" name="cardfilter" value="" id="cardfilter" />
				</div>
			</div>
		</fieldset>
	</div>
	
	<form:form commandName="form" action="submitEditDeck.html">
		<input type="button" value="Back" onclick="location.href='../manageDecks.html'"/>

		<h1><c:out value="${form.name}"/></h1>
		
		<div id="allcards-div" style="background-color: green; float: left;">
			<table class="listing">
				<thead>
					<tr style="background: #fff url(<c:url value='/resources/img/bg-header-grid.png' />) bottom repeat-x;">
					
						<th>Card name</th>
						<th>Type</th>
						<th>Mana</th>
						<th>Rarity</th>
						<th>Quantities</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="currCard" items="${cards}" varStatus="status">
						<tr>
							<td class="cardname" multiverseId="${currCard.card.multiverseid}"><c:out value="${currCard.card.name}"/></td>
							
							<td><c:out value="${currCard.card.type}"/></td>
							<td>
								<c:forEach var="cost" items="${currCard.card.manaCostElements}">
									<img src="http://mtgimage.com/symbol/mana/${cost}/16.png" alt="${cost}" />
								</c:forEach>
							</td>
							<td><c:out value="${currCard.card.rarity}"/></td>
							<td quantity="${currCard.quantity + currCard.foilQuantity}">
								<c:choose>
									<c:when test="${currCard.foilQuantity > 0}">
										<c:out value="${currCard.quantity}"/> / <c:out value="${currCard.foilQuantity}"/>
									</c:when>
									<c:otherwise>
										<c:out value="${currCard.quantity}"/>
									</c:otherwise>
								</c:choose>
								
								<div class="qtydetail" style="display: none;">
									<ul class="qtydetail">
										<c:forEach var="entry" items="${currCard.libraryCardFormBeansBySet}">
											<li>
												<img src="http://mtgimage.com/symbol/set/${entry.key.code}/c/32.png"/>
												<c:choose>
													<c:when test="${entry.value.foilQuantity > 0}">
														<c:out value="${entry.value.quantity}"/> / <c:out value="${entry.value.foilQuantity}"/>
													</c:when>
													<c:otherwise>
														<c:out value="${entry.value.quantity}"/>
													</c:otherwise>
												</c:choose>
											</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="background-color: blue; float: left;">
			<img id="cardimage" src="http://archive.wizards.com/global/images/cardback.jpg" width="230" height="317"/>
		</div>
	</form:form>
</body>

<script type="text/javascript">
$( document ).ready(function() {
	$('table.listing tbody tr').hover(function() {
		$(this).toggleClass('hover');
	});
	
	zebraRows($("table.listing tbody tr"));

	$( document ).tooltip({
		items: "table.listing tbody tr td[quantity]",
		content: function() {
			var element = $( this );

			if (element.attr("quantity") > 0) {
				return $("div.qtydetail", element).html();
			}
		}
	});

	$("td.cardname").mouseenter(function() {
		$("#cardimage").attr("src", "http://mtgimage.com/multiverseid/" + $(this).attr('multiverseId') + ".jpg");
	});

	$("#allcards-div").height(function(index, height) {
	    return window.innerHeight - $(this).offset().top;
	});
	
	$("table.listing").freezeHeader({ 'height': '600px' });
	
});
</script>
</html>