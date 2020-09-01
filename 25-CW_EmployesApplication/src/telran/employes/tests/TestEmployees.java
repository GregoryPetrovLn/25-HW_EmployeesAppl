package telran.employes.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;

import telran.employes.dto.Employee;
import telran.employes.dto.ReturnCodes;
import telran.employes.services.impl.EmployeeServiceMapsImpl;

class TestEmployees {
	EmployeeServiceMapsImpl employeesMap = new EmployeeServiceMapsImpl();
	HashSet<Long> idSet = new HashSet<>();
	long gregoryId = getIdForEmployee();

	Employee empl1 = new Employee(gregoryId, "Gregory", LocalDate.of(1996, 06, 20), "backendDeveloper", 16_000);
	Employee empl2 = new Employee(getIdForEmployee(), "Daria", LocalDate.of(1998, 1, 7), "frontendDeveloper", 12_000);
	Employee empl3 = new Employee(getIdForEmployee(), "Danil", LocalDate.of(1996, 11, 9), "teamLead", 17_000);
	Employee empl4 = new Employee(getIdForEmployee(), "Ann", LocalDate.of(1990, 2, 3), "backendDeveloper", 15_000);
	Employee empl5 = new Employee(getIdForEmployee(), "Andrey", LocalDate.of(1990, 6, 28), "frontendDeveloper", 12_000);
	Employee empl6 = new Employee(getIdForEmployee(), "Chack", LocalDate.of(1996, 1, 2), "teamLead", 20_000);
	Employee empl7 = new Employee(getIdForEmployee(), "Kate", LocalDate.of(1995, 3, 12), "secretary", 10_000);
	Employee empl8 = new Employee(getIdForEmployee(), "Yonatan", LocalDate.of(1992, 5, 15), "backendDeveloper", 15_000);
	Employee empl9 = new Employee(getIdForEmployee(), "Sam", LocalDate.of(1995, 8, 20), "secretary", 10_000);
	Employee empl10 = new Employee(getIdForEmployee(), "Lena", LocalDate.of(2000, 12, 31), "backendDeveloper", 16_000);
	Employee listEmpl[] = { empl1, empl2, empl3, empl4, empl5, empl6, empl7, empl8, empl9, empl10 };

	@BeforeEach
	void fillingMaps() {
		employeesMap.clearAll();

		for (Employee empl : listEmpl) {
			employeesMap.addEmployee(empl);
		}
	}

	Long getIdForEmployee() {
		long min = 10_000;
		long max = 1_000_000;
		long randomId = min + (long) (Math.random() * ((max + 1) - min));

		if (idSet.contains(randomId)) {
			getIdForEmployee();
		} else {
			idSet.add(randomId);
		}

		return randomId;
	}

	@Test
	void testAdd() {
		int withSameSalary = 6;
		int withSameAge = 6;
		int withSameDepartment = 4;
		try {
			assertEquals(listEmpl.length, employeesMap.emplMapSize("employees"));
			assertEquals(withSameSalary, employeesMap.emplMapSize("salary"));
			assertEquals(withSameAge, employeesMap.emplMapSize("age"));
			assertEquals(withSameDepartment, employeesMap.emplMapSize("department"));
		} catch (Throwable e) {
			fail();
		}

		try {
			employeesMap.emplMapSize("lastName");
			fail();
		} catch (Throwable e) {

		}

	}

	@Test
	void testAddEmployee() {
		Employee emplTest = new Employee(getIdForEmployee(), "Jack", LocalDate.of(1982, 2, 21), "withoutDepartment",
				100);

		assertEquals(ReturnCodes.EMPLOYEE_ALREADY_EXISTS, employeesMap.addEmployee(empl1));
		assertEquals(ReturnCodes.OK, employeesMap.addEmployee(emplTest));

	}

	@Test
	void testRemoveEmployee() {
		int mapEmployeesSize = 0;
		int mapEmployeesSalarySize = 0;
		int mapEmployeesAgeSize = 0;
		int mapEmployeesDepartmentSize = 0;
		try {
			mapEmployeesSize = employeesMap.emplMapSizeByKey("backendDeveloper");
//			mapEmployeesSalarySize = employeesMap.emplMapSize("salary");
//			mapEmployeesAgeSize = employeesMap.emplMapSize("age");
//			mapEmployeesDepartmentSize = employeesMap.emplMapSize("department");
		} catch (Throwable e) {
			fail();
		}
		
		try {
		System.out.println(employeesMap.emplMapSize("department"));
		}catch(Throwable e) {
			fail();
		}
		employeesMap.removeEmployee(gregoryId);
		//assertEquals(ReturnCodes.EMPLOYEE_NOT_FOUND, employeesMap.removeEmployee(1232213));
		//assertEquals(ReturnCodes.OK, employeesMap.removeEmployee(gregoryId));
		try {
			System.out.println(employeesMap.emplMapSizeByKey("backendDeveloper"));
//			assertEquals(mapEmployeesSize - 1, employeesMap.emplMapSize("employees"));
//			assertEquals(mapEmployeesSalarySize - 1, employeesMap.emplMapSize("salary"));
//			assertEquals(mapEmployeesAgeSize - 1, employeesMap.emplMapSize("age"));
//			assertEquals(mapEmployeesDepartmentSize - 1, employeesMap.emplMapSize("department"));
		}catch(Throwable e) {
			fail();
		}

	}

	@Test
	void testUpdateEmployee() {
		Employee updated = new Employee(gregoryId, "Gregory_Petrov", LocalDate.of(1996, 06, 20), "backendDeveloper",
				19_000);
		assertEquals(updated, employeesMap.getEmployee(gregoryId));

	}

	@Test
	void testGetEmployeesByAge() {
		HashSet<Employee> excpected = new HashSet<>(Arrays.asList(empl4, empl5, empl8));

		Iterable<Employee> byAge = employeesMap.getEmployeesByAge(26, 40);
		int size = 0;

		for (Employee empl : byAge) {
			assertTrue(excpected.contains(empl));
			size++;
		}
		assertEquals(size, excpected.size());

	}

	@Test
	void testGetEmployeesByDepartment() {
		HashSet<Employee> excpected1 = new HashSet<>(Arrays.asList(empl1, empl4, empl8, empl10));
		HashSet<Employee> excpected2 = new HashSet<>(Arrays.asList(empl2, empl5));
		HashSet<Employee> excpected3 = new HashSet<>(Arrays.asList(empl3, empl6));
		HashSet<Employee> excpected4 = new HashSet<>(Arrays.asList(empl7, empl9));

		Iterable<Employee> byDepartment1 = employeesMap.getEmployeesByDepartment("backendDeveloper");
		Iterable<Employee> byDepartment2 = employeesMap.getEmployeesByDepartment("frontendDeveloper");
		Iterable<Employee> byDepartment3 = employeesMap.getEmployeesByDepartment("teamLead");
		Iterable<Employee> byDepartment4 = employeesMap.getEmployeesByDepartment("secretary");
		int size1 = 0;
		int size2 = 0;
		int size3 = 0;
		int size4 = 0;

		for (Employee empl : byDepartment1) {
			assertTrue(excpected1.contains(empl));
			size1++;
		}
		assertEquals(size1, excpected1.size());

		for (Employee empl : byDepartment2) {
			assertTrue(excpected2.contains(empl));
			size2++;
		}
		assertEquals(size2, excpected2.size());

		for (Employee empl : byDepartment3) {
			assertTrue(excpected3.contains(empl));
			size3++;
		}
		assertEquals(size3, excpected3.size());

		for (Employee empl : byDepartment4) {
			assertTrue(excpected4.contains(empl));
			size4++;
		}
		assertEquals(size4, excpected4.size());

	}

	@Test
	void testGetEmployeesBySalary() {
		HashSet<Employee> excpected1 = new HashSet<>(Arrays.asList(empl1, empl4, empl8, empl10)); // 14 000 - 16 000
		HashSet<Employee> excpected2 = new HashSet<>(Arrays.asList(empl2, empl5)); // 12 000 - 13 000
		HashSet<Employee> excpected3 = new HashSet<>(Arrays.asList(empl1, empl2, empl3, empl4, empl5, empl8, empl10)); // 12
																														// 000
																														// -
																														// 17
																														// 0000
		HashSet<Employee> excpected4 = new HashSet<>(Arrays.asList()); // 5 000 - 8 000

		Iterable<Employee> bySalary1 = employeesMap.getEmployeesBySalary(14000, 16500);
		Iterable<Employee> bySalary2 = employeesMap.getEmployeesBySalary(12000, 13000);

		Iterable<Employee> bySalary3 = employeesMap.getEmployeesBySalary(12000, 17000);
		Iterable<Employee> bySalary4 = employeesMap.getEmployeesBySalary(5000, 8000);
		int size1 = 0;
		int size2 = 0;
		int size3 = 0;
		int size4 = 0;

		for (Employee empl : bySalary1) {
			assertTrue(excpected1.contains(empl));
			size1++;
		}
		assertEquals(size1, excpected1.size());

		for (Employee empl : bySalary2) {
			assertTrue(excpected2.contains(empl));
			size2++;
		}
		assertEquals(size2, excpected2.size());

		for (Employee empl : bySalary3) {
			assertTrue(excpected3.contains(empl));
			size3++;
		}
		assertEquals(size3, excpected3.size());

		for (Employee empl : bySalary4) {
			assertTrue(excpected4.contains(empl));
			size4++;
		}
		assertEquals(size4, excpected4.size());

		bySalary4.forEach(System.out::println);
	}

	@Test
	void testgetEmployeeById() {
		assertEquals(empl1, employeesMap.getEmployee(gregoryId));
	}

}
