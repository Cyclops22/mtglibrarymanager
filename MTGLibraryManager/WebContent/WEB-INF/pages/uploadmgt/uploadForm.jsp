<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<title>Being Java Guys | Hello World</title>
</head>
<body>

	<center>
		<h2>Being Java Guys | Hello World</h2>
		<h3>Please select a file to upload !</h3>

		<form:form method="post" enctype="multipart/form-data"
			modelAttribute="uploadedFile" action="fileUpload.htm">
			<table>
				<tr>
					<td>Upload File:</td>
					<td><input type="file" name="file" /></td>
					<td style="color: red; font-style: italic;"><form:errors
							path="file" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	</center>
</body>
</html>
