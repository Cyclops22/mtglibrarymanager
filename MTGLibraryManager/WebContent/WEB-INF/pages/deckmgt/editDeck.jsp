<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<script src="<c:url value="/resources/js/common.js" />"></script>
	<script src="<c:url value="/resources/js/filtering.js" />"></script>
	
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
		
		<div style="width: 1024px; background-color: green;">
			<table class="listing">
				<thead>
					<tr>
						<th style="width: 30%;">Card name</th>
						<th style="width: 45%;">Type</th>
						<th style="width: 10%;">Mana</th>
						<th style="width: 15%;">Rarity</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="currCard" items="${cards}" varStatus="status">
						<tr>
							<td><c:out value="${currCard.card.name}"/></td>
							<!--
							<td>
								<c:forEach var="currSet" items="${currCard.sets}" varStatus="setStatus">
									<c:out value="${currSet.abbreviation}"/><c:if test="${setStatus.index < fn:length(currCard.sets) - 1}">, </c:if>
								</c:forEach>
							</td>
							-->
							<td><c:out value="${currCard.card.type}"/></td>
							<td><c:out value="${currCard.card.mana}"/></td>
							<td><c:out value="${currCard.card.rarity}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
			
		</div>
	</form:form>
</body>

<script type="text/javascript">
$( document ).ready(function() {
	zebraRows($("table.listing tbody tr"));
});
</script>
</html>