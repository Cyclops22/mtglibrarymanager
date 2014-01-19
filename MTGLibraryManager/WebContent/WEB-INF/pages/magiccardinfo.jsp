<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>magiccards.info site</title>
</head>
<body>

	<a href="scanmagiccardsinfo.html">Scan site</a>

	<table>
		<thead>
			<tr>
				<th>Set</th>
				<th>Url</th>
				<th>Number of cards</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="set" items="${sets}">
				<tr>
					<td><a href='${set.key}/displaySet.html'>${set.key}</a></td>
					<td>${set.value}</td>
					<td>${fn:length(cardsBySet[set.key])}</td>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
