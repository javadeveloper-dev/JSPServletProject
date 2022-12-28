<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="employeecrudmaven.model.EmployeeModel.*"%>
<%@page
	import="employeecrudmaven.model.*,employeecrudmaven.controller.EmployeeController.*,employeecrudmaven.service.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<style>
#logoutsection {
	position: absolute;
	right: 20;
	top: 10;
	font-size: 20px;
}

#logout {
	position: absolute;
	top: 29;
	left: 9;
}
</style>
	<c:out value="${messegeLogin}">
	</c:out>
	<h1>
		<a href="<%=request.getContextPath()%>/new">Add Employee Detail's</a>
	</h1>
	<br>

	<div id="logoutsection">
		Hii
		<c:out value="${username}"></c:out>
		<a href="<%=request.getContextPath()%>/logout" id="logout">Logout</a>
	</div>
	<br>
	<h3>Employee Information</h3>
	<table border="1" width="100%" padding="10px">
		<tr>
			<th>Sr.No.</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Skills</th>
			<th>Age</th>
			<th>Salary (in Rs.)</th>
			<th>Country</th>
			<th>Date of Joining</th>
			<th>E-Mail</th>
			<th>Profile Picture</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
		<%
		int count = 0;
		%>
		<c:forEach items="${empList}" var="e">
			<tr>
				<td><center>
						<%
						count += 1;
						out.print(count);
						;
						%>
					</center></td>

				<%!String picture = "";
	byte[] image = null;%>
				<%
				image = ((EmployeeModel) pageContext.findAttribute("e")).getProfilePicture();
				if (image != null) {
					String encodedString = Base64.getEncoder().encodeToString(image);
					picture = "data:image/jpg;base64," + encodedString;

				}
				%>
				<td><center>${e.getFirstname()}</center></td>
				<td><center>${e.getLastname()}</center></td>
				<td><center>${e.getSkills()}</center></td>
				<td><center>${e.getAge()}</center></td>
				<td><center>
						<fmt:formatNumber type="number" maxFractionDigits="2"
							minFractionDigits="2" value="${e.getSalary()}"></fmt:formatNumber>
					</center></td>
				<td><center>${e.getCountry()}</center></td>
				<td><center>${e.getDateOfJoining()}</center></td>
				<td><center>${e.getEmail()}</center></td>

				<td><center>
						<img src="<%=picture%>" width="100px" height="100px" />
					</center></td>

				<td><center>
						<a href="<%=request.getContextPath()%>/edit?id=${e.getId()}">Edit</a>
					</center></td>
				<td><center>
						<a href="<%=request.getContextPath()%>/delete?id=${e.getId()}">Delete</a>
					</center></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
