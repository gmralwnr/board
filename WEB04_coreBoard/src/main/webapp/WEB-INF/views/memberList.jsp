<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>JSP로 부른 PAGE</title>
	</head>
	<body>
		<h2>JSP로 부른 PAGE111</h2>
		<c:forEach var="mem" items="${ listMember }">
			${ mem.mbr_no } ${ mem.mbr_nm } <br/>
		</c:forEach>
	</body>
</html>