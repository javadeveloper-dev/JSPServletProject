package employeecrudmaven.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

import employeecrudmaven.dao.EmployeeDAO;
import employeecrudmaven.model.EmployeeModel;
import employeecrudmaven.dao.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO {
	static Connection connection = DBConnection.getConnection();

	public void insertEmployee(EmployeeModel employee, FileInputStream fileInputStream) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
			preparedStatement.setString(1, employee.getFirstname());
			preparedStatement.setString(2, employee.getLastname());
			preparedStatement.setInt(3, employee.getAge());
			preparedStatement.setDouble(4, employee.getSalary());
			preparedStatement.setString(5, employee.getDateOfJoining());
			preparedStatement.setString(6, employee.getCountry());
			preparedStatement.setString(7, employee.getEmail());
			preparedStatement.setBinaryStream(8, fileInputStream);
			preparedStatement.setInt(9, employee.getLoginId());
			preparedStatement.setString(10, employee.getFilename());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int selectLatestIdFromEmployee(int id) {

		int employeeId = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employeeId = resultSet.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeId;
	}

	public ArrayList<String> getEmployeeEmail() {
		ArrayList<String> emailList = new ArrayList<String>();
		try {
			PreparedStatement preparedstatementForAllEmail = connection
					.prepareStatement(SELECT_ALL_EMAIL_FROM_EMPLOYEE_SQL);
			ResultSet resultsetForAllEmail = preparedstatementForAllEmail.executeQuery();
			while (resultsetForAllEmail.next()) {
				String email = resultsetForAllEmail.getString("email");
				emailList.add(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailList;
	}

	public EmployeeModel getEmployeeById(int id) {
		EmployeeModel employee = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();

			while (resultSet.next()) {
				id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String country = resultSet.getString("country");
				int age = resultSet.getInt("age");
				Double salary = Double.parseDouble(resultSet.getString("salary"));
				String dateOfJoining = resultSet.getString("doj");
				String email = resultSet.getString("email");
				LinkedHashSet<String> skills = new LinkedHashSet<String>();
				skills = employeeDAOImpl.getEmployeeSkillsById(id);
				String path = resultSet.getString("fileName");
				skills = employeeDAOImpl.getEmployeeSkillsById(id);
				Blob image;
				byte[] profilePicture;
				image = resultSet.getBlob("image");
				profilePicture = image.getBytes(1, (int) image.length());
				employee = new EmployeeModel(id, firstName, lastName, skills, age, salary, dateOfJoining, email,
						country, profilePicture, path);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	public List<EmployeeModel> getAllEmployee(int loginId) {
		List<EmployeeModel> employeeList = new ArrayList<EmployeeModel>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGIN_ID);
			preparedStatement.setInt(1, loginId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				int age = Integer.parseInt(resultSet.getString("age"));
				double salary = Double.parseDouble(resultSet.getString("salary"));
				String dateOfJoining = resultSet.getString("doj");
				String country = resultSet.getString("country");
				LinkedHashSet<String> skills = new LinkedHashSet<String>();
				EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();
				skills = employeeDAOImpl.getEmployeeSkillsById(id);
				String email = resultSet.getString("email");
				Blob image = null;
				byte[] profilePicture;
				image = resultSet.getBlob("image");
				if (image != null) {
					profilePicture = image.getBytes(1, (int) image.length());
					employeeList.add(new EmployeeModel(id, firstName, lastName, skills, age, salary, dateOfJoining,
							country, email, profilePicture));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}

	public boolean updateEmployee(EmployeeModel employee, FileInputStream fileInputStream) {
		boolean udpatedEmployeeDetail = false;
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {
			preparedStatement.setString(1, employee.getFirstname());
			preparedStatement.setString(2, employee.getLastname());
			preparedStatement.setInt(3, employee.getAge());
			preparedStatement.setDouble(4, employee.getSalary());
			preparedStatement.setString(5, employee.getDateOfJoining());
			preparedStatement.setString(6, employee.getCountry());
			preparedStatement.setBinaryStream(7, fileInputStream);
			preparedStatement.setString(8, employee.getFilename());
			preparedStatement.setInt(9, employee.getId());
			udpatedEmployeeDetail = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return udpatedEmployeeDetail;
	}

	public boolean deleteEmployee(int id) throws Exception {
		boolean deleteEmployee = false;

		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);) {
			preparedStatement.setInt(1, id);
			deleteEmployee = preparedStatement.executeUpdate() > 0;
		}
		return deleteEmployee;
	}

	public int insertEmployeeSkillsById(int id, LinkedHashSet<String> skills) {
		try {
			PreparedStatement preparedstatatementForInsertSkills = connection.prepareStatement(INSERT_SKILLS);
			LinkedHashSet<String> checkSkillsSet = new LinkedHashSet<String>();
			preparedstatatementForInsertSkills.setInt(1, id);
			checkSkillsSet = skills;
			for (String checkedSkills : checkSkillsSet) {
				preparedstatatementForInsertSkills.setString(2, checkedSkills);
				preparedstatatementForInsertSkills.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public LinkedHashSet<String> getEmployeeSkillsById(int id) {
		LinkedHashSet<String> skills = new LinkedHashSet<String>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SKILL_BY_ID);

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			LinkedHashSet<String> skillsFromBackEnd = new LinkedHashSet<String>();
			while (resultSet.next()) {
				String backEndSkills = resultSet.getString("empskills");
				skillsFromBackEnd.add(backEndSkills);
				skills = (LinkedHashSet) skillsFromBackEnd.clone();
			}
			skillsFromBackEnd.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skills;
	}

	public void deleteEmployeeSkillsById(int employeeId, LinkedHashSet<String> skills) {
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SKILLS);
			preparedStatement.setInt(1, employeeId);
			for (String skillsToBeDelete : skills) {
				preparedStatement.setString(2, skillsToBeDelete);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateEmployee(EmployeeModel employee) {
		return false;
	}

	public int selectLatestIdFromEmployee() {
		return 0;
	}

}
