package telran.employes.services.impl;

import java.time.LocalDate;
import java.util.*;

import telran.employes.dto.Employee;
import telran.employes.dto.ReturnCodes;
import telran.employes.services.interfaces.EmployeeServise;

public class EmployeeServiceMapsImpl implements EmployeeServise {

	HashMap<Long, Employee> employees = new HashMap<>();

	TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>(); // key - salary value, value - list of Employes
																		// receiving the
	// salary

	TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>(); // key - birth year, value - list of Employes born
																		// at the year

	HashMap<String, List<Employee>> employeesDepartment = new HashMap<>(); // key - department, value - list of
																			// Employees working at the
	// department

	@Override
	@SuppressWarnings("rawtypes")
	public void printEmployees() {
		for (Map.Entry entry : employees.entrySet()) {
			System.out.println(entry.getValue());
		}

	}

	@Override
	public void clearAll() {
		employees.clear();
		employeesSalary.clear();
		employeesAge.clear();
		employeesDepartment.clear();
	}

	@Override
	public int emplMapSize(String param) throws Exception {
		switch (param) {
		case "employees":
			return employees.size();

		case "salary":
			return employeesSalary.size();

		case "age":
			return employeesAge.size();

		case "department":
			return employeesDepartment.size();

		default:
			throw new IllegalArgumentException("Wrong request");

		}

	}

	public int emplMapSizeByKey(String param) throws Exception {
		switch (param) {
		case "employees":
			return employees.size();

		case "salary":
			return employeesSalary.size();

		case "age":
			return employeesAge.size();

		case "backendDeveloper":
			return employeesDepartment.get(param).size();

		default:
			throw new IllegalArgumentException("Wrong request");

		}

	}

	@Override
	public ReturnCodes addEmployee(Employee empl) {
		Employee res = employees.putIfAbsent(empl.getId(), empl);

		if (res != null)
			return ReturnCodes.EMPLOYEE_ALREADY_EXISTS;

		addEmployeeSalary(empl);
		addEmployeeAge(empl);
		addEmployeeDepartment(empl);

		return ReturnCodes.OK;
	}

	private void addEmployeeDepartment(Employee empl) {
		String department = empl.getDepartment();
		List<Employee> employeesList = employeesDepartment.getOrDefault(department, new ArrayList<>());
		employeesList.add(empl);
		employeesDepartment.putIfAbsent(department, employeesList);
		
		

	}

	private void addEmployeeSalary(Employee empl) {
		int salary = empl.getSalary();
		List<Employee> employeesList = employeesSalary.getOrDefault(salary, new ArrayList<>());
		employeesList.add(empl);
		employeesSalary.putIfAbsent(salary, employeesList);

	}

	private void addEmployeeAge(Employee empl) {
		int birthYear = empl.getBirthDate().getYear();
		List<Employee> employeesList = employeesAge.getOrDefault(birthYear, new ArrayList<>());
		employeesList.add(empl);
		employeesAge.putIfAbsent(birthYear, employeesList); 
	}

	@Override
	public ReturnCodes removeEmployee(long id) {
		Employee empl = employees.remove(id);
		if (empl == null) {
			return ReturnCodes.EMPLOYEE_NOT_FOUND;
		}

		removeEmployeeAge(empl);
		removeEmployeeSalary(empl);
		removeEmployeeDepartment(empl);

		return ReturnCodes.OK;
	}

	private void removeEmployeeDepartment(Employee empl) {
		/**
		 * I have some problems with adding new employeesList without 
		 * removed employee to map.
		 * 
		 * Tried to do by these ways
		 * 
		 */
		String department = empl.getDepartment();

		List<Employee> employeesList = employeesDepartment.getOrDefault(department, new ArrayList<>());

		employeesList.remove(empl);


		employeesDepartment.get(department).clear();


		//employeesDepartment.putIfAbsent(department, employeesList);
		//employeesDepartment.get(department).addAll(employeesList);
		employeesDepartment.put(department, employeesList);
		/**
		 * but it's still empty 
		 */

//		for (Map.Entry<String, List<Employee>> entry : employeesDepartment.entrySet()) {
//			System.out.println(entry);
//		}

	}

	private void removeEmployeeSalary(Employee empl) {
		/**
		 * 
		 * The same problem with removing
		 * 
		 */
		int salary = empl.getSalary();
		List<Employee> employeesList = employeesSalary.getOrDefault(salary, new ArrayList<>());
//		employeesList.remove(empl);
//
//		employeesSalary.put(salary, employeesList);
	}

	private void removeEmployeeAge(Employee empl) {
		/**
		 * 
		 * The same problem with removing
		 * 
		 */
		int birthYear = empl.getBirthDate().getYear();
		List<Employee> employeesList = employeesAge.getOrDefault(birthYear, new ArrayList<>());

	}

	@Override
	public Employee updateEmployee(long id, Employee newEmployee) {
		removeEmployee(id);
		addEmployee(newEmployee);

		return newEmployee;
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		NavigableMap<Integer, List<Employee>> employeesSubmap = employeesAge.subMap(getBirthYear(ageTo), true,
				getBirthYear(ageFrom), true);

		return toCollectionEmployes(employeesSubmap.values());
	}

	private Iterable<Employee> toCollectionEmployes(Collection<List<Employee>> values) {
		List<Employee> result = new ArrayList<>();
		values.forEach(result::addAll);

		return result;
	}

	private Integer getBirthYear(int ageFrom) {
		return LocalDate.now().minusYears(ageFrom).getYear();
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		return employeesDepartment.getOrDefault(department, new LinkedList<>());
	}

	@Override
	public Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		NavigableMap<Integer, List<Employee>> employeesSubMap = employeesSalary.subMap(salaryFrom, true, salaryTo,
				true);

		return toCollectionEmployes(employeesSubMap.values());
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

}
