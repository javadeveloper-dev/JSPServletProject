package employeecrudmaven.dao;



import java.io.FileInputStream;

import java.util.*;

import employeecrudmaven.model.EmployeeModel;

public interface EmployeeDAO {


	public static final String INSERT_EMPLOYEE_SQL = "INSERT INTO Employee"
			+ "(firstname,lastname,age,salary,doj,email)Values" + "(?,?,?,?,?,?)"; 

	// Queries for Employee Table
	public static final String INSERT = "INSERT INTO Employee"
			+ "(firstname,lastname,age,salary,doj,country,email,image,Login_Id,filename)Values" + "(?,?,?,?,?,?,?,?,?,?)";


	public static final String SELECT_BY_ID = "select * from Employee where id=?";


	public static final String SELECT_ALL_EMPLOYEE = "select * from Employee ";
	
	public static final String SELECT_LATEST_ID_FROM_EMPLOYEE_SQL = "select id from Employee order by id desc limit 1 ";
	
	public static final String SELECT_ALL_EMAIL_FROM_EMPLOYEE_SQL="select email from Employee";
	
	public static final String SELECT_EMPLOYEE_EMAIL_BY_ID="select email from Employee where id=?";
	
	public static final String DELETE_EMPLOYEE_BY_ID = "delete from Employee where id=?"; 

	public static final String UPDATE_EMPLOYEE_SQL = "update Employee set firstname=?,lastname=?,age=?,salary=?,doj=?,email=? where id=?"; 

	public static final String SELECT_LOGIN_ID = "select * From Employee where Login_Id=? ";

	public static final String SELECT_LATEST_ID = "select id from Employee order by id desc limit 1 ";


	public static final String DELETE_BY_ID = "delete from Employee where id=?";

	public static final String UPDATE_SQL = "update Employee set firstname=?,lastname=?,age=?,salary=?,doj=?,country=?,image=?,fileName=? where id=?";
	
	// Queries for employee_skill table -
	public static final String INSERT_SKILLS = "INSERT INTO Employee_skills" + "(emp_id,empskills)Values"
			+ "(?,?)";

	public static final String DELETE_SKILLS = "delete from Employee_skills where emp_id=? AND empskills=?  ";

	public static final String SELECT_SKILL_BY_ID = "select * from employee_skills where emp_id=?"; //

	// Methods for Employee Table
	public void insertEmployee(EmployeeModel employee,FileInputStream fileInputStream);

	public EmployeeModel getEmployeeById(int id);
	
	public  ArrayList<String> getEmployeeEmail();
 
	public boolean deleteEmployee(int id) throws Exception;
	
	//Methods For Employee_Skill Table

	public List<EmployeeModel> getAllEmployee(int loginId);

	public boolean updateEmployee(EmployeeModel employee,FileInputStream fileInputStream);


	// Methods For Employee_Skill Table
	public int insertEmployeeSkillsById(int id, LinkedHashSet<String> skills);

	public void deleteEmployeeSkillsById(int id, LinkedHashSet<String> skills);

	public LinkedHashSet<String> getEmployeeSkillsById(int id);

	boolean updateEmployee(EmployeeModel employee);

	int selectLatestIdFromEmployee();

}
