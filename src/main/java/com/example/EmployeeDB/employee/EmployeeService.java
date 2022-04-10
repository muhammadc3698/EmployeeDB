package com.example.EmployeeDB.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.EmployeeDB.exceptions.RequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //To tell springboot this is the class that need to be auto instantiated
public class EmployeeService 
{
    //Reference to Repository Interface
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;


    //Cities the comapny has offices in
    ArrayList<String> city = new ArrayList<String>() {
        {
            add("baltimore");
            add("washington Dc");
            add("hunt valley");
        }
    };
    //Departments within the company
    ArrayList<String> dept = new ArrayList<String>() {
        {
            add("backend");
            add("devops");
            add("marketing");
            add("human resources");
        }
    };
    //Job titles in the company
    ArrayList<String> jobTitle = new ArrayList<String>() {
        {
            add("frontend developer");
            add("backend developer");
            add("fullstack developer");
            add("team leader");
            add("manager");
        }
    };

    public EmployeeService() 
    {

    }

    //Constructor for Service Class
    public EmployeeService(EmployeeRepository employeeRepository) 
    {
        this.employeeRepository = employeeRepository;
    }
    //Constructor for Service Class
    public EmployeeService(ManagerRepository managerRepository) 
    {
        this.managerRepository = managerRepository;
    }

    //Returns a list of all employees in the database
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    //Returns a list of all employees in the database
    public List<Manager> getAllManagers()
    {
        return managerRepository.findAll();
    }

    //Returns a employee in the database given an id
    public Optional<Employee> getEmployeeById(long id)
    {
        if(!employeeRepository.findById(id).isPresent())
        {
            throw new RequestException("Id Does Not Exist");
        }
        return employeeRepository.findById(id);
    }


    //Given an employee add it to the data base
    public void addEmployee(Employee employee) 
    {
        if(employeeRepository.findById(employee.getId()).isPresent())
        {
            throw new RequestException("Id Already Taken");
            // throw new IllegalStateException("Id Already Taken");
        }
        if(employeeRepository.findByEmail(employee.getEmail()).isPresent())
        {
            throw new RequestException("Email Already Taken");
        }
        if(employeeRepository.findByPhone(employee.getPhone()).isPresent())
        {
            throw new RequestException("Phone Number Already Taken");
        }
        //Makes sure the end date is after the start date 
        if(employee.getEndDate() != null && employee.getEndDate().isBefore(employee.getStartDate()))
        {
            throw new RequestException("Employee End Date must be after the start date.");
        }
        //Check to see if the department, city and jobtitle match existing ones
        if(!city.contains(employee.getCity().toLowerCase()))
        {
            throw new RequestException("City Must be one of the cities the company is located in. Cities List: " + city);
        }
        if(!dept.contains(employee.getDepartment().toLowerCase()))
        {
            throw new RequestException("Department must be one of the departments in the company. Departments List: " + dept);
        }
        if(!jobTitle.contains(employee.getJobtitle().toLowerCase()))
        {
            throw new RequestException("Job title must be one of the Job titles in the company. Job Titles List: " + jobTitle);
        }
        
        employeeRepository.save(employee);
        if(employee.getJobtitle().toLowerCase() == "manager")
        {
            Manager manager = new Manager(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhone());
            managerRepository.save(manager);
        }
    }

    //Given an employee id delete it from the data base
    public void delEmployee(Long id) 
    {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        {
            throw new RequestException("Id Does Not Exist");
        }
        

        employeeRepository.delete(employee.get());
        //Checks to see if the emplyee was also a manager
        if(employee.get().getJobtitle().toLowerCase() == "manager")
        {
            Optional<Manager> manager = managerRepository.findById(id);
            managerRepository.delete(manager.get());
        }
    }

    //Given an employee id update the information
    public void updateEmployee(Long id, Employee updatedEmployee) 
    {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        {
            throw new RequestException("Id Does Not Exist");
        }

        //Make sure the ph# and email is different from the ones already on the database
        Optional<Employee> empEmail = employeeRepository.findByEmail(updatedEmployee.getEmail());
        Optional<Employee> empPhone = employeeRepository.findByPhone(updatedEmployee.getPhone());
        if(empEmail.isPresent() && empEmail.get().getId() != id)
        {
            throw new RequestException("Email Already Exisits.");
        }
        if(empPhone.isPresent() && empPhone.get().getId() != id)
        {
            throw new RequestException("Phone Number Already Exists.");
        }
        //Makes sure the end date is after the start date 
        if(updatedEmployee.getEndDate() != null && updatedEmployee.getEndDate().isBefore(updatedEmployee.getStartDate()))
        {
            throw new RequestException("Employee End Date must be after the start date.");
        }

        //Check to see if the department, city and jobtitle match existing ones
        if(!city.contains(updatedEmployee.getCity().toLowerCase()))
        {
            throw new RequestException("City Must be one of the cities the company is located in. Cities List: " + city);
        }
        if(!dept.contains(updatedEmployee.getDepartment().toLowerCase()))
        {
            throw new RequestException("Department must be one of the departments in the company. Departments List: " + dept);
        }
        if(!jobTitle.contains(updatedEmployee.getJobtitle().toLowerCase()))
        {
            throw new RequestException("Job title must be one of the Job titles in the company. Job Titles List: " + jobTitle);
        }


        Employee currEmployee = employee.get();
        currEmployee.setFirstName(updatedEmployee.getFirstName());
        currEmployee.setLastName(updatedEmployee.getLastName());
        currEmployee.setEmail(updatedEmployee.getEmail());
        currEmployee.setPhone(updatedEmployee.getPhone());
        currEmployee.setDob(updatedEmployee.getDob());
        currEmployee.setJobtitle(updatedEmployee.getJobtitle());
        currEmployee.setDepartment(updatedEmployee.getDepartment());
        currEmployee.setCity(updatedEmployee.getCity());
        currEmployee.setStartDate(updatedEmployee.getStartDate());
        currEmployee.setEndDate(updatedEmployee.getEndDate());
        currEmployee.setReportingManager(updatedEmployee.getReportingManager());
        employeeRepository.save(currEmployee);

        //Updates manager list
        if(updatedEmployee.getJobtitle().toLowerCase() == "manager")
        {
            //If the manager already exists update/else add a new manager
            if(managerRepository.findById(id).isPresent())
            {
                Optional<Manager> manager = managerRepository.findById(id);
                manager.get().setEmail(updatedEmployee.getEmail());
                manager.get().setPhone(updatedEmployee.getPhone());
                manager.get().setFirstName(updatedEmployee.getFirstName());
                manager.get().setLastName(updatedEmployee.getLastName());
            }
            else
            {
                Manager manager = new Manager(updatedEmployee.getId(), updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getEmail(), updatedEmployee.getPhone());
                managerRepository.save(manager);
            }
        }
    }



}
