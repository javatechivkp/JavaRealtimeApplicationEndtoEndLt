package com.org.java.serviceimpl;

import com.org.java.entity.Employee;
import com.org.java.repository.EmployeeRepository;
import com.org.java.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> addEmployees(List<Employee> employee) {
       List<Employee> list= employeeRepository.saveAll(employee);
       return list;
    }

    @Override
    public List<Employee> updateEmployee(List<Employee> employee) {
        List<Employee> list= employeeRepository.saveAll(employee);
        return list;
    }

    @Override
    public List<Employee> getAllEmployess() {
        List<Employee> list=employeeRepository.findAll();
        return list;
    }
}
