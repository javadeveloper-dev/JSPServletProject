package employeecrudmaven.service;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import employeecrudmaven.model.EmployeeModel;

public interface EmployeeService {

	// Methods For Employee1 Table
	public void insertEmployee(EmployeeModel employee,FileInputStream fileInputStream);

	public EmployeeModel getEmployeeById(int id);
	
	public ArrayList<String> getEmployeeEmail();
	
	//Methods For Employee_Skills Table

	public boolean updateEmployee(EmployeeModel employee,FileInputStream fileInputStream) throws SQLException;

	public boolean deleteEmployee(int id) throws Exception;

	public boolean regexValidationForFirstName(String firstName);

	public boolean regexValidationForLastName(String lastName);

	public boolean regexValidationForAge(String age);

	public boolean regexValidationSalary(String salary);
	
	public boolean regexValidationEmail(String email);

	// Methods For Employee_Skills Table=
	public LinkedHashSet<String> getEmployeeSkillsById(int id);

	public void deleteEmployeeSkillsById(int employeeId, LinkedHashSet<String> skills);

	public int insertEmployeeSkillsById(int id, LinkedHashSet<String> skills);

	public boolean updateEmployeeSkills(EmployeeModel employee);

	public List<EmployeeModel> getAllEmployee(Integer loginId);

	

	
}
