package telran.employes.services.interfaces;

import telran.employes.dto.Employee;
import telran.employes.dto.ReturnCodes;

public interface EmployeeServise {
	/**
	 * 
	 * @param empl
	 * @return
	 */
	ReturnCodes addEmployee(Employee empl);

	/**
	 * 
	 * @param id
	 * @return
	 */
	ReturnCodes removeEmployee(long id);

	/**
	 * 
	 * @param id
	 * @param newEmploye
	 * @return
	 */
	Employee updateEmployee(long id, Employee newEmploye);

	/**
	 * Ask about iterable
	 * 
	 * @param ageTo
	 * @param ageFrom
	 * @return
	 */
	Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo);

	/**
	 * 
	 * @param department
	 * @return
	 */
	Iterable<Employee> getEmployeesByDepartment(String department);

	/**
	 * 
	 * @param salaryFrom
	 * @param salaryTo
	 * @return
	 */
	Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Employee getEmployee(long id);

}
