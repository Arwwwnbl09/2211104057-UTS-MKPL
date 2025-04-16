package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private PersonalData personalData;
	private EmploymentPeriod employmentPeriod;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(PersonalData personalData, EmploymentPeriod employmentPeriod) {
		this.personalData = personalData;
		this.employmentPeriod = employmentPeriod;
		this.childNames = new LinkedList<>();
		this.childIdNumbers = new LinkedList<>();
	}

	public void setMonthlySalary(int grade) {
		int baseSalary = 0;

		switch (grade) {
			case 1:
				baseSalary = 3000000;
				break;
			case 2:
				baseSalary = 5000000;
				break;
			case 3:
				baseSalary = 7000000;
				break;
			default:
				baseSalary = 0;
				break;
		}

		if (personalData.isForeigner()) {
			baseSalary *= 1.5;
		}

		this.monthlySalary = baseSalary;
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setOtherMonthlyIncome(int income) {


	// Method ini dihapus karena tidak digunakan (Speculative Generality)
	// public void setAdditionalIncome(int income) {
	// this.otherMonthlyIncome = income;
	// }

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}


	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber;
	}

	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();

		int monthWorked;
		if (date.getYear() == employmentPeriod.getYearJoined()) {
			monthWorked = date.getMonthValue() - employmentPeriod.getMonthJoined();
		} else {
			monthWorked = 12;
		}

		boolean hasNoSpouse = (spouseIdNumber == null || spouseIdNumber.isEmpty());

		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorked,
				annualDeductible, hasNoSpouse, childIdNumbers.size());
	}
}
}