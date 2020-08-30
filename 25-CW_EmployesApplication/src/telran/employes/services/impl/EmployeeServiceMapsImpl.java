package telran.employes.services.impl;

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
		// TODO Auto-generated method stub

	}

	private void addEmployeeSalary(Employee empl) {
		// TODO Auto-generated method stub

	}

	private void addEmployeeAge(Employee empl) {
		int birthYear = empl.getBirthDate().getYear();
		List<Employee> employeesList = employeesAge.getOrDefault(birthYear, new ArrayList<>());
		employeesList.add(empl);
		employeesAge.putIfAbsent(birthYear, employeesList); // employeesList - ссылка на тот лист
	}

	@Override
	public ReturnCodes removeEmployee(long id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	private void removeEmployeeSalary(Employee empl) {
		// TODO Auto-generated method stub

	}

	private void removeEmployeeAge(Employee empl) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee updateEmployee(long id, Employee newEmploye) {
		// TODO Auto-generated method stub
		// delete old and add new employee
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		// TODO Auto-generated method stub
		NavigableMap<Integer, List<Employee>> employeesSubmap = employeesAge.subMap(getBirthYear(ageTo), true, getBirthYear(ageFrom), true);
		
		return toCollectionEmployes(employeesSubmap.values()); // из списка списков получить один список
	}

	private Iterable<Employee> toCollectionEmployes(Collection<List<Employee>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	private Integer getBirthYear(int ageFrom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {

		return employeesDepartment.getOrDefault(department, new LinkedList<>());
	}

	@Override
	public Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployee(long id) {

		return employees.get(id);
	}

}
