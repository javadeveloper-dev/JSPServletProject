<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style>
.loginbody {
	margin-left: 500px;
	margin-top: 30px;
}

.loginbody p {
	color: red;
}

#loginbutton {
	margin-top: 30px;
	margin-left: 100px;
}

#signin {
	margin-top: 15px;
	margin-left: 30px;
}

#messegeLogin {
	margin-top: 40px;
}

#usernameAndPassword {
	margin-top: 10px;
}

#forgetPassword a {
	display: block;
	margin-left: 150px;
	margin-top: -16px;
	text-decoration: none;
}

#messegeUsername {
	color: red;
	margin-left: 96px;
	margin-top: -20px;
	font-size: 15px;
}

#messegePassword {
	color: red;
	margin-left: 96px;
	margin-top: -20px;
	font-size: 15px;
}
</style>
</head>
<body>
	<c:out value="${LogOutMessege}">
	</c:out>
	<c:out value="${messege}">
	</c:out>
	<div id="messegeLogin"></div>
	<form action="<%=request.getContextPath()%>/" method="post">
		<center>
			<h1>Login Employee</h1>
		</center>
		<div class="loginbody">
			<p>
				<c:out value="${messegeLogin}">
				</c:out>
			<p>
				<input type="hidden" name="Id">
			<div id="usernameAndPassword">
				UserName <span style="color: red; font-weight: bold">*</span>:- <input
					type="text" name="Username" value="${username}" /><br /> <br />
				<div id="messegeUsername">
					<c:out value="${messegeUsername}"></c:out>
				</div>
				<br /> Password <span style="color: red; font-weight: bold">*</span>:-
				<input type="password" name="Password" value="${password}" /><br />
				<br />
				<div id="messegePassword">
					<c:out value="${messegePassword}"></c:out>
				</div>
				<input type="submit" name="LogIn" id="loginbutton" value="Login" />
				<div id="signin">
					Don't have an Account?<a
						href="<%=request.getContextPath()%>/signup"> Sign up</a>
				</div>
			</div>
	</form>
	</div>
</body>
</html>