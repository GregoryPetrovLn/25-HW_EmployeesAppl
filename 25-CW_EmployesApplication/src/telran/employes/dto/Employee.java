package telran.employes.dto;

import java.time.LocalDate;

public class Employee {
	private long id;
	private String name;
	private LocalDate birthDate;
	private String department;
	private int salary;

	public Employee(long id, String name, LocalDate birthDate, String department, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.department = department;
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	public Employee() {

	}

	@Override
	public String toString() {
		return "Employee -> [id = " + this.id + ", name = " + this.name + ", birthDate = " + this.birthDate + ", department = "
				+ this.department + ", salary = " + this.salary + "]";
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return
	 */
	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	/**
	 * 
	 * @return
	 */
	public String getDepartment() {
		return this.department;
	}

	/**
	 * 
	 * @return
	 */
	public int getSalary() {
		return this.salary;
	}

}
