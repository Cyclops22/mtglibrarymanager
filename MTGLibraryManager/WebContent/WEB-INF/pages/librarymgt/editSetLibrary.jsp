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
	<title>Managing library set</title>
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
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