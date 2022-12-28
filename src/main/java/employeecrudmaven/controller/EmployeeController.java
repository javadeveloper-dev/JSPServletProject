package employeecrudmaven.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import employeecrudmaven.model.EmployeeModel;
import employeecrudmaven.service.EmployeeService;
import employeecrudmaven.service.EmployeeServiceImpl;

@WebServlet("/list")
@MultipartConfig
public class EmployeeController extends HttpServlet {
	EmployeeService employeeService = new EmployeeServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;

		case "/insert":
			try {
				insertNewEmployee(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/delete":
			try {
				deleteEmployee(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/edit":
			showEditForm(request, response);
			break;

		case "/update":
			try {
				updateEmployee(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/list":
			listEmployee(request, response);
			break;
		default:
			break;
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setDateHeader("Expire", 0);
		HttpSession session = request.getSession();
		Integer loginId = (Integer) session.getAttribute("id");
		session.removeAttribute("idForEdit");
		if (loginId != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF\\Views\\EmployeeRegistration.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("http://localhost:8080/EmployeeCRUDMaven");
		}

	}

	private void insertNewEmployee(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ClassNotFoundException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF\\Views\\EmployeeRegistration.jsp");
		HttpSession session = request.getSession();
		Integer loginId = (Integer) session.getAttribute("id");
		boolean flag = true;
		String firstName = request.getParameter("empfname");
		String lastName = request.getParameter("emplname");
		String employeeAge = request.getParameter("empage");
		String employeeSalary = request.getParameter("empsalary");
		String country = request.getParameter("country");
		String dateOfJoining = request.getParameter("empdoj");
		Part part = request.getPart("file");
		FileInputStream fileInputStream = null;
		String fileName = part.getSubmittedFileName();
		String employeeSkills[] = new String[0];
		String checkedSkills = "";
		LinkedHashSet<String> skills = new LinkedHashSet<String>();
		String email = request.getParameter("empemail");
		if (request.getParameterValues("empSkills") == null) {
			skills.add("");
		} else {
			employeeSkills = request.getParameterValues("empSkills");
			for (int i = 0; i < employeeSkills.length; i++) {
				checkedSkills = employeeSkills[i];
				skills.add(checkedSkills);
			}
		}
		
		EmployeeModel employee = new EmployeeModel();
		if (firstName != null && lastName != null) {
			
			if (country == null) {
				request.setAttribute("country", "Select the Country");
				flag = false;
			} else {
				employee.setCountry(country);
			}

			if (fileName.isEmpty()) {
				request.setAttribute("Picture", "Upload a Profile Picture");
				flag = false;
			} else {
				fileInputStream = (FileInputStream) part.getInputStream();
			}

			if (dateOfJoining.isEmpty()) {
				request.setAttribute("dateOfJoining", "Enter the Date Of Joining");
				flag = false;
			} else {
				employee.setDateOfJoining(dateOfJoining);
			}

			if (!employeeService.regexValidationForFirstName(firstName)) {
				request.setAttribute("firstName", "Enter the Valid First Name");
				flag = false;
			} else {
				employee.setFirstname(firstName);
			}

			if (!employeeService.regexValidationForLastName(lastName)) {
				request.setAttribute("lastName", "Enter the Valid Last Name");
				flag = false;
			} else {
				employee.setLastname(lastName);
			}

			if (!employeeService.regexValidationForAge(employeeAge)) {
				request.setAttribute("age", "Enter the Valid Age");
				flag = false;
			} else {
				int age = Integer.parseInt(employeeAge);
				employee.setAge(age);
			}
			
			if (!employeeService.regexValidationSalary(employeeSalary)) {
				request.setAttribute("salary", "Enter the Valid Salary");
				flag = false;
			} else {
				double salary = Double.parseDouble(employeeSalary.replaceAll(",", ""));
				employee.setSalary(salary);
			}
		
			if(!employeeService.regexValidationEmail(email)) {
				request.setAttribute("email", "Enter the Valid Email");
				flag=false;
			}else {
				employee.setEmail(email);
			}
			employee.setSkills(skills);
			request.setAttribute("employee", employee);

			if (flag) {
				double salary = Float.parseFloat(request.getParameter("empsalary").replaceAll(",", ""));
				int age = Integer.parseInt(employeeAge);
				EmployeeModel newEmployee = new EmployeeModel(firstName, lastName, skills, age, salary, dateOfJoining,email,
						country, loginId, fileName);
				employeeService.insertEmployee(newEmployee, fileInputStream);
				employeeService.insertEmployeeSkillsById(newEmployee.getId(), skills);
				response.sendRedirect("http://localhost:8080/EmployeeCRUDMaven/list");
			} else {
				dispatcher.forward(request, response);
			}
		} else {
			dispatcher.forward(request, response);
			return;
		}
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			employeeService.deleteEmployee(id);
			response.sendRedirect(request.getContextPath() + "/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int idForEdit = Integer.parseInt(request.getParameter("id"));
		EmployeeModel employee;
		try {
			HttpSession session = request.getSession();
			session.setAttribute("idForEdit", idForEdit);
			Integer loginId = (Integer) session.getAttribute("id");
			if (loginId != null) {
				employee = employeeService.getEmployeeById(idForEdit);
				byte[] image = employee.getProfilePicture();
				String encodedString = Base64.getEncoder().encodeToString(image);
				String picture = "data:image/jpg;base64," + encodedString;
				session.setAttribute("editFileName", employee.getFilename());
				request.setAttribute("picture", picture);
				request.setAttribute("employee", employee);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("//WEB-INF//Views//EmployeeRegistration.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("http://localhost:8080/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		Integer loginId = (Integer) session.getAttribute("id");
		if (session != null && loginId != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF\\Views\\EmployeeRegistration.jsp");
			String firstName = request.getParameter("empfname");
			String lastName = request.getParameter("emplname");
			String dateOfJoining = request.getParameter("empdoj");
			String email = request.getParameter("empemail");
			String country = request.getParameter("country");
			String fileFromEdit = (String) session.getAttribute("editFileName");
			String employeeAge = request.getParameter("empage");
			String employeeSalary = request.getParameter("empsalary");
			Part part = request.getPart("file");
			FileInputStream fileInputStream = null;
			String file = part.getSubmittedFileName();
			String path = "D:\\ProfilePicture\\" + fileFromEdit;
			Integer idForUpdate = (Integer) session.getAttribute("idForEdit");
			request.setAttribute("idForEdit", idForUpdate);
			EmployeeModel employee = new EmployeeModel();
			boolean flag = true;

			if (firstName != null && lastName != null) {
				String employeeSkills[] = new String[0];
				String checkedSkills = "";
				LinkedHashSet<String> skills = new LinkedHashSet<String>();
				
				
				if (request.getParameterValues("empSkills") == null) {
					skills.add("");
				} else {
					employeeSkills = request.getParameterValues("empSkills");
					for (int i = 0; i < employeeSkills.length; i++) {
						checkedSkills = employeeSkills[i];
						skills.add(checkedSkills);
					}
					employee.setSkills(skills);
				}

				if (file.isEmpty()) {
					fileInputStream = new FileInputStream(path);
					file = fileFromEdit;
				} else {
					fileInputStream = (FileInputStream) part.getInputStream();
				}

				if (country == null) {
					request.setAttribute("country", "Select the country");
					flag = false;
				} else {
					employee.setCountry(country);
				}

				if (dateOfJoining.isEmpty()) {
					request.setAttribute("dateOfJoining", "Enter the Date Of Joining");
					flag = false;
				} else {
					employee.setDateOfJoining(dateOfJoining);
				}

				if (!employeeService.regexValidationForFirstName(firstName)) {
					request.setAttribute("firstName", "Enter the Valid First Name");
					flag = false;
				} else {
					employee.setFirstname(firstName);
				}

				if (!employeeService.regexValidationForLastName(lastName)) {
					request.setAttribute("lastName", "Enter the Valid Last Name");
				} else {
					employee.setLastname(lastName);
				}

				if (!employeeService.regexValidationForAge(employeeAge)) {
					request.setAttribute("age", "Enter the Valid Age");
					flag = false;
				} else {
					int age = Integer.parseInt(employeeAge);
					employee.setAge(age);
				}

				if (!employeeService.regexValidationSalary(employeeSalary)) {
					request.setAttribute("salary", "Enter the Valid Salary");
					flag = false;
				} else {
					double salary = Double.parseDouble(employeeSalary.replaceAll(",", ""));
					employee.setSalary(salary);
				}
				
				if(!employeeService.regexValidationEmail(email)) {
					request.setAttribute("email", "Enter the Valid Email");
					flag=false;
				}else {
					employee.setEmail(email);
				}

				request.setAttribute("employee", employee);

				if (flag) {
					double salary = Float.parseFloat(request.getParameter("empsalary").replaceAll(",", ""));
					int age = Integer.parseInt(employeeAge);
					EmployeeModel employeeforUpdate = new EmployeeModel(idForUpdate, firstName, lastName, skills, age, salary,
							dateOfJoining, country, file);
					employeeService.updateEmployee(employeeforUpdate, fileInputStream);
					EmployeeModel empskills = new EmployeeModel(idForUpdate, skills);
					employeeService.updateEmployeeSkills(empskills);
					response.sendRedirect(request.getContextPath() + "/list");
					return;
				} else {
					dispatcher.forward(request, response);
				}
			} else {
				dispatcher.forward(request, response);
				return;
			}
		}

	}

	private void listEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
			response.setDateHeader("Expire", 0);
			HttpSession session = request.getSession();
			Integer loginId = (Integer) session.getAttribute("id");
			if (loginId != null && session != null) {
				List<EmployeeModel> employeeList = employeeService.getAllEmployee(loginId);
				String username = (String) session.getAttribute("usernameLogin");
				request.setAttribute("empList", employeeList);
				request.setAttribute("username", username);
				session.removeAttribute("editFileName");
				session.removeAttribute("picture");
				RequestDispatcher dispatcher = request.getRequestDispatcher("//WEB-INF//Views//index.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("http://localhost:8080/EmployeeCRUDMaven/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
