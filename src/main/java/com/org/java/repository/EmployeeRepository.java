package com.org.java.repository;

import com.org.java.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e WHERE e.empid = :empid")
    Optional<Employee> findById(@Param("empid") Long empid);

    @Query("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2)")
    Employee findByMaxSalary();

    List<Employee> findByName(String name);

    List<Employee> findByNameAndAge(String name, int age);

    List<Employee> findByNameOrAge(String name, int age);

    List<Employee> findByAgeGreaterThan(int age);

    List<Employee> findByAgeLessThan(int age);

    List<Employee> findByAgeBetween(int startAge, int endAge);

    List<Employee> findByNameStartingWith(String prefix);

    List<Employee> findByNameEndingWith(String suffix);

    List<Employee> findByNameContaining(String infix);

    List<Employee> findByNameIgnoreCase(String name);

    List<Employee> findByNameIn(List<String> names);

    List<Employee> findByNameNotIn(List<String> names);

    List<Employee> findByNameAndWorkLocation(String name, String workLocation);

    List<Employee> findByNameOrWorkLocation(String name, String workLocation);

    List<Employee> findByWorkLocation(String workLocation);

    List<Employee> findBySalaryGreaterThan(double salary);

    List<Employee> findBySalaryLessThan(double salary);

    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

    List<Employee> findByAge(int age);

    List<Employee> findByEmail(String email);

    List<Employee> findByEmailContaining(String emailDomain);

    List<Employee> findByWorkLocationAndAge(String workLocation, int age);

    List<Employee> findByWorkLocationAndSalaryGreaterThan(String workLocation, double salary);

    List<Employee> findByPlatform(String platform);

    List<Employee> findByPlatformAndWorkLocation(String platform, String workLocation);

    List<Employee> findByProjectName(String projectName);

    List<Employee> findByProjectNameAndWorkLocation(String projectName, String workLocation);

    List<Employee> findByPanNumber(String panNumber);

    List<Employee> findByAddharNumber(Long addharNumber);

    List<Employee> findByMobbileNumber(Long mobbileNumber);

    List<Employee> findByNameAndSalary(String name, double salary);

    List<Employee> findByAgeAndSalary(int age, double salary);

    List<Employee> findByNameAndAgeAndSalary(String name, int age, double salary);

    List<Employee> findByNameAndPlatformAndWorkLocation(String name, String platform, String workLocation);


}

