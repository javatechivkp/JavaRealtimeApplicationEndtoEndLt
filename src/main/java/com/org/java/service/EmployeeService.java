package com.org.java.service;

import com.org.java.dto.EmployeeDto;
import com.org.java.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public interface EmployeeService {

    List<EmployeeDto> addEmployeeDetails(List<Employee> employee);

    List<EmployeeDto> updateEmployeeDetails(List<EmployeeDto> employeesDto);

    List<EmployeeDto> fetchAllEmployeeDetails();

    Optional<EmployeeDto> findByEmployeeId(Long empid);

    EmployeeDto maxSalaryEmployeeDetails();

    EmployeeDto maxSalarywithQuery();

    EmployeeDto minSalaryEmployeeDetails();

    EmployeeDto secondHigestSalaryEmployeeDetails();

    List<EmployeeDto> filterWithLimitSalaryEmployeeDetails();

    List<EmployeeDto> findAllSalaryAscOrder();

    List<EmployeeDto> findAllSalaryDscOrder();

    Long findCountAllSalaries();

    double findSumAllSalaries();

    List<EmployeeDto> findLastSecondThridSalaries();

    List<EmployeeDto> findByName(String name);

    List<EmployeeDto> findByNameAndAge(String name, int age);

    List<EmployeeDto> findByNameOrAge(String name, int age);

    List<EmployeeDto> findByAgeGreaterThan(int age);

    List<EmployeeDto> findByAgeLessThan(int age);

    List<EmployeeDto> findByAgeBetween(int startAge, int endAge);

    List<EmployeeDto> findByNameStartingWith(String prefix);

    List<EmployeeDto> findByNameEndingWith(String suffix);

    List<EmployeeDto> findByNameContaining(String infix);

    List<EmployeeDto> findByNameIgnoreCase(String name);

    List<EmployeeDto> findByNameIn(List<String> names);

    List<EmployeeDto> findByNameNotIn(List<String> names);

    List<EmployeeDto> findByNameAndWorkLocation(String name, String workLocation);

    List<EmployeeDto> findByNameOrWorkLocation(String name, String workLocation);

    List<EmployeeDto> findByWorkLocation(String workLocation);

    List<EmployeeDto> findBySalaryGreaterThan(double salary);

    List<EmployeeDto> findBySalaryLessThan(double salary);

    List<EmployeeDto> findBySalaryBetween(double minSalary, double maxSalary);

    List<EmployeeDto> findByAge(int age);

    // ============ EMAIL RELATED ============
    Optional<EmployeeDto> findByEmail(String email);

    List<EmployeeDto> findByEmailContaining(String emailDomain);

    List<EmployeeDto> findByWorkLocationAndAge(String workLocation, int age);

    List<EmployeeDto> findByWorkLocationAndSalaryGreaterThan(String workLocation, double salary);

    List<EmployeeDto> findByPlatform(String platform);

    List<EmployeeDto> findByPlatformAndWorkLocation(String platform, String workLocation);

    // ============ PROJECT RELATED ============
    List<EmployeeDto> findByProjectName(String projectName);

    List<EmployeeDto> findByProjectNameAndWorkLocation(String projectName, String workLocation);

    List<EmployeeDto> findByProjectNameAndPlatform(String projectName, String platform);

    // ============ DOCUMENT/ID RELATED ============
    Optional<EmployeeDto> findByPanNumber(String panNumber);

    Optional<EmployeeDto> findByAddharNumber(Long addharNumber);

    Optional<EmployeeDto> findByMobbileNumber(Long mobbileNumber);

    // ============ COMBINED QUERIES ============

    List<EmployeeDto> findByNameAndSalary(String name, double salary);

    List<EmployeeDto> findByAgeAndSalary(int age, double salary);

    List<EmployeeDto> findByNameAndAgeAndSalary(String name, int age, double salary);

    List<EmployeeDto> findByNameAndPlatformAndWorkLocation(String name, String platform, String workLocation);

    // ============ SORTING METHODS ============
    List<EmployeeDto> sortByEmployeeId();

    List<EmployeeDto> sortByEmployeeName();

    List<EmployeeDto> sortByEmployeeNameDsc();

    List<EmployeeDto> sortByEmployeeSalary();

    List<EmployeeDto> sortByEmployeeSalaryDsc();

    List<EmployeeDto> sortByEmployeeAge();

    List<EmployeeDto> sortByEmployeeAgeDsc();

    List<EmployeeDto> sortByEmail();

    List<EmployeeDto> sortByWorkLocation();

    // ============ AGGREGATION/STATISTICS ============
    double findAverageSalary();

    int findAverageAge();

    List<String> findAllWorkLocations();

    List<String> findAllPlatforms();

    List<String> findAllProjectNames();

    long countEmployeesByWorkLocation(String workLocation);

    long countEmployeesByPlatform(String platform);

    long countEmployeesByProjectName(String projectName);

    // ============ FILTERING & PAGINATION ============
    List<EmployeeDto> findEmployeesByAgeAndSalaryRange(int startAge, int endAge, double minSalary, double maxSalary);

    List<EmployeeDto> findTopNEmployeesByAge(int n);

    List<EmployeeDto> findTopNEmployeesBySalary(int n);

    List<Employee> mapNamesDeatails();

    List<String> mapNamesToUppercaseDeatails();

    Map<Character, Integer> findStringOccurenceDeatails();

    Map<Object, List<Employee>> groupBySalaryDeatails();

    Map<Object, List<Employee>> groupByNamesDeatails();

    Map<Object, List<Employee>> groupByWorkLocationDeatails();

    Map<Object, List<Employee>> groupByPlatformDeatails();

    Map<Object, List<Employee>> groupByProjectNameDeatails();

    Map<Object, List<Employee>> groupByAgeDeatails();

    Map<Double, Long> groupBySalaryDeatailsWithCount();

    Map<String, Long> groupByNamesDeatailsWithCount();

    Map<String, Long> groupByWorkLocationDeatailsWithCount();


    String firstnonRepeactedCharacterInStringDeatails();

    String firstRepeactedCharacterInStringDeatails();

    List<String> printDublicatesInStringDeatails();

    List<String> uniquerecordsInStringDeatails();

    String longestStringDeatails();

    String smallestStringDeatails();

    List<String> filterDepartmentIdsDeatails();

    String stringReverseJava8Deatails();


    List<Employee> indexRangesDeatails(int fromIndex, int toIndex);

    String joiningNamesDeatails();

    Set<Employee> listToSetCoversion();

    Map<Long, Employee> listToMapCoversion();

    List<Employee> setToListConversion();

    Map<Long, Employee> setToMapConversionDetails();

    List<Map.Entry<Long, Employee>> mapToListConversionDetails();

    Set<Map.Entry<Long, Employee>> mapToSetConversionDetails();


    String leftRotationStringDeatails();

    String rightRotationStringDeatails();

    List<EmployeeDto> findByEmployeeBetweenSalaryDeatails();

    Map<String, Long> findBygroupCountDeatails();

    public Page<Employee> getEmployeesList(int page, int size);

    Map<String, List<Employee>> groupByWorkLocationWithEmployeesDeatails();


    Map<Double, List<Employee>> groupBySalaryWithEmployeesDeatails();

    Map<String, Long> countByWorkLocationDeatails();

    byte[] pdfGenerator();

    byte[] excelGenerator();

    ResponseEntity<String> sendEmailWithPdfAttachment(String to);




}