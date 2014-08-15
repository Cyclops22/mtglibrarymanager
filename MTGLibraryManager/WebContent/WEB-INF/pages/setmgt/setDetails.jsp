<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<title>${set.name}</title>
</head>
<body>
	<img id="back" src="" alt="Back"/>

	<h1>${set.name}</h1>
	<table>
		<thead>
			<tr>
				<th>No</th>
				<th>Card's name</th>
				<th>Type</th>
				<th>Mana</th>
				<th>Rarity</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="card" items="${set.cards}">
				<tr>
					<td>${card.number}</td>
					<td>${card.name}</td>
					<td>${card.type}</td>
					<td>
						<c:forEach var="cost" items="${card.manaCostElements}">
							<img src="http://mtgimage.com/symbol/mana/${cost}/16.png" alt="${cost}" />
						</c:forEach>
					</td>
					<td>${card.rarity}</td>
			</c:forEach>
		</tbody>
	</table>
</body>

<script type="text/javascript">

$(document).ready(function() {
	$("img#back").click(function() {
		window.location.href = "<c:url value='/setmgt/manageSets.html' />";
	});
});

</script>
</html>
