<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
.formBody {
	margin-left: 520px;
	margin-top: 100px;
}

#submitForm {
	margin-left: 100px;
	margin-top: 50px;
}

#messege {
	margin-left: 90px;
	margin-top: -19px;
	color: red;
	font-size: 15px;
}

#passwordMessege {
	margin-left: 80px;
	color: red;
	font-size: 15px;
}

#confirmPasswordMessege {
	margin-left: 140px;
	color: red;
	font-size: 15px;
}

h1 {
	margin-left: 30px;
	margin-top: -30px;
}
</style>
</head>
<body>
	<div class="formBody">
		<h1>Reset Password</h1>
		<form action="<%=request.getContextPath()%>/forgetPassword"
			method="post">
			UserName :- <input type="text" name="usernameForgetPass"
				value="${username}" /><br />
			<br />
			<div id="messege">
				<c:out value="${messege}">
				</c:out>
			</div>

			<br />
			<br />
			<div id="Password">
				Password :- <input type="password" name="passwordForgetPass"
					value="${password}" />
			</div>

			<div id="passwordMessege">
				<c:out value="${messegeForPassword}"></c:out>
				<br />
				<br />
				<br />
			</div>
			Confirm Password :- <input type="password"
				name="confirmpasswordForgetPass" value="${confirmPassword}" /><br />
			<div id="confirmPasswordMessege">
				<c:out value="${messegeForConfirmPassword}"></c:out>
				<br />
				<br />
				<br />
			</div>
			<div id="login">

				<input type="submit" id="submitForm" value="Submit" />
			</div>
		</form>
	</div>
</body>
</html>