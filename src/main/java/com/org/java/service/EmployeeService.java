package com.org.java.service;

import com.org.java.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    public List<Employee> addEmployees(List<Employee> employee);

    public List<Employee> updateEmployee(List<Employee> employee);

    public List<Employee> getAllEmployess();

}
