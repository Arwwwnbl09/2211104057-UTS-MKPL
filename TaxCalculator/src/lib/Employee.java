package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;

	private int yearJoined;
	private int monthJoined;
	private int dayJoined;

	private boolean isForeigner;
	private boolean gender; // true = Laki-laki, false = Perempuan

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;

		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}

	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya
	 * (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3:
	 * 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	public void setMonthlySalary(int grade) {
		monthlySalary = getBaseSalary(grade);
	}

	private int getBaseSalary(int grade) {
		int base;
		switch (grade) {
			case 1:
				base = 3000000;
				break;
			case 2:
				base = 5000000;
				break;
			case 3:
				base = 7000000;
				break;
			default:
				base = 0;
				break;
		}
		if (isForeigner) {
			base *= 1.5;
		}
		return base;
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	// public void setAdditionalIncome(int income) {
	// this.otherMonthlyIncome = income;
	// }

	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber; // FIXED: sebelumnya keliru set ke idNumber
	}

	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();

		int monthWorked;
		if (date.getYear() == yearJoined) {
			monthWorked = date.getMonthValue() - monthJoined;
		} else {
			monthWorked = 12;
		}

		return TaxFunction.calculateTax(
				monthlySalary,
				otherMonthlyIncome,
				monthWorked,
				annualDeductible,
				spouseIdNumber == null || spouseIdNumber.equals(""),
				childIdNumbers.size());
	}
}
