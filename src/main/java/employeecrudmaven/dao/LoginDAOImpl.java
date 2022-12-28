package employeecrudmaven.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import employeecrudmaven.model.LoginModel;

public class LoginDAOImpl implements LoginDAO {

	static Connection connection = DBConnection.getConnection();

	public void insertLogin(LoginModel loginModel) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(INSERT_lOGIN_TABLE);
			prepareStatement.setString(1, loginModel.getUsername());
			prepareStatement.setString(2, loginModel.getPassword());
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getId(String username, String password) {
		int id = 0;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(SELECT_USERNAME_PASSWORD_BY_ID);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("Login_Id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public LoginModel getUsername(String username) {
		LoginModel loginModel = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERNAME);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("Login_Id");
				String usernameFromDB = resultSet.getString("Username");
				String passwordFromDB = resultSet.getString("Password");
				loginModel = new LoginModel(usernameFromDB, passwordFromDB, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginModel;
	}

	public void updatePassword(String username,String password) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(UPDATE_PASSWORD);
			preparedStatement.setString(1,password);	
		preparedStatement.setString(2, username);
		
		preparedStatement.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
