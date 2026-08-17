package com.org.java.controller;

import com.org.java.entity.Employee;
import com.org.java.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployess();
    }

    @PostMapping("/add")
    public List<Employee> addEmployees(@RequestBody List<Employee> employees) {
        return employeeService.addEmployees(employees);
    }

    @PutMapping("/update")
    public List<Employee> updateEmployees(@RequestBody List<Employee> employees) {
        return employeeService.updateEmployee(employees);
    }
    @GetMapping("/welcome")
    public String welocmeTest(){
        return "AWS FINAL CI/CD TESTING IT IS WORKING OPR NOT.IF WORKING AWS PIPILINE USING SPRINGBOOT APPLICATIONS";
    }
}