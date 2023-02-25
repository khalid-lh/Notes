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
<title>User-Notes</title>
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

.alert-content {
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
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <jsp:useBean id="user"
					class="est.dsic.models.User" scope="session"></jsp:useBean>
				<h1><%=user.getEmail()%></h1></a>

			<div class="d-flex justify-content-between">
				<form action="noteservlet" method="get">
					<input type="hidden" name="action" value="save">
					<button class="btn btn-secondary" type="submit">Save</button>
				</form>
				<form action="noteservlet" method="get">
					<input type="hidden" name="action" value="logout">
					<button class="btn btn-secondary" type="submit">Log out</button>
				</form>
			</div>

		</div>
	</nav>
	<center>

		<div class="container">
			<div class="mt-4">
				<form action="noteservlet" method="get">
					<label for="floatingTextarea">Note</label>
					<textarea class="form-control" placeholder="Note Something"
						id="floatingTextarea" cols="50" name="note"></textarea>
					<input type="hidden" name="action" value="note_user"><br>
					<button class="btn btn-primary me-2" type="submit"
						value="Open Popup">Add Note</button>
				</form>
			</div>
			<br>
			<div>
				<%@ 
					page
					import="est.dsic.dal.Notedao ,java.util.List , est.dsic.models.Note"%>
				<%
				Notedao notedao = new Notedao();
				List<Note> data = notedao.getAllNotes(user);
				Note note = null;
				%>
				<table class="styled-table mt-6">
					<thead>
						<tr>
							<th>id</th>
							<th>Note</th>
							<th>Date</th>
							<th>Modify</th>
							<th>Delete</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Note obj : data) {
						%>
						<tr>
							<td><%=obj.getId()%></td>
							<td><%=obj.getNote()%></td>
							<td><%=obj.getDate()%></td>
							<td>
								<button type="button" class="btn btn-primary"
									onclick="openAlertBox(this)">Modify</button>
							</td>
							<td>
								<form action="noteservlet" method="post">
									<input type="hidden" name="id" value="<%=obj.getId()%>">
									<button type="submit" class="btn btn-danger">Delete</button>
								</form>
							</td>
						</tr>
						<div id="alert-box" class="alert-box">
							<div class="alert-content">
								<h2>Modify Note </h2>
								<form action="noteservlet" method="get">
									<div class="input-group">
										<textarea id="note" class="form-control"
											placeholder="Note Something" id="floatingTextarea" cols="50"
											name="note_to_modify" ></textarea>
									</div>
									<div class="button-group">
										<button id="" type="button" class="cancel-button"
											onclick="closeAlertBox()">Cancel</button>
										<input type="hidden" id="iput_id" name="id_note_modify"> <input type="hidden" name="action" value="modify_note">
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
			</div>
	</center>
</body>
<script type="text/javascript">
	function openAlertBox(button) {
		document.getElementById("alert-box").style.display = "flex";
		var td2 = button.parentNode.previousElementSibling.previousElementSibling;
		var myTextarea = document.getElementById("note");
		myTextarea.value = td2.textContent;
		var td1 = button.parentNode.previousElementSibling.previousElementSibling.previousElementSibling;
		var iput_id = document.getElementById("iput_id");
		iput_id.value = td1.textContent;
	}

	function closeAlertBox() {
		document.getElementById("alert-box").style.display = "none";
	}
</script>
</html>