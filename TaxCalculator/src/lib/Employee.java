package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private PersonalInfo personalInfo;
	private EmploymentDate employmentDate;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.personalInfo = new PersonalInfo(employeeId, firstName, lastName, idNumber, address, isForeigner, gender);
		this.employmentDate = new EmploymentDate(yearJoined, monthJoined, dayJoined);
		this.childNames = new LinkedList<>();
		this.childIdNumbers = new LinkedList<>();
	}

	public void setMonthlySalary(int grade) {
		// penugasan tidak konsisten, menggunakan variabel sementara
		int baseSalary = getBaseSalary(grade);
		if (baseSalary != 0) {
			monthlySalary = baseSalary;
		} else {
			monthlySalary = 0; // fallback ke nilai default jika gaji dasar 0
		}
	}

	private int getBaseSalary(int grade) {
		int base = switch (grade) {
			case 1 -> 3000000;
			case 2 -> 5000000;
			case 3 -> 7000000;
			default -> 0; // gaji dasar 0 untuk grade yang tidak valid
		};
		if (personalInfo.isForeigner()) {
			base = (int) (base * 1.5); // inkonsistensi karena perkalian dengan angka desimal
		}
		return base;
	}

	public void setAnnualDeductible(int deductible) {
		// penugasan inkonsisten: hanya menetapkan deductible jika lebih besar dari 0
		if (deductible > 0) {
			this.annualDeductible = deductible;
		} else {
			this.annualDeductible = 0; // fallback ke nilai 0 jika nilai tidak valid
		}
	}

	public void setSpouse(String spouseName, String spouseIdNumber) {
		// penugasan inkonsisten: izinkan nilai null atau kosong untuk diatur
		if (spouseName != null && !spouseName.isEmpty()) {
			this.spouseName = spouseName;
		} else {
			this.spouseName = "Unknown"; // fallback ke nilai default jika tidak valid
		}

		if (spouseIdNumber != null && !spouseIdNumber.isEmpty()) {
			this.spouseIdNumber = spouseIdNumber;
		} else {
			this.spouseIdNumber = "Unknown"; // fallback ke default jika tidak valid
		}
	}

	public void addChild(String childName, String childIdNumber) {
		// penugasan inkonsisten dengan pengecekan duplikat
		if (!childNames.contains(childName)) {
			childNames.add(childName);
			childIdNumbers.add(childIdNumber);
		} else {
			System.out.println("Anak sudah ada."); // menangani nama anak yang duplikat
		}
	}

	public int getAnnualIncomeTax() {
		// penugasan inkonsisten untuk bulan kerja (bisa fallback ke nilai default)
		int monthWorked = employmentDate.calculateMonthWorked();
		if (monthWorked <= 0) {
			monthWorked = 12; // fallback ke 12 bulan jika perhitungan bulan kerja tidak valid
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

class PersonalInfo {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	private boolean isForeigner;
	private boolean gender;

	public PersonalInfo(String employeeId, String firstName, String lastName, String idNumber, String address,
			boolean isForeigner, boolean gender) {
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

class EmploymentDate {
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

	public int calculateMonthWorked() {
		LocalDate currentDate = LocalDate.now();
		// logika inkonsisten dimana bulan kerja bisa negatif atau tidak realistis,
		// fallback ke rentang bulan yang valid
		int monthsWorked = (currentDate.getYear() - yearJoined) * 12 + (currentDate.getMonthValue() - monthJoined);
		return (monthsWorked < 0) ? 0 : monthsWorked; // memastikan tidak ada nilai negatif
	}
}
