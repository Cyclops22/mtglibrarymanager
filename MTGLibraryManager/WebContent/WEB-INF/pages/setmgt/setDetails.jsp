<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>magiccards.info site</title>
</head>
<body>
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
			<c:forEach var="card" items="${cards}">
				<tr>
					<td>${card.number}</td>
					<td><a href='${card.url}' target="_blank">${card.name}</a></td>
					<td>${card.type}</td>
					<td>${card.mana}</td>
					<td>${card.rarity}</td>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
