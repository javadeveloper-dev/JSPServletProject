package employeecrudmaven.model;

import java.util.*;

public class EmployeeModel {
	private int id;
	public String firstname;
	private String lastname;
	private LinkedHashSet<String> skills = new LinkedHashSet<String>();
	private int age;
	private double salary;
	private String dateOfJoining;
	private String email;
	private String country;
	private int loginId;
	private byte[] profilePicture;
	private String filename;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LinkedHashSet<String> getSkills() {
		return skills;
	}

	public void setSkills(LinkedHashSet<String> skills) {
		this.skills = skills;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public EmployeeModel(int id, String firstname, String lastname, LinkedHashSet<String> skills, int age,
			double salary, String dateOfJoining, String email, String country, byte[] profilePicture, String filename) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.skills = skills;		//Constructor for geEmployeeById() of EmployeeDAOImpl class
		this.age = age;
		this.salary = salary;
		this.dateOfJoining = dateOfJoining;
		this.email = email;
		this.country = country;
		this.profilePicture = profilePicture;
		this.filename = filename;
	}
	
	public EmployeeModel(String firstname, String lastname, LinkedHashSet<String> skills, int age, double salary,
			String dateOfJoining, String email, String country, int loginId, String filename) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.skills = skills;
		this.age = age;
		this.salary = salary;					//Constructor for insertNewEmployee() of EmployeeController Class
		this.dateOfJoining = dateOfJoining;
		this.email = email;
		this.country = country;
		this.loginId = loginId;
		this.filename = filename;
	}
	
	public EmployeeModel(int id, String firstname, String lastname, LinkedHashSet<String> skills, int age,
			double salary, String dateOfJoining, String country, String email, byte[] profilePicture) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname; // Constructor for getAllEmployee() of EmployeeDAOImpl class
		this.skills = skills;
		this.age = age;
		this.salary = salary;
		this.dateOfJoining = dateOfJoining;
		this.country = country;
		this.email = email;
		this.profilePicture = profilePicture;
	}
	
	public EmployeeModel(int id, String firstname, String lastname, LinkedHashSet<String> skills, int age,
			double salary, String dateOfJoining, String country, String filename) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.skills = skills;
		this.age = age; // Constructor for updateEmployee() of Controller Class
		this.salary = salary;
		this.dateOfJoining = dateOfJoining;
		this.country = country;
		this.filename = filename;
	}
	
	public EmployeeModel(int id, LinkedHashSet<String> skills) {
		super();
		this.id = id;
		this.skills = skills;
	}
	
	public EmployeeModel() {
	}

}
