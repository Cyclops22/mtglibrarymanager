<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Managing library set</title>
</head>
<body>
	<form:form commandName="form" action="submitSetLibrary.html">
		<input type="submit" value="Save" />
		<input type="button" value="Cancel" onclick="location.href='../editLibrary.html'" />
		
		<form:hidden path="id" />
		
		<table>
			<thead>
				<tr>
					<th rowspan="2" colspan="2">${form.referencedSet.name}</th>
					<th colspan="2">Quantities</th>
				</tr>
				<tr>
					<th>Normal</th>
					<th>Foil</th>
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
							<a href="<c:out value='${currCard.referencedCard.url}'/>" target="_blank">
								<c:out value="${currCard.referencedCard.name}"/>
							</a>
							
							
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