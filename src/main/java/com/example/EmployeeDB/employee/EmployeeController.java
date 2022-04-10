package com.example.EmployeeDB.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //To define the controller class as a request handler
@RequestMapping(value = "/employees") //Maping the url for the employees
public class EmployeeController {

    //Reference to Service Class
    private final EmployeeService employeeService;
    
    //Constructor for Controller Class
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Retrieve all employees
    @GetMapping
    @RequestMapping(value = "/getEmployees") //Maping the url for the employee list
    public List<Employee> getEmployees()
    {
        return employeeService.getAllEmployees();
    }
    //Retrieve all employees
    @GetMapping
    @RequestMapping(value = "/getManagers") //Maping the url for the employee list
    public List<Manager> getManagers()
    {
        return employeeService.getAllManagers();
    }

    //Retrieve a single employee given an id
    @GetMapping
    @RequestMapping(value = "/getEmployee/{id}") //Maping the url for the employee list
    public Optional<Employee> getEmployeeById(@PathVariable("id") long id)
    {
        return employeeService.getEmployeeById(id);
    }
    

    //Add
    @PostMapping
    @RequestMapping(value = "/addEmployee")
    public String addEmployee(@RequestBody Employee employee) //Requestbody takes the objects of employee that were add at runtime in config and add it to employee
    {
        employeeService.addEmployee(employee);
        return "Employee Added";
    }

    //Delete
    @DeleteMapping
    @RequestMapping(value = "/delEmployee/{id}")
    public String delEmployee(@PathVariable("id") long id) //Requestbody takes the objects of employee that were add at runtime in config and add it to employee
    {
        employeeService.delEmployee(id);
        return "Employee Deleted";
    }

    //Update
    @PutMapping
    @RequestMapping(value = "/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) //Requestbody takes the objects of employee that were add at runtime in config and add it to employee
    {
        employeeService.updateEmployee(id, employee);
        return "Employee Updated";
    }
    

}
