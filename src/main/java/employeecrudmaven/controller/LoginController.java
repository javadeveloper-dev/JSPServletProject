package employeecrudmaven.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import employeecrudmaven.model.LoginModel;
import employeecrudmaven.service.LoginService;
import employeecrudmaven.service.LoginServiceImpl;

@WebServlet("/")
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/signup")) {
			signupEmployee(request, response);
		} else if (action.equals("/logout")) {
			logOut(request, response);
		} else if (action.equals("/forgetPassword")) {
			forgetPassword(request, response);
		} else {
			loginEmployee(request, response);
		}
	}

	LoginService loginService = new LoginServiceImpl();

	private void forgetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("//WEB-INF//Views//ForgetPassword.jsp");
		String username = request.getParameter("usernameForgetPass");
		String password = request.getParameter("passwordForgetPass");
		String confirmPassword = request.getParameter("confirmpasswordForgetPass");
		boolean flag = true;
		if (username != null && password != null && confirmPassword != null) {
			if (confirmPassword.isEmpty()) {
				request.setAttribute("messegeForConfirmPassword", "Enter the Confirm Password");
				flag = false;
			}
			
			if (username.isEmpty()) {
				request.setAttribute("messege", "Enter the Username");
				flag = false;
			}
			if (!loginService.isUsernameNotExists(username)) {
				request.setAttribute("messege", "Enter the Correct Username");
				flag = false;
			}
			
			if (!loginService.isPasswordNotEqualsConfirmPassword(password, confirmPassword)
					&& !confirmPassword.isEmpty()) {
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("confirmPassword", confirmPassword);
				request.setAttribute("messegeForConfirmPassword", "Both Passowrd are not Same.Try again.");
				flag = false;
			}
			if (!loginService.regexValidationForPassword(password)) {
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("messegeForPassword", "Enter the Valid Password");
				flag = false;
			}
			if (flag) {
				loginService.updatePassword(username, password);
				request.setAttribute("messege", "Password Changed Successful..Please Log in.");
				RequestDispatcher dispatcherForForgetPassword = request
						.getRequestDispatcher("//WEB-INF//Views//Login.jsp");
				dispatcherForForgetPassword.forward(request, response);
				return;
			}
			dispatcher.forward(request, response);
		} else {
			dispatcher.forward(request, response);
		}
	}

	private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setDateHeader("Expire", 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("//WEB-INF//Views//Login.jsp");
		dispatcher.forward(request, response);
	}

	private void loginEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setDateHeader("Expire", 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("//WEB-INF//Views//Login.jsp");
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		HttpSession session = request.getSession();
		boolean flag = true;
		if ((username != null && password != null)) {
			if (username.isEmpty()) {
				session.setAttribute("username", username);
				request.setAttribute("messegeUsername", "Enter the Username");
				request.setAttribute("password", password);
				flag = false;
			}
			if (password.isEmpty()) {
				request.setAttribute("username", username);
				request.setAttribute("messegePassword", "Enter the Password");
				flag = false;
			}
			if (!loginService.isUsernameNotExists(username)) {
				request.setAttribute("password", password);
				request.setAttribute("messegeLogin", "Invalid Credentials...");
				flag = false;
			}
			if (!loginService.isPasswordNotExists(username, password)) {
				request.setAttribute("username", username);
				request.setAttribute("messegeLogin", "Invalid Credentials...");
				flag = false;
			}
			if (flag) {
				int id = loginService.getId(username, password);
				session.setAttribute("id", id);
				session.setAttribute("username", username);
				response.sendRedirect("http://localhost:8080/EmployeeCRUDMaven/list");
			} else {
				dispatcher.forward(request, response);
			}
		}
		Integer loginId = (Integer) session.getAttribute("id");
		if (!request.getServletPath().equals("/")) {
			RequestDispatcher dispatcherForErrorPage = request.getRequestDispatcher("//WEB-INF//Views//404Error.jsp");
			dispatcherForErrorPage.forward(request, response);
		}
		if (loginId != null && !response.isCommitted()) {
			session.setAttribute("id", loginId);
			response.sendRedirect("http://localhost:8080/EmployeeCRUDMaven/list");
		}
		if (!response.isCommitted()) {
			dispatcher.forward(request, response);
		}
	}

	private void signupEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("//WEB-INF//Views//SignUp.jsp");
		String username = request.getParameter("usernameForSignIn");
		String password = request.getParameter("passwordForSignIn");
		String confirmPassword = request.getParameter("confirmpasswordForSignIn");
		LoginModel signUpModel = new LoginModel(username, password);
		boolean flag = true;
		if (username != null && password != null && confirmPassword != null) {
			if (confirmPassword.isEmpty()) {
				request.setAttribute("messegeForConfirmPassword", "Enter the Confirm Password");
				flag = false;
			}
			if (loginService.isUsernameNotExists(username)) {
				request.setAttribute("username", username);
				request.setAttribute("messege", "Username is already Present");
				flag = false;
			}
			if (!loginService.regexValidationForUsername(username)) {
				request.setAttribute("username", username);
				request.setAttribute("messege", "Enter the Valid Username");
				flag = false;
			}
			if (!loginService.isPasswordNotEqualsConfirmPassword(password, confirmPassword)
					&& !confirmPassword.isEmpty()) {
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("confirmPassword", confirmPassword);
				request.setAttribute("messegeForConfirmPassword", "Both Passowrd are not Same.Try again.");
				flag = false;
			}
			if (!loginService.regexValidationForPassword(password)) {
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("messegeForPassword", "Enter the Valid Password");
				flag = false;
			}
			if (flag) {
				loginService.insertLogin(signUpModel);
				request.setAttribute("messege", "Registration Successful");
				RequestDispatcher dispatcherForSignIn = request.getRequestDispatcher("//WEB-INF//Views//Login.jsp");
				dispatcherForSignIn.forward(request, response);
			}
			dispatcher.forward(request, response);
			return;
		} else {
			dispatcher.forward(request, response);
		}
	}

}
