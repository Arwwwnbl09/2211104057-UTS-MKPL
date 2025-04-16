package lib;

public class EmploymentPeriod {
    private int yearJoined;
    private int monthJoined;
    private int dayJoined;

    public EmploymentPeriod(int yearJoined, int monthJoined, int dayJoined) {
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

    public int getDayJoined() {
        return dayJoined;
    }
}
