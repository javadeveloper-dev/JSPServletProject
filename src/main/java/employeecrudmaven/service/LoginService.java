package employeecrudmaven.service;

import java.util.*;

import employeecrudmaven.model.LoginModel;

public interface LoginService {

	public boolean isPasswordNotEqualsConfirmPassword(String password, String confirmPassword);

	public boolean regexValidationForUsername(String username);

	public boolean regexValidationForPassword(String password);

	public boolean isUsernameNotExists(String username);

	public LoginModel getUsername(String usenameLogin);

	public boolean isPasswordNotExists(String usernameLogin, String passwordLogin);

	public void insertLogin(LoginModel registrationModel);

	public int getId(String usernameLogin, String passwordLogin);

	public void updatePassword(String username, String password);

}
