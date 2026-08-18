package com.org.java.serviceimpl;

import com.org.java.dto.EmployeeDto;
import com.org.java.entity.Employee;
import com.org.java.exception.NoIdFoundException;
import com.org.java.mapper.EmployeeMapper;
import com.org.java.repository.EmployeeRepository;
import com.org.java.service.EmployeeService;
import com.org.java.util.EmailSender;
import com.org.java.util.ExcelGenerator;
import com.org.java.util.PdfGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public List<EmployeeDto> addEmployeeDetails(List<Employee> employee) {
        List<Employee> saved = employeeRepository.saveAll(employee);
        return saved.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> updateEmployeeDetails(List<EmployeeDto> employeeDto) {
        List<Employee> entities = employeeDto.stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeeDtotoEmployee)
                .collect(Collectors.toList());
        List<Employee> saved = employeeRepository.saveAll(entities);
        return saved.stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> fetchAllEmployeeDetails() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<EmployeeDto> findByEmployeeId(Long empid) {
        Optional<Employee> employee = employeeRepository.findById(empid);
        if (employee.isPresent()) {
            return Optional.ofNullable(EmployeeMapper.INSTANCE
                    .mapToEmployeetoEmployeeDTO(employee.get()));
        }
        throw new NoIdFoundException("Employee not found with id: ", "404");
    }

    @Override
    public EmployeeDto maxSalaryEmployeeDetails() {
        List<Employee> employees = employeeRepository.findAll();
        Employee maxSalaryEmp = employees.stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
        return EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(maxSalaryEmp);
    }

    @Override
    public EmployeeDto maxSalarywithQuery() {
        Employee maxSal = employeeRepository.findByMaxSalary();
        return EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(maxSal);
    }

    @Override
    public EmployeeDto minSalaryEmployeeDetails() {
        List<Employee> employees = employeeRepository.findAll();
        Employee minSalaryEmp = employees.stream().min(Comparator.comparingDouble(Employee::getSalary)).get();
        return EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(minSalaryEmp);
    }

    @Override
    public EmployeeDto secondHigestSalaryEmployeeDetails() {
        List<Employee> employees = employeeRepository.findAll();
        Employee secondHigestSalary = employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst().get();
        return EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(secondHigestSalary);
    }

    @Override
    public List<EmployeeDto> filterWithLimitSalaryEmployeeDetails() {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> filterSalaries = employees.stream().sorted((s1, s2) -> s1.getSalary() < s2.getSalary() ? -1 : s1.getSalary() > s2.getSalary() ? 1 : 0).filter(e -> e.getSalary() < 50000)
                .collect(Collectors.toList());
        return filterSalaries.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findAllSalaryAscOrder() {
        List<Employee> list = employeeRepository.findAll();
        List<Employee> ascSalries = list.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).collect(Collectors.toList());
        return ascSalries.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findAllSalaryDscOrder() {
        List<Employee> list = employeeRepository.findAll();
        list.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).collect(Collectors.toList());
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long findCountAllSalaries() {
        List<Employee> list = employeeRepository.findAll();
        long count = list.stream().mapToDouble(Employee::getSalary).summaryStatistics().getCount();
        return count;
    }

    @Override
    public double findSumAllSalaries() {
        List<Employee> list = employeeRepository.findAll();
        double sum = list.stream().mapToDouble(Employee::getSalary).summaryStatistics().getSum();
        return sum;
    }

    @Override
    public List<EmployeeDto> findLastSecondThridSalaries() {
        List<Employee> list = employeeRepository.findAll();
        List<Employee> salaries = list.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).skip(1).limit(3).collect(Collectors.toList());
        return salaries.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByName(String name) {
        List<Employee> namesList = employeeRepository.findByName(name);
        return namesList.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameAndAge(String name, int age) {
        List<Employee> employeeList = employeeRepository.findByNameAndAge(name, age);
        return employeeList.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameOrAge(String name, int age) {
        List<Employee> listEmp = employeeRepository.findByNameOrAge(name, age);
        return listEmp.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByAgeGreaterThan(int age) {
        List<Employee> list = employeeRepository.findByAgeGreaterThan(age);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByAgeLessThan(int age) {
        List<Employee> list = employeeRepository.findByAgeLessThan(age);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByAgeBetween(int startAge, int endAge) {
        List<Employee> employees = employeeRepository.findByAgeBetween(startAge, endAge);
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameStartingWith(String prefix) {
        List<Employee> list = employeeRepository.findByNameStartingWith(prefix);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameEndingWith(String suffix) {
        List<Employee> list = employeeRepository.findByNameEndingWith(suffix);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameContaining(String infix) {
        List<Employee> list = employeeRepository.findByNameContaining(infix);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameIgnoreCase(String name) {
        List<Employee> list = employeeRepository.findByNameIgnoreCase(name);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameIn(List<String> names) {
        List<Employee> list = employeeRepository.findByNameIn(names);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameNotIn(List<String> names) {
        List<Employee> list = employeeRepository.findByNameNotIn(names);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameAndWorkLocation(String name, String workLocation) {
        List<Employee> list = employeeRepository.findByNameAndWorkLocation(name, workLocation);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameOrWorkLocation(String name, String workLocation) {
        List<Employee> list = employeeRepository.findByNameOrWorkLocation(name, workLocation);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByWorkLocation(String workLocation) {
        List<Employee> list = employeeRepository.findByWorkLocation(workLocation);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findBySalaryGreaterThan(double salary) {
        List<Employee> list = employeeRepository.findBySalaryGreaterThan(salary);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findBySalaryLessThan(double salary) {
        List<Employee> list = employeeRepository.findBySalaryLessThan(salary);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findBySalaryBetween(double minSalary, double maxSalary) {
        List<Employee> list = employeeRepository.findBySalaryBetween(minSalary, maxSalary);
        return list.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByAge(int age) {
        List<Employee> employees = employeeRepository.findByAge(age);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDto> findByEmail(String email) {
        List<Employee> employees = employeeRepository.findByEmail(email);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .findFirst();
    }

    @Override
    public List<EmployeeDto> findByEmailContaining(String emailDomain) {
        List<Employee> employees = employeeRepository.findByEmailContaining(emailDomain);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByWorkLocationAndAge(String workLocation, int age) {
        List<Employee> employees = employeeRepository.findByWorkLocationAndAge(workLocation, age);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByWorkLocationAndSalaryGreaterThan(String workLocation, double salary) {
        List<Employee> employees = employeeRepository.findByWorkLocationAndSalaryGreaterThan(workLocation, salary);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByPlatform(String platform) {
        List<Employee> employees = employeeRepository.findByPlatform(platform);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByPlatformAndWorkLocation(String platform, String workLocation) {
        List<Employee> employees = employeeRepository.findByPlatformAndWorkLocation(platform, workLocation);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByProjectName(String projectName) {
        List<Employee> employees = employeeRepository.findByProjectName(projectName);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByProjectNameAndWorkLocation(String projectName, String workLocation) {
        List<Employee> employees = employeeRepository.findByProjectNameAndWorkLocation(projectName, workLocation);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByProjectNameAndPlatform(String projectName, String platform) {
        return List.of();
    }

    @Override
    public Optional<EmployeeDto> findByPanNumber(String panNumber) {
        List<Employee> employees = employeeRepository.findByPanNumber(panNumber);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .findFirst();
    }

    @Override
    public Optional<EmployeeDto> findByAddharNumber(Long addharNumber) {
        List<Employee> employees = employeeRepository.findByAddharNumber(addharNumber);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .findFirst();
    }

    @Override
    public Optional<EmployeeDto> findByMobbileNumber(Long mobbileNumber) {
        List<Employee> employees = employeeRepository.findByMobbileNumber(mobbileNumber);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .findFirst();
    }

    @Override
    public List<EmployeeDto> findByNameAndSalary(String name, double salary) {
        List<Employee> employees = employeeRepository.findByNameAndSalary(name, salary);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByAgeAndSalary(int age, double salary) {
        List<Employee> employees = employeeRepository.findByAgeAndSalary(age, salary);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameAndAgeAndSalary(String name, int age, double salary) {
        List<Employee> employees = employeeRepository.findByNameAndAgeAndSalary(name, age, salary);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByNameAndPlatformAndWorkLocation(String name, String platform, String workLocation) {
        List<Employee> employees = employeeRepository.findByNameAndPlatformAndWorkLocation(name, platform, workLocation);
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeId() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparingLong(Employee::getEmpid)).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeName() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeNameDsc() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getName).reversed()).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeSalary() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparingDouble(Employee::getSalary)).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeSalaryDsc() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeAge() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparingInt(Employee::getAge)).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmployeeAgeDsc() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparingInt(Employee::getAge).reversed()).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByEmail() {
        List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getEmail)).collect(Collectors.toList());
        return employees.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> sortByWorkLocation() {
        List<Employee> sortByWorkLocation=  employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getWorkLocation)).collect(Collectors.toList());
        return sortByWorkLocation.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public double findAverageSalary() {
        double salary = employeeRepository.findAll().stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
        return salary;
    }

    @Override
    public int findAverageAge() {
        int age = employeeRepository.findAll().stream().mapToInt(Employee::getAge).summaryStatistics().getAverage() > 0 ? (int) employeeRepository.findAll().stream().mapToInt(Employee::getAge).summaryStatistics().getAverage() : 0;
        return age;
    }

    @Override
    public List<String> findAllWorkLocations() {
        List<String> workLocations = employeeRepository.findAll().stream().map(Employee::getWorkLocation).distinct().collect(Collectors.toList());
        return workLocations;
    }

    @Override
    public List<String> findAllPlatforms() {
        List<String> allPlatforms = employeeRepository.findAll().stream().map(Employee::getPlatform).distinct().collect(Collectors.toList());
        return allPlatforms;
    }

    @Override
    public List<String> findAllProjectNames() {
        List<String> allProjects = employeeRepository.findAll().stream().map(Employee::getProjectName).distinct().collect(Collectors.toList());
        return allProjects;
    }

    @Override
    public long countEmployeesByWorkLocation(String workLocation) {
        long countWorkLocations = employeeRepository.findAll().stream().filter(e -> e.getWorkLocation().equals(workLocation)).count();
        return countWorkLocations;
    }

    @Override
    public long countEmployeesByPlatform(String platform) {
        long countPlatforms = employeeRepository.findAll().stream().filter(e -> e.getPlatform().equals(platform)).count();
        return countPlatforms;
    }

    @Override
    public long countEmployeesByProjectName(String projectName) {
        long countEmployeesByProject = employeeRepository.findAll().stream().filter(e -> e.getProjectName().equals(projectName)).count();
        return countEmployeesByProject;
    }

    @Override
    public List<EmployeeDto> findEmployeesByAgeAndSalaryRange(int startAge, int endAge, double minSalary, double maxSalary) {
        List<Employee> findEmployeesByAgeAndSalaryRange = employeeRepository.findAll().stream().filter(e -> e.getAge() >= startAge && e.getAge() <= endAge && e.getSalary() >= minSalary && e.getSalary() <= maxSalary).collect(Collectors.toList());
        return findEmployeesByAgeAndSalaryRange.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findTopNEmployeesByAge(int n) {
        List<Employee> findTopNEmployeesByAge = employeeRepository.findAll().stream().sorted(Comparator.comparingInt(Employee::getAge).reversed()).limit(n).collect(Collectors.toList());
        return findTopNEmployeesByAge.stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findTopNEmployeesBySalary(int n) {
        employeeRepository.findAll().stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).limit(n).collect(Collectors.toList());
        return employeeRepository.findAll().stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> mapNamesDeatails() {
        List<Employee> mapNamesDeatails = employeeRepository.findAll().stream().map(e -> {
            Employee emp = new Employee();
            emp.setName(e.getName());
            return emp;
        }).collect(Collectors.toList());
        return mapNamesDeatails;
    }

    @Override
    public List<String> mapNamesToUppercaseDeatails() {
        List<String> mapNamesToUppercaseDeatails = employeeRepository.findAll().stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList());
        return mapNamesToUppercaseDeatails;
    }

    @Override
    public Map<Character, Integer> findStringOccurenceDeatails() {

        String str = null;
        List<Employee> list = employeeRepository.findAll();
        List<String> names = list.stream().map(s1 -> s1.getName()).sorted().collect(Collectors.toList());
        System.out.println(names);
        for (String string : names) {
            if (string.equals("naveenkumar")) {
                str = "naveenkumar";
                break;
            }

        }
        Map<String, Long> map = Arrays.stream(str.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(map);

        char[] ch = str.toCharArray();
        Map<Character, Integer> map1 = new HashMap<Character, Integer>();
        for (Character char1 : ch) {
            if (map1.containsKey(char1)) {
                map1.put(char1, map1.get(char1) + 1);
            } else {
                map1.put(char1, +1);
            }

        }
        System.out.println(map);
        return map1;
    }

    @Override
    public Map<Object, List<Employee>> groupBySalaryDeatails() {
        Map<Double, List<Employee>> groupBySalaryDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getSalary));
        return Map.copyOf(groupBySalaryDeatails);
    }

    @Override
    public Map<Object, List<Employee>> groupByNamesDeatails() {
        Map<String, List<Employee>> groupByNamesDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getName));
        return Map.copyOf(groupByNamesDeatails);
    }

    @Override
    public Map<Object, List<Employee>> groupByWorkLocationDeatails() {
        Map<String, List<Employee>> groupByWorkLocationDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getWorkLocation));
        return Map.copyOf(groupByWorkLocationDeatails);
    }

    @Override
    public Map<Object, List<Employee>> groupByPlatformDeatails() {
        Map<Object, List<Employee>> groupByPlatformDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getPlatform));
        return Map.copyOf(groupByPlatformDeatails);
    }

    @Override
    public Map<Object, List<Employee>> groupByProjectNameDeatails() {
        Map<Object, List<Employee>> groupByProjectNameDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getProjectName));
        return Map.copyOf(groupByProjectNameDeatails);
    }

    @Override
    public Map<Object, List<Employee>> groupByAgeDeatails() {
        Map<Object, List<Employee>> groupByAgeDeatails = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getAge));
        return Map.copyOf(groupByAgeDeatails);
    }

    @Override
    public Map<Double, Long> groupBySalaryDeatailsWithCount() {
        Map<Double, Long> groupBySalaryDeatailsWithCount = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.counting()));
        return Map.copyOf(groupBySalaryDeatailsWithCount);
    }

    @Override
    public Map<String, Long> groupByNamesDeatailsWithCount() {
        Map<String, Long> groupByNamesDeatailsWithCount = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getName, Collectors.counting()));
        return Map.copyOf(groupByNamesDeatailsWithCount);
    }

    @Override
    public Map<String, Long> groupByWorkLocationDeatailsWithCount() {
        Map<String, Long> groupByWorkLocationDeatailsWithCount = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getWorkLocation, Collectors.counting()));
        return Map.copyOf(groupByWorkLocationDeatailsWithCount);
    }

    @Override
    public String firstnonRepeactedCharacterInStringDeatails() {
        String str = null;
        List<Employee> list = employeeRepository.findAll();
        List<String> names = list.stream().map(s1 -> s1.getName()).sorted().collect(Collectors.toList());
        for (String string : names) {
            if (string.equals("suresh")) {
                str = "suresh";
                break;
            }

        }
        String firstnonRepeated = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(s1 -> s1.getValue() == 1).findFirst().get().getKey();
        return firstnonRepeated;
    }

    @Override
    public String firstRepeactedCharacterInStringDeatails() {
        String str = null;
        List<Employee> list = employeeRepository.findAll();
        List<String> names = list.stream().map(s1 -> s1.getName()).sorted().collect(Collectors.toList());
        for (String string : names) {
            if (string.equals("suresh")) {
                str = "suresh";
                break;
            }

        }
        String firstRepeatedCharcater = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(s1 -> s1.getValue() > 1).findFirst().get().getKey();
        return firstRepeatedCharcater;
    }

    @Override
    public List<String> printDublicatesInStringDeatails() {
        String str = null;
        List<Employee> list = employeeRepository.findAll();
        List<String> names = list.stream().map(s1 -> s1.getName()).sorted().collect(Collectors.toList());
        for (String string : names) {
            if (string.equals("sreenivasarao")) {
                str = "sreenivasarao";
                break;
            }

        }
        List<String> dublicates = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(s1 -> s1.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return dublicates;
    }

    @Override
    public List<String> uniquerecordsInStringDeatails() {
        String str = null;
        List<Employee> list = employeeRepository.findAll();
        List<String> names = list.stream().map(s1 -> s1.getName()).sorted().collect(Collectors.toList());
        for (String string : names) {
            if (string.equals("sreenivasarao")) {
                str = "sreenivasarao";
                break;
            }

        }
        List<String> uniq = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(s1 -> s1.getValue() == 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return uniq;
    }

    @Override
    public String longestStringDeatails() {
        List<Employee> list = employeeRepository.findAll();
        List<String> stringnames = list.stream().map(s1 -> s1.getName()).collect(Collectors.toList());
        String longestString = stringnames.stream()
                .reduce((word1, word2) -> word1.length() > word2.length() ? word1 : word2).get();

        return longestString;
    }

    @Override
    public String smallestStringDeatails() {
        List<Employee> list = employeeRepository.findAll();
        List<String> stringnames = list.stream().map(s1 -> s1.getName()).collect(Collectors.toList());
        String smallestString = stringnames.stream()
                .reduce((word1, word2) -> word2.length() > word1.length() ? word1 : word2).get();

        return smallestString;
    }

    @Override
    public List<String> filterDepartmentIdsDeatails() {
       /* List<Employee> list = employeeRepository.findAll();
        List<Integer> deptIds = list.stream().map(s1 -> s1.getDepartmentId()).collect(Collectors.toList());
        List<String> depts = deptIds.stream().map(s1 -> s1 + "").filter(s2 -> s2.startsWith("2"))
                .collect(Collectors.toList());
        return depts;*/
        return List.of();
    }

    @Override
    public String stringReverseJava8Deatails() {
        String str = "SREENIVASARAO";
        String reverse = Arrays.asList(str).stream().map(s -> new StringBuilder(s).reverse().toString())
                .collect(Collectors.toList()).get(0);
        return reverse;
    }

    @Override
    public List<Employee> indexRangesDeatails(int fromIndex, int toIndex) {
        return  employeeRepository.findAll().stream().skip(fromIndex).limit(toIndex - fromIndex).collect(Collectors.toList());

    }

    @Override
    public String joiningNamesDeatails() {
        List<Employee> list = employeeRepository.findAll();
        String joinNames = list.stream().map(s1 -> s1.getName()).collect(Collectors.joining(","));
        return joinNames;
    }

    @Override
    public Set<Employee> listToSetCoversion() {
        Set<Employee> listToSetCoversion = employeeRepository.findAll().stream().collect(Collectors.toSet());
        return listToSetCoversion;
    }

    @Override
    public Map<Long, Employee> listToMapCoversion() {
        List<Employee> list = employeeRepository.findAll();
        Map<Long, Employee> listToMapConversion = list.stream()
                .collect(Collectors.toMap(Employee::getEmpid, Function.identity()));
        return listToMapConversion;
    }

    @Override
    public List<Employee> setToListConversion() {
        List<Employee> setToListConversion = employeeRepository.findAll().stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        return setToListConversion;
    }

    @Override
    public Map<Long, Employee> setToMapConversionDetails() {
        List<Employee> list = employeeRepository.findAll();
        Set<Employee> set = list.stream().collect(Collectors.toSet());
        // TODO Auto-generated method stub
        Map<Long, Employee> setToMap = set.stream()
                .collect(Collectors.toMap(Employee::getEmpid, Function.identity()));
        return setToMap;
    }

    @Override
    public List<Map.Entry<Long, Employee>> mapToListConversionDetails() {
        List<Employee> list = employeeRepository.findAll();
        Map<Long, Employee> listToMapConversion = list.stream()
                .collect(Collectors.toMap(Employee::getEmpid, Function.identity()));
        List<Map.Entry<Long, Employee>> mapToList = listToMapConversion.entrySet().stream().collect(Collectors.toList());
        return mapToList;
    }

    @Override
    public Set<Map.Entry<Long, Employee>> mapToSetConversionDetails() {
        List<Employee> list = employeeRepository.findAll();
        Map<Long, Employee> listToMapConversion = list.stream()
                .collect(Collectors.toMap(Employee::getEmpid, Function.identity()));
        Set<Map.Entry<Long, Employee>> mapToSet = listToMapConversion.entrySet().stream().collect(Collectors.toSet());
        return mapToSet;
    }


    @Override
    public String leftRotationStringDeatails() {
        String originalString = "sreenivasarao";
        int rotateCharacters = 4;
        String leftRotation = originalString.substring(rotateCharacters) + originalString.substring(0, rotateCharacters);
        return leftRotation;
    }

    @Override
    public String rightRotationStringDeatails() {
        String originalString = "sreenivasarao";
        int rotatechar = 3;
        int partion = originalString.length() - rotatechar;
        String rightRotation = originalString.substring(partion) + originalString.substring(0, partion);
        return rightRotation;
    }

    @Override
    public List<EmployeeDto> findByEmployeeBetweenSalaryDeatails() {
        List<Employee> list = employeeRepository.findAll();
        List<Employee> empList = list.stream().filter(s1 -> s1.getSalary() > 65000 & s1.getSalary() < 90000).collect(Collectors.toList());
        List<EmployeeDto> empDtoList = empList.stream().map(s1 -> EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(s1)).collect(Collectors.toList());
        return empDtoList;
    }

    @Override
    public Map<String, Long> findBygroupCountDeatails() {
        List<Employee> list = employeeRepository.findAll();
        List<EmployeeDto> dtoList = list.stream().map(s1 -> EmployeeMapper.INSTANCE.mapToEmployeetoEmployeeDTO(s1)).collect(Collectors.toList());
        Map<String, Long> platformDeatils = dtoList.stream().collect(Collectors.groupingBy(EmployeeDto::getPlatform, Collectors.counting()));
        return platformDeatils;
    }

    @Override
    public Page<Employee> getEmployeesList(int page, int size) {
        Page<Employee> page1 = employeeRepository.findAll(PageRequest.of(page, size));
        return page1;
    }

    @Override
    public Map<String, List<Employee>> groupByWorkLocationWithEmployeesDeatails() {
        Map<String, List<Employee>> groupWorkLocations = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getWorkLocation));
        return Map.copyOf(groupWorkLocations);
    }


    @Override
    public Map<Double, List<Employee>> groupBySalaryWithEmployeesDeatails() {
        Map<Double, List<Employee>> groupSalary = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getSalary));
        return Map.copyOf(groupSalary);
    }

    @Override
    public Map<String, Long> countByWorkLocationDeatails() {
        Map<String, Long> countWorkLocations = employeeRepository.findAll().stream().collect(Collectors.groupingBy(Employee::getWorkLocation, Collectors.counting()));
        return Map.copyOf(countWorkLocations);
    }

    @Override
    public byte[] pdfGenerator() {
        List<EmployeeDto> pdfGenerator= employeeRepository.findAll().stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO).collect(Collectors.toList());
        byte[] pdf = PdfGenerator.generateEmployeesPdf(pdfGenerator);
        return pdf;
    }

    @Override
    public byte[] excelGenerator() {
        List<EmployeeDto> excelGenerator= employeeRepository.findAll().stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO).collect(Collectors.toList());
        byte[] excel= ExcelGenerator.generateEmployeesExcel(excelGenerator);
        return excel;
    }

    @Override
    public ResponseEntity<String> sendEmailWithPdfAttachment(String to) {
        try{
            List<EmployeeDto> employeeDtos = employeeRepository.findAll().stream().map(EmployeeMapper.INSTANCE::mapToEmployeetoEmployeeDTO).collect(Collectors.toList());
            byte[] pdf = PdfGenerator.generateEmployeesPdf(employeeDtos);
            emailSender.sendPdf(to, "Employee Details", "Please find the attached PDF with employee details.", pdf, "employee_details.pdf");
            return ResponseEntity.ok("Email sent successfully with PDF attachment.");
        } catch (
                MessagingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Email sending failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }

    }
    // Add to EmployeeServiceImpl


}