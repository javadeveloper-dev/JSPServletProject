package employeecrudmaven.dao;

import employeecrudmaven.model.LoginModel;

public interface LoginDAO {
	
	public static final String INSERT_lOGIN_TABLE="Insert into Login"+"(Username,Password)Values"+"(?,?)";
	
	public static final String SELECT_USERNAME_PASSWORD_BY_ID="select Login_Id from Login where Username=? and Password=?";
	
	public static final String SELECT_USERNAME="select * from Login where BINARY Username=?";
	
	public static final String UPDATE_PASSWORD="update Login set Password=? where Username=?";
	
	public LoginModel getUsername(String username);
	
	public void insertLogin(LoginModel loginModel);

	public int getId(String usernameLogin,String password);

	public void updatePassword(String username,String password);
	
}
