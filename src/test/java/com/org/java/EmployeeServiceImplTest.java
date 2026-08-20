
package com.org.java;

import com.org.java.dto.EmployeeDto;
import com.org.java.entity.Employee;
import com.org.java.exception.NoIdFoundException;
import com.org.java.repository.EmployeeRepository;
import com.org.java.serviceimpl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee e1;
    private Employee e2;
    private Employee e3;

    @BeforeEach
    void setUp() {
        e1 = buildEmployee(1L, "Alice", 30, 60000.0, "alice@test.com", "HYD", "Java", "ProjectA", 1001L, "ABCDE1234F", 9876543210L);
        e2 = buildEmployee(2L, "Bob", 35, 70000.0, "bob@test.com", "PUNE", "Spring", "ProjectB", 1002L, "FGHIJ5678K", 9876543211L);
        e3 = buildEmployee(3L, "Charlie", 40, 80000.0, "charlie@test.com", "HYD", "AWS", "ProjectC", 1003L, "LMNOP9012L", 9876543212L);
    }

    @Test
    void addEmployeeDetails_shouldSaveAndMapToDto() {
        List<Employee> employees = List.of(e1, e2);
        when(employeeRepository.saveAll(employees)).thenReturn(employees);

        List<EmployeeDto> result = employeeService.addEmployeeDetails(employees);

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        verify(employeeRepository).saveAll(employees);
    }

    @Test
    void updateEmployeeDetails_shouldConvertDtoListToEntityListAndSave() {
        List<EmployeeDto> dtoList = List.of(toDto(e1), toDto(e2));
        when(employeeRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<EmployeeDto> result = employeeService.updateEmployeeDetails(dtoList);

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    void fetchAllEmployeeDetails_shouldReturnMappedDtos() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.fetchAllEmployeeDetails();

        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void findByEmployeeId_shouldReturnEmployee_whenFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(e1));

        Optional<EmployeeDto> result = employeeService.findByEmployeeId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getEmpid());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void findByEmployeeId_shouldThrowNoIdFoundException_whenNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        NoIdFoundException ex = assertThrows(NoIdFoundException.class, () -> employeeService.findByEmployeeId(99L));

        assertEquals("Employee not found with id: ", ex.getErrorMessage());
        assertEquals("404", ex.getErrorCode());
    }

    @Test
    void maxSalaryEmployeeDetails_shouldReturnEmployeeWithMaxSalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        EmployeeDto result = employeeService.maxSalaryEmployeeDetails();

        assertNotNull(result);
        assertEquals("Charlie", result.getName());
        assertEquals(80000.0, result.getSalary());
    }

    @Test
    void maxSalarywithQuery_shouldReturnEmployeeWithMaxSalaryFromQuery() {
        when(employeeRepository.findByMaxSalary()).thenReturn(e3);

        EmployeeDto result = employeeService.maxSalarywithQuery();

        assertNotNull(result);
        assertEquals("Charlie", result.getName());
        assertEquals(80000.0, result.getSalary());
    }

    @Test
    void minSalaryEmployeeDetails_shouldReturnEmployeeWithMinSalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        EmployeeDto result = employeeService.minSalaryEmployeeDetails();

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(60000.0, result.getSalary());
    }

    @Test
    void secondHigestSalaryEmployeeDetails_shouldReturnSecondHighestSalaryEmployee() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        EmployeeDto result = employeeService.secondHigestSalaryEmployeeDetails();

        assertNotNull(result);
        assertEquals("Bob", result.getName());
        assertEquals(70000.0, result.getSalary());
    }

    @Test
    void filterWithLimitSalaryEmployeeDetails_shouldReturnSalariesBelow50000() {
        Employee low = buildEmployee(4L, "Diana", 28, 45000.0, "diana@test.com", "CHN", "Java", "ProjectD", 1004L, "QRSTU1234V", 9876543213L);
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3, low));

        List<EmployeeDto> result = employeeService.filterWithLimitSalaryEmployeeDetails();

        assertEquals(1, result.size());
        assertEquals("Diana", result.get(0).getName());
    }

    @Test
    void findAllSalaryAscOrder_shouldSortBySalaryAscending() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.findAllSalaryAscOrder();

        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void findAllSalaryDscOrder_shouldSortBySalaryDescending() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findAllSalaryDscOrder();

        assertEquals(3, result.size());
        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Alice", result.get(2).getName());
    }

    @Test
    void findCountAllSalaries_shouldReturnTotalEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        Long count = employeeService.findCountAllSalaries();

        assertEquals(3L, count);
    }

    @Test
    void findSumAllSalaries_shouldReturnSumOfAllSalaries() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        double sum = employeeService.findSumAllSalaries();

        assertEquals(210000.0, sum);
    }

    @Test
    void findLastSecondThridSalaries_shouldReturnTop3ExceptHighest() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findLastSecondThridSalaries();

        assertEquals(2, result.size());
        assertEquals("Bob", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
    }

    @Test
    void findByName_shouldReturnEmployeesMatchingName() {
        when(employeeRepository.findByName("Alice")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByName("Alice");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameAndAge_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByNameAndAge("Alice", 30)).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameAndAge("Alice", 30);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameOrAge_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByNameOrAge("Alice", 35)).thenReturn(List.of(e1, e2));

        List<EmployeeDto> result = employeeService.findByNameOrAge("Alice", 35);

        assertEquals(2, result.size());
    }

    @Test
    void findByAgeGreaterThan_shouldReturnEmployeesOlderThanAge() {
        when(employeeRepository.findByAgeGreaterThan(30)).thenReturn(List.of(e2, e3));

        List<EmployeeDto> result = employeeService.findByAgeGreaterThan(30);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(x -> x.getName().equals("Bob")));
        assertTrue(result.stream().anyMatch(x -> x.getName().equals("Charlie")));
    }

    @Test
    void findByAgeLessThan_shouldReturnEmployeesYoungerThanAge() {
        when(employeeRepository.findByAgeLessThan(35)).thenReturn(List.of(e1, e2));

        List<EmployeeDto> result = employeeService.findByAgeLessThan(35);

        assertEquals(2, result.size());
    }

    @Test
    void findByAgeBetween_shouldReturnEmployeesWithinRange() {
        when(employeeRepository.findByAgeBetween(30, 35)).thenReturn(List.of(e1, e2));

        List<EmployeeDto> result = employeeService.findByAgeBetween(30, 35);

        assertEquals(2, result.size());
    }

    @Test
    void findByNameStartingWith_shouldReturnEmployeesWithMatchingPrefix() {
        when(employeeRepository.findByNameStartingWith("A")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameStartingWith("A");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameContaining_shouldReturnEmployeesContainingText() {
        when(employeeRepository.findByNameContaining("li")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameContaining("li");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameIgnoreCase_shouldIgnoreCaseWhenSearching() {
        when(employeeRepository.findByNameIgnoreCase("alice")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameIgnoreCase("alice");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameIn_shouldReturnEmployeesInGivenNames() {
        when(employeeRepository.findByNameIn(List.of("Alice", "Bob"))).thenReturn(List.of(e1, e2));

        List<EmployeeDto> result = employeeService.findByNameIn(List.of("Alice", "Bob"));

        assertEquals(2, result.size());
    }

    @Test
    void findByWorkLocation_shouldReturnEmployeesForLocation() {
        when(employeeRepository.findByWorkLocation("HYD")).thenReturn(List.of(e1, e3));

        List<EmployeeDto> result = employeeService.findByWorkLocation("HYD");

        assertEquals(2, result.size());
    }

    @Test
    void findBySalaryGreaterThan_shouldReturnHigherSalaries() {
        when(employeeRepository.findBySalaryGreaterThan(65000)).thenReturn(List.of(e2, e3));

        List<EmployeeDto> result = employeeService.findBySalaryGreaterThan(65000);

        assertEquals(2, result.size());
    }

    @Test
    void findBySalaryBetween_shouldReturnEmployeesWithinSalaryRange() {
        when(employeeRepository.findBySalaryBetween(60000, 75000)).thenReturn(List.of(e1, e2));

        List<EmployeeDto> result = employeeService.findBySalaryBetween(60000, 75000);

        assertEquals(2, result.size());
    }

    @Test
    void findByAge_shouldReturnEmployeesByAge() {
        when(employeeRepository.findByAge(35)).thenReturn(List.of(e2));

        List<EmployeeDto> result = employeeService.findByAge(35);

        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
    }

    @Test
    void findByEmail_shouldReturnEmployeeWhenEmailMatches() {
        when(employeeRepository.findByEmail("alice@test.com")).thenReturn(List.of(e1));

        Optional<EmployeeDto> result = employeeService.findByEmail("alice@test.com");

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void findByEmailContaining_shouldReturnMatchingDomainEmployees() {
        when(employeeRepository.findByEmailContaining("@test.com")).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findByEmailContaining("@test.com");

        assertEquals(3, result.size());
    }

    @Test
    void findByWorkLocationAndAge_shouldReturnEmployeesMatchingBoth() {
        when(employeeRepository.findByWorkLocationAndAge("HYD", 30)).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByWorkLocationAndAge("HYD", 30);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByPlatform_shouldReturnEmployeesByPlatform() {
        when(employeeRepository.findByPlatform("Java")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByPlatform("Java");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByPlatformAndWorkLocation_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByPlatformAndWorkLocation("Java", "HYD")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByPlatformAndWorkLocation("Java", "HYD");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByProjectName_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByProjectName("ProjectA")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByProjectName("ProjectA");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByProjectNameAndWorkLocation_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByProjectNameAndWorkLocation("ProjectA", "HYD")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByProjectNameAndWorkLocation("ProjectA", "HYD");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByProjectNameAndPlatform_shouldReturnEmptyList_asCurrentImplementation() {
        List<EmployeeDto> result = employeeService.findByProjectNameAndPlatform("ProjectA", "Java");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByPanNumber_shouldReturnEmployeeDto_whenPanExists() {
        when(employeeRepository.findByPanNumber("ABCDE1234F")).thenReturn(List.of(e1));

        Optional<EmployeeDto> result = employeeService.findByPanNumber("ABCDE1234F");

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void findByAddharNumber_shouldReturnEmployeeDto_whenAadharExists() {
        when(employeeRepository.findByAddharNumber(1001L)).thenReturn(List.of(e1));

        Optional<EmployeeDto> result = employeeService.findByAddharNumber(1001L);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void findByMobbileNumber_shouldReturnEmployeeDto_whenPhoneExists() {
        when(employeeRepository.findByMobbileNumber(9876543210L)).thenReturn(List.of(e1));

        Optional<EmployeeDto> result = employeeService.findByMobbileNumber(9876543210L);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void findByNameAndSalary_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByNameAndSalary("Bob", 70000.0)).thenReturn(List.of(e2));

        List<EmployeeDto> result = employeeService.findByNameAndSalary("Bob", 70000.0);

        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
    }

    @Test
    void findByAgeAndSalary_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByAgeAndSalary(35, 70000.0)).thenReturn(List.of(e2));

        List<EmployeeDto> result = employeeService.findByAgeAndSalary(35, 70000.0);

        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
    }

    @Test
    void findByNameAndAgeAndSalary_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByNameAndAgeAndSalary("Alice", 30, 60000.0)).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameAndAgeAndSalary("Alice", 30, 60000.0);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void findByNameAndPlatformAndWorkLocation_shouldReturnMatchingEmployees() {
        when(employeeRepository.findByNameAndPlatformAndWorkLocation("Alice", "Java", "HYD")).thenReturn(List.of(e1));

        List<EmployeeDto> result = employeeService.findByNameAndPlatformAndWorkLocation("Alice", "Java", "HYD");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void sortByEmployeeId_shouldSortByEmpIdAscending() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.sortByEmployeeId();

        assertEquals(1L, result.get(0).getEmpid());
        assertEquals(2L, result.get(1).getEmpid());
        assertEquals(3L, result.get(2).getEmpid());
    }

    @Test
    void sortByEmployeeName_shouldSortAlphabeticallyAscending() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.sortByEmployeeName();

        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void sortByEmployeeNameDsc_shouldSortAlphabeticallyDescending() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.sortByEmployeeNameDsc();

        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Alice", result.get(2).getName());
    }

    @Test
    void sortByEmployeeSalary_shouldSortAscendingBySalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.sortByEmployeeSalary();

        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void sortByEmployeeSalaryDsc_shouldSortDescendingBySalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.sortByEmployeeSalaryDsc();

        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Alice", result.get(2).getName());
    }

    @Test
    void sortByEmployeeAge_shouldSortAscendingByAge() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.sortByEmployeeAge();

        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void sortByEmployeeAgeDsc_shouldSortDescendingByAge() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.sortByEmployeeAgeDsc();

        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Alice", result.get(2).getName());
    }

    @Test
    void sortByEmail_shouldSortByEmail() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e1, e2));

        List<EmployeeDto> result = employeeService.sortByEmail();

        assertEquals("alice@test.com", result.get(0).getEmail());
        assertEquals("bob@test.com", result.get(1).getEmail());
        assertEquals("charlie@test.com", result.get(2).getEmail());
    }

    @Test
    void sortByWorkLocation_shouldSortByWorkLocation() {
        when(employeeRepository.findAll()).thenReturn(List.of(e3, e2, e1));

        List<EmployeeDto> result = employeeService.sortByWorkLocation();

        assertEquals("HYD", result.get(0).getWorkLocation());
        assertEquals("HYD", result.get(1).getWorkLocation());
        assertEquals("PUNE", result.get(2).getWorkLocation());
    }

    @Test
    void findAverageSalary_shouldReturnAverageSalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        double avg = employeeService.findAverageSalary();

        assertEquals(70000.0, avg);
    }

    @Test
    void findAverageAge_shouldReturnAverageAge() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        int avgAge = employeeService.findAverageAge();

        assertEquals(35, avgAge);
    }

    @Test
    void findAllWorkLocations_shouldReturnDistinctLocations() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<String> result = employeeService.findAllWorkLocations();

        assertEquals(List.of("HYD", "PUNE"), result);
    }

    @Test
    void findAllPlatforms_shouldReturnDistinctPlatforms() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<String> result = employeeService.findAllPlatforms();

        assertEquals(List.of("Java", "Spring", "AWS"), result);
    }

    @Test
    void findAllProjectNames_shouldReturnDistinctProjectNames() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<String> result = employeeService.findAllProjectNames();

        assertEquals(List.of("ProjectA", "ProjectB", "ProjectC"), result);
    }

    @Test
    void countEmployeesByWorkLocation_shouldReturnCountForLocation() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        long count = employeeService.countEmployeesByWorkLocation("HYD");

        assertEquals(2, count);
    }

    @Test
    void countEmployeesByPlatform_shouldReturnCountForPlatform() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        long count = employeeService.countEmployeesByPlatform("Java");

        assertEquals(1, count);
    }

    @Test
    void countEmployeesByProjectName_shouldReturnCountForProject() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        long count = employeeService.countEmployeesByProjectName("ProjectA");

        assertEquals(1, count);
    }

    @Test
    void findEmployeesByAgeAndSalaryRange_shouldFilterByAgeAndSalary() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findEmployeesByAgeAndSalaryRange(30, 35, 60000, 70000);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(x -> x.getName().equals("Alice")));
        assertTrue(result.stream().anyMatch(x -> x.getName().equals("Bob")));
    }

    @Test
    void findTopNEmployeesByAge_shouldReturnFirstNHighestAges() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findTopNEmployeesByAge(2);

        assertEquals(2, result.size());
        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
    }

    @Test
    void findTopNEmployeesBySalary_shouldReturnAllEmployeesWithCurrentImplementation() {
        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2, e3));

        List<EmployeeDto> result = employeeService.findTopNEmployeesBySalary(2);

        assertEquals(3, result.size());
    }

    private Employee buildEmployee(Long empid, String name, int age, double salary, String email,
                                   String workLocation, String platform, String projectName,
                                   Long addharNumber, String panNumber, Long mobileNumber) {
        Employee employee = new Employee();
        employee.setEmpid(empid);
        employee.setName(name);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setEmail(email);
        employee.setWorkLocation(workLocation);
        employee.setPlatform(platform);
        employee.setProjectName(projectName);
        employee.setAddharNumber(addharNumber);
        employee.setPanNumber(panNumber);
        employee.setMobbileNumber(mobileNumber);
        return employee;
    }

    private EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getEmpid(),
                employee.getName(),
                employee.getAge(),
                employee.getSalary(),
                employee.getEmail(),
                employee.getWorkLocation(),
                employee.getPlatform(),
                employee.getProjectName(),
                employee.getAddharNumber(),
                employee.getPanNumber(),
                employee.getMobbileNumber()
        );
    }
}
