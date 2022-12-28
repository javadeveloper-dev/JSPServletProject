package employeecrudmaven.service;

import java.io.FileInputStream;
import java.util.*;
import java.util.regex.*;
import employeecrudmaven.dao.EmployeeDAO;
import employeecrudmaven.dao.EmployeeDAOImpl;
import employeecrudmaven.dao.LoginDAOImpl;
import employeecrudmaven.model.EmployeeModel;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDAO employeeDAO = new EmployeeDAOImpl();

	String regexForName = "([A-Z][a-z]*)";
	String regexForSize = ".{3,}";
	String regexForAge = "1[89]|[2-9][0-9]|100";
	String regexForSalary = "([+]?\\d\\.?\\d*.?\\S*)(?=.*[^a-zA-Z])[^!@#%^&*?>']";
	String regexForEmail = "[a-zA-Z][a-zA-Z0-9]*[@][a-zA-Z0-9]+([.][a-zA-Z]+)+";

	public void insertEmployee(EmployeeModel employee, FileInputStream fileInputStream) {
		employeeDAO.insertEmployee(employee, fileInputStream);

	}

	public EmployeeModel getEmployeeById(int id) {
		return employeeDAO.getEmployeeById(id);
	}

	public List<EmployeeModel> getAllEmployee(int loginId) {
		return employeeDAO.getAllEmployee(loginId);
	}

	public boolean updateEmployee(EmployeeModel employee, FileInputStream fileInputStream) {
		return employeeDAO.updateEmployee(employee, fileInputStream);

	}

	public boolean deleteEmployee(int id) throws Exception {
		return employeeDAO.deleteEmployee(id);
	}

	public boolean updateEmployeeSkills(EmployeeModel employee) {

		boolean updateSkills = false;
		LinkedHashSet<String> userSkills = new LinkedHashSet<String>();
		userSkills = employee.getSkills();

		int employeeId = employee.getId();
		EmployeeDAO getSkills = new EmployeeDAOImpl();
		LinkedHashSet<String> skillsFromDB = new LinkedHashSet<String>();
		skillsFromDB = getSkills.getEmployeeSkillsById(employeeId);
		LinkedHashSet<String> retainUserSkills = (LinkedHashSet<String>) userSkills.clone();
		LinkedHashSet<String> removeUserSkills = (LinkedHashSet<String>) userSkills.clone();
		LinkedHashSet<String> retainDBSkills = (LinkedHashSet<String>) skillsFromDB.clone();
		LinkedHashSet<String> removeDBSkills = (LinkedHashSet<String>) skillsFromDB.clone();

		if (userSkills.equals(skillsFromDB)) {
			updateSkills = true;

		} else {
			retainUserSkills.retainAll(skillsFromDB);
			removeDBSkills.removeAll(retainUserSkills);
			EmployeeDAO employeeSkillsDelete = new EmployeeDAOImpl();
			employeeSkillsDelete.deleteEmployeeSkillsById(employeeId, removeDBSkills);
			retainDBSkills.retainAll(userSkills);
			removeUserSkills.removeAll(retainDBSkills);
			EmployeeDAO skillsToBeInserted = new EmployeeDAOImpl();
			skillsToBeInserted.insertEmployeeSkillsById(employeeId, removeUserSkills);
			updateSkills = true;

		}
		return updateSkills;
	}

	public LinkedHashSet<String> getEmployeeSkillsById(int id) {
		return employeeDAO.getEmployeeSkillsById(id);
	}

	public void deleteEmployeeSkillsById(int employeeId, LinkedHashSet<String> skills) {
		employeeDAO.deleteEmployeeSkillsById(employeeId, skills);
	}

	public int insertEmployeeSkillsById(int id, LinkedHashSet<String> skills) {
		int latestId = 0;
		EmployeeModel employee = new EmployeeModel();
		latestId = EmployeeDAOImpl.selectLatestIdFromEmployee(id);
		if (employee.getId() == 0) {
			id = latestId;
			employeeDAO.insertEmployeeSkillsById(id, skills);

		}
		return id;
	}

	public ArrayList<String> getEmployeeEmail() {
		return employeeDAO.getEmployeeEmail();
	}

	public boolean regexValidationForFirstName(String firstName) {
		if (!(Pattern.matches(regexForName, firstName) && Pattern.matches(regexForSize, firstName))) {
			return false;
		}
		return true;
	}

	public boolean regexValidationForLastName(String lastName) {
		if (!(Pattern.matches(regexForName, lastName) && Pattern.matches(regexForSize, lastName))) {
			return false;
		}
		return true;
	}

	public boolean regexValidationForAge(String age) {
		if (!Pattern.matches(regexForAge, age)) {
			return false;
		}
		return true;
	}

	public boolean regexValidationSalary(String salary) {
		if (salary == null || salary.isEmpty()) {
			return false;
		} else {
			if (!Pattern.matches(regexForSalary, salary)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean regexValidationEmail(String email) {
		System.out.println(Pattern.matches(regexForEmail, email));
		if (!Pattern.matches(regexForEmail, email)) {
			return false;
		}
		return true;
	}

	public List<EmployeeModel> getAllEmployee(Integer loginId) {
		return employeeDAO.getAllEmployee(loginId);
	}

}
