package com.org.java.controller;

import com.org.java.dto.EmployeeDto;
import com.org.java.entity.Employee;
import com.org.java.service.EmployeeService;
import com.org.java.designpatteran.SingletonDesignPattarean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/welcome")
    public String welcomeTest(){
        return "THIS IS FOR AWS NEW TEST ENDTOEND LOADED";
    }

    // ============ CRUD OPERATIONS ============
    @PostMapping("/add")
    public ResponseEntity<List<EmployeeDto>> addEmployeeDetails(@RequestBody List<Employee> employee) {
        List<EmployeeDto> list = employeeService.addEmployeeDetails(employee);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<List<EmployeeDto>> updateEmployeeDetails(@RequestBody List<EmployeeDto> employee) {
        List<EmployeeDto> list = employeeService.updateEmployeeDetails(employee);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EmployeeDto>> fetchAllEmployeeDetails() {
        List<EmployeeDto> list = employeeService.fetchAllEmployeeDetails();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findById/{empid}")
    public ResponseEntity<Optional<EmployeeDto>> findByEmployeeId(@PathVariable("empid") Long empid) {
        Optional<EmployeeDto> employeeDto = employeeService.findByEmployeeId(empid);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ SALARY RELATED ============
    @GetMapping("/maxSalary")
    public ResponseEntity<EmployeeDto> maxSalaryEmployeeDetails() {
        EmployeeDto employeeDto = employeeService.maxSalaryEmployeeDetails();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/minSalary")
    public ResponseEntity<EmployeeDto> minSalaryEmployeeDetails() {
        EmployeeDto employeeDto = employeeService.minSalaryEmployeeDetails();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/secondHighestSalary")
    public ResponseEntity<EmployeeDto> secondHighestSalaryEmployeeDetails() {
        EmployeeDto employeeDto = employeeService.secondHigestSalaryEmployeeDetails();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/filterWithLimitSalary")
    public ResponseEntity<List<EmployeeDto>> filterWithLimitSalaryEmployeeDetails() {
        List<EmployeeDto> employeeDto = employeeService.filterWithLimitSalaryEmployeeDetails();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/maxSalaryWithQuery")
    public ResponseEntity<EmployeeDto> maxSalarywithQuery() {
        EmployeeDto employeeDto = employeeService.maxSalarywithQuery();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findAllAscSalaries")
    public ResponseEntity<List<EmployeeDto>> findAllSalaryAscOrder() {
        List<EmployeeDto> employeeDto = employeeService.findAllSalaryAscOrder();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findAllDscSalaries")
    public ResponseEntity<List<EmployeeDto>> findAllSalaryDscOrder() {
        List<EmployeeDto> employeeDto = employeeService.findAllSalaryDscOrder();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/countAllSalaries")
    public ResponseEntity<Long> findCountAllSalaries() {
        Long count = employeeService.findCountAllSalaries();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/findSumSalaries")
    public ResponseEntity<Double> findSumAllSalaries() {
        double sum = employeeService.findSumAllSalaries();
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping("/filterSal")
    public ResponseEntity<List<EmployeeDto>> findLastSecondThirdSalaries() {
        List<EmployeeDto> employeeDto = employeeService.findLastSecondThridSalaries();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ SALARY RANGE QUERIES ============
    @GetMapping("/findBySalaryGreaterThan")
    public ResponseEntity<List<EmployeeDto>> findBySalaryGreaterThan(@RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findBySalaryGreaterThan(salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findBySalaryLessThan")
    public ResponseEntity<List<EmployeeDto>> findBySalaryLessThan(@RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findBySalaryLessThan(salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findBySalaryBetween")
    public ResponseEntity<List<EmployeeDto>> findBySalaryBetween(@RequestParam("minSalary") double minSalary, @RequestParam("maxSalary") double maxSalary) {
        List<EmployeeDto> employeeDto = employeeService.findBySalaryBetween(minSalary, maxSalary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ NAME RELATED ============
    @GetMapping("/findByName")
    public ResponseEntity<List<EmployeeDto>> findByName(@RequestParam("name") String name) {
        List<EmployeeDto> employeeDto = employeeService.findByName(name);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameStartingWith")
    public ResponseEntity<List<EmployeeDto>> findByNameStartingWith(@RequestParam("prefix") String prefix) {
        List<EmployeeDto> employeeDto = employeeService.findByNameStartingWith(prefix);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameEndingWith")
    public ResponseEntity<List<EmployeeDto>> findByNameEndingWith(@RequestParam("suffix") String suffix) {
        List<EmployeeDto> employeeDto = employeeService.findByNameEndingWith(suffix);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameContaining")
    public ResponseEntity<List<EmployeeDto>> findByNameContaining(@RequestParam("infix") String infix) {
        List<EmployeeDto> employeeDto = employeeService.findByNameContaining(infix);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameIgnoreCase")
    public ResponseEntity<List<EmployeeDto>> findByNameIgnoreCase(@RequestParam("name") String name) {
        List<EmployeeDto> employeeDto = employeeService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameIn")
    public ResponseEntity<List<EmployeeDto>> findByNameIn(@RequestParam("names") List<String> names) {
        List<EmployeeDto> employeeDto = employeeService.findByNameIn(names);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameNotIn")
    public ResponseEntity<List<EmployeeDto>> findByNameNotIn(@RequestParam("names") List<String> names) {
        List<EmployeeDto> employeeDto = employeeService.findByNameNotIn(names);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ AGE RELATED ============
    @GetMapping("/findByAgeGreaterThan")
    public ResponseEntity<List<EmployeeDto>> findByAgeGreaterThan(@RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByAgeGreaterThan(age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByAgeLessThan")
    public ResponseEntity<List<EmployeeDto>> findByAgeLessThan(@RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByAgeLessThan(age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByAgeBetween")
    public ResponseEntity<List<EmployeeDto>> findByAgeBetween(@RequestParam("startAge") int startAge, @RequestParam("endAge") int endAge) {
        List<EmployeeDto> employeeDto = employeeService.findByAgeBetween(startAge, endAge);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByAge")
    public ResponseEntity<List<EmployeeDto>> findByAge(@RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByAge(age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ EMAIL RELATED ============
    @GetMapping("/findByEmail")
    public ResponseEntity<Optional<EmployeeDto>> findByEmail(@RequestParam("email") String email) {
        Optional<EmployeeDto> employeeDto = employeeService.findByEmail(email);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByEmailContaining")
    public ResponseEntity<List<EmployeeDto>> findByEmailContaining(@RequestParam("emailDomain") String emailDomain) {
        List<EmployeeDto> employeeDto = employeeService.findByEmailContaining(emailDomain);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ WORK LOCATION RELATED ============
    @GetMapping("/findByWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByWorkLocation(@RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByWorkLocation(workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameAndWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByNameAndWorkLocation(@RequestParam("name") String name, @RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByNameAndWorkLocation(name, workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameOrWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByNameOrWorkLocation(@RequestParam("name") String name, @RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByNameOrWorkLocation(name, workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByWorkLocationAndAge")
    public ResponseEntity<List<EmployeeDto>> findByWorkLocationAndAge(@RequestParam("workLocation") String workLocation, @RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByWorkLocationAndAge(workLocation, age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByWorkLocationAndSalaryGreaterThan")
    public ResponseEntity<List<EmployeeDto>> findByWorkLocationAndSalaryGreaterThan(@RequestParam("workLocation") String workLocation, @RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findByWorkLocationAndSalaryGreaterThan(workLocation, salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ PLATFORM RELATED ============
    @GetMapping("/findByPlatform")
    public ResponseEntity<List<EmployeeDto>> findByPlatform(@RequestParam("platform") String platform) {
        List<EmployeeDto> employeeDto = employeeService.findByPlatform(platform);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByPlatformAndWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByPlatformAndWorkLocation(@RequestParam("platform") String platform, @RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByPlatformAndWorkLocation(platform, workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ PROJECT RELATED ============
    @GetMapping("/findByProjectName")
    public ResponseEntity<List<EmployeeDto>> findByProjectName(@RequestParam("projectName") String projectName) {
        List<EmployeeDto> employeeDto = employeeService.findByProjectName(projectName);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByProjectNameAndWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByProjectNameAndWorkLocation(@RequestParam("projectName") String projectName, @RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByProjectNameAndWorkLocation(projectName, workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByProjectNameAndPlatform")
    public ResponseEntity<List<EmployeeDto>> findByProjectNameAndPlatform(@RequestParam("projectName") String projectName, @RequestParam("platform") String platform) {
        List<EmployeeDto> employeeDto = employeeService.findByProjectNameAndPlatform(projectName, platform);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ DOCUMENT/ID RELATED ============
    @GetMapping("/findByPanNumber")
    public ResponseEntity<Optional<EmployeeDto>> findByPanNumber(@RequestParam("panNumber") String panNumber) {
        Optional<EmployeeDto> employeeDto = employeeService.findByPanNumber(panNumber);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByAddharNumber")
    public ResponseEntity<Optional<EmployeeDto>> findByAddharNumber(@RequestParam("addharNumber") Long addharNumber) {
        Optional<EmployeeDto> employeeDto = employeeService.findByAddharNumber(addharNumber);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByMobbileNumber")
    public ResponseEntity<Optional<EmployeeDto>> findByMobbileNumber(@RequestParam("mobbileNumber") Long mobbileNumber) {
        Optional<EmployeeDto> employeeDto = employeeService.findByMobbileNumber(mobbileNumber);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ COMBINED QUERIES ============
    @GetMapping("/findByNameAndAge")
    public ResponseEntity<List<EmployeeDto>> findByNameAndAge(@RequestParam("name") String name, @RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByNameAndAge(name, age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameOrAge")
    public ResponseEntity<List<EmployeeDto>> findByNameOrAge(@RequestParam("name") String name, @RequestParam("age") int age) {
        List<EmployeeDto> employeeDto = employeeService.findByNameOrAge(name, age);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameAndSalary")
    public ResponseEntity<List<EmployeeDto>> findByNameAndSalary(@RequestParam("name") String name, @RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findByNameAndSalary(name, salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByAgeAndSalary")
    public ResponseEntity<List<EmployeeDto>> findByAgeAndSalary(@RequestParam("age") int age, @RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findByAgeAndSalary(age, salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameAndAgeAndSalary")
    public ResponseEntity<List<EmployeeDto>> findByNameAndAgeAndSalary(@RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("salary") double salary) {
        List<EmployeeDto> employeeDto = employeeService.findByNameAndAgeAndSalary(name, age, salary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findByNameAndPlatformAndWorkLocation")
    public ResponseEntity<List<EmployeeDto>> findByNameAndPlatformAndWorkLocation(@RequestParam("name") String name, @RequestParam("platform") String platform, @RequestParam("workLocation") String workLocation) {
        List<EmployeeDto> employeeDto = employeeService.findByNameAndPlatformAndWorkLocation(name, platform, workLocation);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ SORTING METHODS ============
    @GetMapping("/sortByEmployeeId")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeId() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeId();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeName")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeName() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeName();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeNameDsc")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeNameDsc() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeNameDsc();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeSalary")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeSalary() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeSalary();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeSalaryDsc")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeSalaryDsc() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeSalaryDsc();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeAge")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeAge() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeAge();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmployeeAgeDsc")
    public ResponseEntity<List<EmployeeDto>> sortByEmployeeAgeDsc() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmployeeAgeDsc();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByEmail")
    public ResponseEntity<List<EmployeeDto>> sortByEmail() {
        List<EmployeeDto> employeeDto = employeeService.sortByEmail();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/sortByWorkLocation")
    public ResponseEntity<List<EmployeeDto>> sortByWorkLocation() {
        List<EmployeeDto> employeeDto = employeeService.sortByWorkLocation();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ AGGREGATION/STATISTICS ============
    @GetMapping("/findAverageSalary")
    public ResponseEntity<Double> findAverageSalary() {
        double average = employeeService.findAverageSalary();
        return new ResponseEntity<>(average, HttpStatus.OK);
    }

    @GetMapping("/findAverageAge")
    public ResponseEntity<Integer> findAverageAge() {
        int average = employeeService.findAverageAge();
        return new ResponseEntity<>(average, HttpStatus.OK);
    }

    @GetMapping("/findAllWorkLocations")
    public ResponseEntity<List<String>> findAllWorkLocations() {
        List<String> workLocations = employeeService.findAllWorkLocations();
        return new ResponseEntity<>(workLocations, HttpStatus.OK);
    }

    @GetMapping("/findAllPlatforms")
    public ResponseEntity<List<String>> findAllPlatforms() {
        List<String> platforms = employeeService.findAllPlatforms();
        return new ResponseEntity<>(platforms, HttpStatus.OK);
    }

    @GetMapping("/findAllProjectNames")
    public ResponseEntity<List<String>> findAllProjectNames() {
        List<String> projectNames = employeeService.findAllProjectNames();
        return new ResponseEntity<>(projectNames, HttpStatus.OK);
    }

    @GetMapping("/countEmployeesByWorkLocation")
    public ResponseEntity<Long> countEmployeesByWorkLocation(@RequestParam("workLocation") String workLocation) {
        long count = employeeService.countEmployeesByWorkLocation(workLocation);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/countEmployeesByPlatform")
    public ResponseEntity<Long> countEmployeesByPlatform(@RequestParam("platform") String platform) {
        long count = employeeService.countEmployeesByPlatform(platform);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/countEmployeesByProjectName")
    public ResponseEntity<Long> countEmployeesByProjectName(@RequestParam("projectName") String projectName) {
        long count = employeeService.countEmployeesByProjectName(projectName);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // ============ FILTERING & PAGINATION ============
    @GetMapping("/findEmployeesByAgeAndSalaryRange")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByAgeAndSalaryRange(@RequestParam("startAge") int startAge, @RequestParam("endAge") int endAge, @RequestParam("minSalary") double minSalary, @RequestParam("maxSalary") double maxSalary) {
        List<EmployeeDto> employeeDto = employeeService.findEmployeesByAgeAndSalaryRange(startAge, endAge, minSalary, maxSalary);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findTopNEmployeesByAge")
    public ResponseEntity<List<EmployeeDto>> findTopNEmployeesByAge(@RequestParam("n") int n) {
        List<EmployeeDto> employeeDto = employeeService.findTopNEmployeesByAge(n);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/findTopNEmployeesBySalary")
    public ResponseEntity<List<EmployeeDto>> findTopNEmployeesBySalary(@RequestParam("n") int n) {
        List<EmployeeDto> employeeDto = employeeService.findTopNEmployeesBySalary(n);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    // ============ JAVA 8 STREAM OPERATIONS ============
    @GetMapping("/mapNamesDetails")
    public ResponseEntity<List<Employee>> mapNamesDetails() {
        List<Employee> employees = employeeService.mapNamesDeatails();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/mapNamesToUppercaseDetails")
    public ResponseEntity<List<String>> mapNamesToUppercaseDetails() {
        List<String> names = employeeService.mapNamesToUppercaseDeatails();
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping("/findStringOccurrenceDetails")
    public ResponseEntity<Map<Character, Integer>> findStringOccurrenceDetails() {
        Map<Character, Integer> occurrences = employeeService.findStringOccurenceDeatails();
        return new ResponseEntity<>(occurrences, HttpStatus.OK);
    }

    @GetMapping("/groupBySalaryDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupBySalaryDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupBySalaryDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByNamesDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupByNamesDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupByNamesDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByWorkLocationDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupByWorkLocationDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupByWorkLocationDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByPlatformDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupByPlatformDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupByPlatformDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByProjectNameDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupByProjectNameDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupByProjectNameDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByAgeDetails")
    public ResponseEntity<Map<Object, List<Employee>>> groupByAgeDetails() {
        Map<Object, List<Employee>> grouped = employeeService.groupByAgeDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupBySalaryDetailsWithCount")
    public ResponseEntity<Map<Double, Long>> groupBySalaryDetailsWithCount() {
        Map<Double, Long> grouped = employeeService.groupBySalaryDeatailsWithCount();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByNamesDetailsWithCount")
    public ResponseEntity<Map<String, Long>> groupByNamesDetailsWithCount() {
        Map<String, Long> grouped = employeeService.groupByNamesDeatailsWithCount();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupByWorkLocationDetailsWithCount")
    public ResponseEntity<Map<String, Long>> groupByWorkLocationDetailsWithCount() {
        Map<String, Long> grouped = employeeService.groupByWorkLocationDeatailsWithCount();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/firstNonRepeatedCharacterInStringDetails")
    public ResponseEntity<String> firstNonRepeatedCharacterInStringDetails() {
        String result = employeeService.firstnonRepeactedCharacterInStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/firstRepeatedCharacterInStringDetails")
    public ResponseEntity<String> firstRepeatedCharacterInStringDetails() {
        String result = employeeService.firstRepeactedCharacterInStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/printDuplicatesInStringDetails")
    public ResponseEntity<List<String>> printDuplicatesInStringDetails() {
        List<String> duplicates = employeeService.printDublicatesInStringDeatails();
        return new ResponseEntity<>(duplicates, HttpStatus.OK);
    }

    @GetMapping("/uniqueRecordsInStringDetails")
    public ResponseEntity<List<String>> uniqueRecordsInStringDetails() {
        List<String> uniques = employeeService.uniquerecordsInStringDeatails();
        return new ResponseEntity<>(uniques, HttpStatus.OK);
    }

    @GetMapping("/longestStringDetails")
    public ResponseEntity<String> longestStringDetails() {
        String result = employeeService.longestStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/smallestStringDetails")
    public ResponseEntity<String> smallestStringDetails() {
        String result = employeeService.smallestStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/filterDepartmentIdsDetails")
    public ResponseEntity<List<String>> filterDepartmentIdsDetails() {
        List<String> ids = employeeService.filterDepartmentIdsDeatails();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/stringReverseJava8Details")
    public ResponseEntity<String> stringReverseJava8Details() {
        String result = employeeService.stringReverseJava8Deatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/indexRangesDetails")
    public ResponseEntity<List<Employee>> indexRangesDetails(@RequestParam("fromIndex") int fromIndex, @RequestParam("toIndex") int toIndex) {
        List<Employee> employees = employeeService.indexRangesDeatails(fromIndex, toIndex);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/joiningNamesDetails")
    public ResponseEntity<String> joiningNamesDetails() {
        String result = employeeService.joiningNamesDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/listToSetConversion")
    public ResponseEntity<Set<Employee>> listToSetConversion() {
        Set<Employee> employees = employeeService.listToSetCoversion();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/listToMapConversion")
    public ResponseEntity<Map<Long, Employee>> listToMapConversion() {
        Map<Long, Employee> employees = employeeService.listToMapCoversion();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/setToListConversion")
    public ResponseEntity<List<Employee>> setToListConversion() {
        List<Employee> employees = employeeService.setToListConversion();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/setToMapConversionDetails")
    public ResponseEntity<Map<Long, Employee>> setToMapConversionDetails() {
        Map<Long, Employee> employees = employeeService.setToMapConversionDetails();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/mapToListConversionDetails")
    public ResponseEntity<List<Map.Entry<Long, Employee>>> mapToListConversionDetails() {
        List<Map.Entry<Long, Employee>> entries = employeeService.mapToListConversionDetails();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/mapToSetConversionDetails")
    public ResponseEntity<Set<Map.Entry<Long, Employee>>> mapToSetConversionDetails() {
        Set<Map.Entry<Long, Employee>> entries = employeeService.mapToSetConversionDetails();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/leftRotationStringDetails")
    public ResponseEntity<String> leftRotationStringDetails() {
        String result = employeeService.leftRotationStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/rightRotationStringDetails")
    public ResponseEntity<String> rightRotationStringDetails() {
        String result = employeeService.rightRotationStringDeatails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findByEmployeeBetweenSalaryDetails")
    public ResponseEntity<List<EmployeeDto>> findByEmployeeBetweenSalaryDetails() {
        List<EmployeeDto> employees = employeeService.findByEmployeeBetweenSalaryDeatails();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/findByGroupCountDetails")
    public ResponseEntity<Map<String, Long>> findByGroupCountDetails() {
        Map<String, Long> counts = employeeService.findBygroupCountDeatails();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }

    @GetMapping("/getEmployeesList")
    public ResponseEntity<Page<Employee>> getEmployeesList(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Employee> employees = employeeService.getEmployeesList(page, size);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/groupByWorkLocationWithEmployeesDetails")
    public ResponseEntity<Map<String, List<Employee>>> groupByWorkLocationWithEmployeesDetails() {
        Map<String, List<Employee>> grouped = employeeService.groupByWorkLocationWithEmployeesDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/groupBySalaryWithEmployeesDetails")
    public ResponseEntity<Map<Double, List<Employee>>> groupBySalaryWithEmployeesDetails() {
        Map<Double, List<Employee>> grouped = employeeService.groupBySalaryWithEmployeesDeatails();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    @GetMapping("/countByWorkLocationDetails")
    public ResponseEntity<Map<String, Long>> countByWorkLocationDetails() {
        Map<String, Long> counts = employeeService.countByWorkLocationDeatails();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }
    @GetMapping("/generatePdf")
    public ResponseEntity <byte[]> generatePdf() {
        byte[] employeeDto = employeeService.pdfGenerator();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(employeeDto);
    }

    @GetMapping("/generateExcel")
    public ResponseEntity <byte[]> generateExcel() {
        byte[] xlsxFile = employeeService.excelGenerator();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(xlsxFile);
    }

    @PostMapping("/sendEmailWithPdf")
    public ResponseEntity<String> sendEmailWithPdfAttachment(@RequestParam("to") String to) {
        ResponseEntity<String> response = employeeService.sendEmailWithPdfAttachment(to);
        return response;  // Assuming the service returns a ResponseEntity<String> directly
    }
    // ============ SINGLETON PATTERN TEST ============
    @GetMapping("/singleton")
    public ResponseEntity<String> testSingleton() {
        SingletonDesignPattarean singleton = SingletonDesignPattarean.getInstance();
        String instanceInfo = "Singleton Instance HashCode: " + System.identityHashCode(singleton) + "\n";
        instanceInfo += "Singleton Class: " + singleton.getClass().getSimpleName() + "\n";

        // Call the singleton method (this will print to console)
        singleton.someMethod();
        return new ResponseEntity<>(instanceInfo, HttpStatus.OK);
    }

    @GetMapping("/singleton/compare")
    public ResponseEntity<String> compareSingletonInstances() {
        SingletonDesignPattarean instance1 = SingletonDesignPattarean.getInstance();
        SingletonDesignPattarean instance2 = SingletonDesignPattarean.getInstance();

        String comparison = "Instance 1 HashCode: " + System.identityHashCode(instance1) + "\n";
        comparison += "Instance 2 HashCode: " + System.identityHashCode(instance2) + "\n";
        comparison += "Are they the same instance? " + (instance1 == instance2) + "\n";

        return new ResponseEntity<>(comparison, HttpStatus.OK);
    }
}