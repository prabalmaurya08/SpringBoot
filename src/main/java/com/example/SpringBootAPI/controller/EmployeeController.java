package com.example.SpringBootAPI.controller;

import com.example.SpringBootAPI.model.Employee;
import com.example.SpringBootAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    //Creating Repository object and calling the class
    @Autowired
    private EmployeeRepository employeeRepository;

    //creating a method
    @RequestMapping("/employees") //return all the employees
    private  List<Employee> getALlEmployee(){
        return employeeRepository.findAll();
    }

    //find employee by id
    @GetMapping(value = "/employees/{id}")
    private Employee getEmployeeById(@PathVariable  Long id){
        return employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));

    }


    //Add
    @PostMapping(value = "/employees")
    private Employee addEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //Update Employee
    @PutMapping(value = "/employees/{id}")
    private ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            // Fetch the existing employee by ID
            Employee existingEmployee = getEmployeeById(id);

            // Update the existing employee's details with the new values
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            // Update other fields as necessary

            // Save the updated employee back to the database
            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            // Return the updated employee with HTTP status OK
            return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            // If the employee doesn't exist, return a NOT_FOUND status
            return new ResponseEntity<String>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }



}
