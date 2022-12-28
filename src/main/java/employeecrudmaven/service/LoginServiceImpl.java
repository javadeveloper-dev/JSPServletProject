package employeecrudmaven.service;

import java.util.regex.*;

import employeecrudmaven.dao.LoginDAO;
import employeecrudmaven.dao.LoginDAOImpl;
import employeecrudmaven.model.LoginModel;

public class LoginServiceImpl implements LoginService {
	LoginDAO loginDAO = new LoginDAOImpl();

	public final String regexForUsername = "([a-zA-Z]{3,})*\\S";
	public final String regexForPassword = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$&+,:;=?@#|'<>.-^*()%!]).{5,14})";
	public final String regexForNoWhiteSpace = "\\S*";

	public void insertLogin(LoginModel registrationModel) {
		loginDAO.insertLogin(registrationModel);
	}

	public boolean isPasswordNotEqualsConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isUsernameNotExists(String username) {
		LoginModel loginModel = loginDAO.getUsername(username);
		if (loginModel == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isPasswordNotExists(String username, String password) {
		LoginModel loginModel = loginDAO.getUsername(username);
		if (loginModel == null) {
			return false;
		} else if (!loginModel.getPassword().equals(password)) {
			return false;
		}
		return true;
	}

	public int getId(String username, String password) {
		return loginDAO.getId(username, password);
	}

	public LoginModel getUsername(String usrenameLogin) {
		return null;
	}

	public boolean regexValidationForUsername(String username) {
		if (!Pattern.matches(regexForUsername, username)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean regexValidationForPassword(String password) {
		if (!(Pattern.matches(regexForPassword, password) && Pattern.matches(regexForNoWhiteSpace, password))) {
			return false;
		} else {
			return true;
		}
	}

	public void updatePassword(String username, String password) {
		loginDAO.updatePassword(username, password);
	}

}
