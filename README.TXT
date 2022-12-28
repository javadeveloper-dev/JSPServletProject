Project Name is EmployeeCRUDMaven

In this project there are three databse tables 
1) Login Table
2) Employee Table
3) Employee Skills

Commands for Creating Tables.
1) First Creates the Login Table.
create table Login(Login_Id int(3) primary key auto_increment,Username varchar(10) not null,Password varchar(10) not null);

2)And Then Create the Employee Table .
create table Employee(id int(3) primary key not null auto_increment,firstname varchar(20) not null , lastname varchar(20) not null, age int(3) not null,salary double not null,doj varchar(10),country varchar(10),email varchar(254),image blob not null,filename varchar(20),Login_Id int(3) not null,Foreign key(Login_Id) references Login(Login_Id));

Login Table which connnected to Employee Table by Login Id.

3)And Then Create the Employee Skills Table.
create table Employee_skills(emp_id int(3),empskills varchar(20),foreign key(emp_id) references Employee(id)  on delete cascade on update cascade); 

Employee Table is Connect to Employee_skills table by id of Employee Table.
