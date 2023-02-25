<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>
<jsp:useBean id="user" class="est.dsic.models.User" scope="session"></jsp:useBean>
  <h1>Hello <%= user.getEmail() %></h1>
</body>
</html>