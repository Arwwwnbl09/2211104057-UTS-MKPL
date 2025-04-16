package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private PersonalInfo personalInfo;
	private EmploymentDate employmentDate;
	private Salary salary;
	private DependentInfo dependentInfo;

	public Employee(PersonalInfo personalInfo, EmploymentDate employmentDate) {
		this.personalInfo = personalInfo;
		this.employmentDate = employmentDate;
		this.salary = new Salary();
		this.dependentInfo = new DependentInfo();
	}

	public void setMonthlySalary(int grade) {
		salary.setMonthlySalary(grade, personalInfo.isForeigner());
	}

	public void setAnnualDeductible(int deductible) {
		salary.setAnnualDeductible(deductible);
	}

	public void setOtherMonthlyIncome(int income) {
		salary.setOtherMonthlyIncome(income);
	}

	public void setSpouse(String spouseName, String spouseIdNumber) {
		dependentInfo.setSpouse(spouseName, spouseIdNumber);
	}

	public void addChild(String childName, String childIdNumber) {
		dependentInfo.addChild(childName, childIdNumber);
	}

	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();
		int monthWorked = (date.getYear() == employmentDate.getYearJoined())
				? date.getMonthValue() - employmentDate.getMonthJoined()
				: 12;

		return TaxFunction.calculateTax(
				salary.getMonthlySalary(),
				salary.getOtherMonthlyIncome(),
				monthWorked,
				salary.getAnnualDeductible(),
				dependentInfo.hasNoSpouse(),
				dependentInfo.getNumberOfChildren());
	}

	// Primitive Obsession refactored classes

	static class PersonalInfo {
		private String employeeId;
		private String firstName;
		private String lastName;
		private String idNumber;
		private String address;
		private boolean isForeigner;
		private boolean gender;

		public PersonalInfo(String employeeId, String firstName, String lastName,
				String idNumber, String address, boolean isForeigner, boolean gender) {
			this.employeeId = employeeId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.idNumber = idNumber;
			this.address = address;
			this.isForeigner = isForeigner;
			this.gender = gender;
		}

		public boolean isForeigner() {
			return isForeigner;
		}
	}

	static class EmploymentDate {
		private int yearJoined;
		private int monthJoined;
		private int dayJoined;

		public EmploymentDate(int yearJoined, int monthJoined, int dayJoined) {
			this.yearJoined = yearJoined;
			this.monthJoined = monthJoined;
			this.dayJoined = dayJoined;
		}

		public int getYearJoined() {
			return yearJoined;
		}

		public int getMonthJoined() {
			return monthJoined;
		}
	}

	static class Salary {
		private int monthlySalary;
		private int otherMonthlyIncome;
		private int annualDeductible;

		public void setMonthlySalary(int grade, boolean isForeigner) {
			int base = switch (grade) {
				case 1 -> 3000000;
				case 2 -> 5000000;
				case 3 -> 7000000;
				default -> 0;
			};
			if (isForeigner)
				base *= 1.5;
			this.monthlySalary = base;
		}

		public void setOtherMonthlyIncome(int income) {
			this.otherMonthlyIncome = income;
		}

		public void setAnnualDeductible(int deductible) {
			this.annualDeductible = deductible;
		}

		public int getMonthlySalary() {
			return monthlySalary;
		}

		public int getOtherMonthlyIncome() {
			return otherMonthlyIncome;
		}

		public int getAnnualDeductible() {
			return annualDeductible;
		}
	}

	static class DependentInfo {
		private String spouseName;
		private String spouseIdNumber;
		private List<String> childNames = new LinkedList<>();
		private List<String> childIdNumbers = new LinkedList<>();

		public void setSpouse(String name, String idNumber) {
			this.spouseName = name;
			this.spouseIdNumber = idNumber;
		}

		public void addChild(String name, String idNumber) {
			childNames.add(name);
			childIdNumbers.add(idNumber);
		}

		public boolean hasNoSpouse() {
			return spouseIdNumber == null || spouseIdNumber.isEmpty();
		}

		public int getNumberOfChildren() {
			return childIdNumbers.size();
		}
	}
}
