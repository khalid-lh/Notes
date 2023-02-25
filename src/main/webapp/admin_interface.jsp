<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
	integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"
	integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2XofN4zfuZxLkoj1gXtW8ANNCe9d5Y3eG5eD"
	crossorigin="anonymous"></script>
<title>Admin_Interface</title>
<style type="text/css">
.styled-table thead tr {
	background-color: black;
	color: #ffffff;
	text-align: left;
}

.styled-table th, .styled-table td {
	padding: 12px 15px;
}

.styled-table tbody tr {
	border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
	background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
	border-bottom: 2px solid #009879;
}

.styled-table tbody tr.active-row {
	font-weight: bold;
	color: #009879;
}

.alert-box {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
	display: none;
	justify-content: center;
	align-items: center;
}

.alert-box-modify {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
	display: none;
	justify-content: center;
	align-items: center;
}

.alert-content {
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	max-width: 500px;
}

.alert-content-modify {
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	max-width: 500px;
}

.input-group {
	display: flex;
	margin-bottom: 20px;
}

.input-group label {
	width: 100px;
	text-align: right;
	margin-right: 10px;
}

.input-group input {
	flex: 1;
	padding: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

.button-group {
	display: flex;
	justify-content: flex-end;
}

.cancel-button {
	margin-right: 10px;
	background-color: #ccc;
	border: none;
	color: #fff;
	padding: 10px;
	border-radius: 5px;
}

.modify-button {
	background-color: #428bca;
	border: none;
	color: #fff;
	padding: 10px;
	border-radius: 5px;
}

<
style>#name {
	width: 300px;
}
</style>
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <jsp:useBean id="user"
					class="est.dsic.models.User" scope="session"></jsp:useBean>
				<h1><%=user.getEmail()%></h1></a>
			<div class="d-flex justify-content-between">

				<button class="btn btn-secondary" type="button"
					onclick="openAlertBox(this)">Add user</button>

				<form action="adminservlet" method="get">
					<input type="hidden" name="action" value="logout">
					<button class="btn btn-secondary" type="submit">Log out</button>
				</form>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="mt-4">
			<div>
				<%@ 
					page
					import="est.dsic.dal.Userdao ,java.util.List, est.dsic.models.User"%>
				<%
				Userdao userdao = new Userdao();
				List<User> data = userdao.getAllusers(user);
				User user_admin = null;
				String Status = null;
				%>
				<center>
					<h2>List Users</h2>
					<table class="styled-table mt-6">
						<thead>
							<tr>
								<th>Name</th>
								<th>Email</th>
								<th>Status</th>
								<th>Modify</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (User obj : data) {
							%>
							<tr>
								<td><%=obj.getName()%></td>
								<td><%=obj.getEmail()%></td>
								<%
								if (obj.isAdmin()) {
									Status = "Admin";
								} else {
									Status = "User";
								}
								%>
								<td><%=Status%></td>
								<td>
										<button type="button" class="btn btn-primary"
											onclick="openAlertBox_modify(this)">Modify</button>

								</td>
								<td>
									<form action="adminservlet" method="post">
										<input type="hidden" name="email" value="<%=obj.getEmail()%>">
										<button type="submit" class="btn btn-danger">Delete</button>
									</form>
								</td>
							</tr>
							<div id="alert-box_modify" class="alert-box-modify">
								<div class="alert-content-modify">
									<center>
										<h2>Modify User</h2>
									</center>
									<form action="adminservlet" method="get">
										<div class="input-group">
											<div class="mb-3">
												<label for="name" class="form-label">Name</label> 
												<input
													type="text" id="name" class="form-control"  name="name"
													size="30" style="width: 300px;">
											</div>
										</div>
										<div class="input-group">
											<div class="mb-3">
												<label for="pwd" class="form-label">Password</label> <input
													type="password" id="pwd" name="pwd" class="form-control" 
													size="30" style="width: 300px;">
											</div>
										</div>
										<label for="country">Select Status:</label><br> <select
											id="status" name="status">
											<option value="User">User</option>
											<option value="Admin">Admin</option>
										</select>
										<div class="button-group">
										<input type="hidden" id="email" name="email">
											<input type="hidden" name="action" value="modify_user">
											<button id="" type="button" class="cancel-button"
												onclick="closeAlertBox_modify()">Cancel</button>
											<button type="submit" class="modify-button">Modify</button>
										</div>
									</form>
								</div>
							</div>
							<%
							}
							%>
						</tbody>
					</table>
				</center>
			</div>
		</div>
		<div id="alert-box" class="alert-box">
			<div class="alert-content">
				<center>
					<h2>Add User</h2>
				</center>
				<form action="adminservlet" method="get">
					<div class="input-group">
						<div class="mb-3">
							<label for="name" class="form-label">Name</label> <input
								type="text" class="form-control" id="name" name="name" size="30"
								style="width: 300px;">
						</div>
					</div>
					<div class="input-group">
						<div class="mb-3">
							<label for="email" class="form-label">Email address</label> <input
								type="email" class="form-control" id="email" name="email"
								size="30" style="width: 300px;">
						</div>
					</div>
					<div class="input-group">
						<div class="mb-3">
							<label for="pwd" class="form-label">Password</label> <input
								type="password" class="form-control" id="pwd" name="pwd"
								size="30" style="width: 300px;">
						</div>
					</div>
					<label for="country">Select Status:</label><br> <select
						id="status" name="status">
						<option value="User">User</option>
						<option value="admin">Admin</option>
					</select>
					<div class="button-group">
						<input type="hidden" name="action" value="add_user">
						<button id="" type="button" class="cancel-button"
							onclick="closeAlertBox()">Cancel</button>
						<button type="submit" class="modify-button">Add</button>
					</div>
				</form>
			</div>
		</div>
</body>
<script type="text/javascript">
function openAlertBox_modify(button) {
	document.getElementById("alert-box_modify").style.display = "flex";
	var td2 = button.parentNode.previousElementSibling.previousElementSibling;
	var email = document.getElementById("email");
	email.value = td2.textContent;
	var td1 = button.parentNode.previousElementSibling.previousElementSibling.previousElementSibling;
	var name = document.getElementById("name");
	name.value = td1.textContent;
	var td3 = button.parentNode.previousElementSibling;
	var status = document.getElementById("status");
	status.value = td3.textContent;
}
	function openAlertBox(button) {
		document.getElementById("alert-box").style.display = "flex";
	}

	function closeAlertBox() {
		document.getElementById("alert-box").style.display = "none";
	}
	
	function closeAlertBox_modify() {
		document.getElementById("alert-box_modify").style.display = "none";
	}
</script>
</html>