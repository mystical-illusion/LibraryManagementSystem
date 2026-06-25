package models;

public class Student extends Member {
    public Student(int memberId, String name, String department, String email) {
        super(memberId, name, department, email);
    }

    public int getMaxBooks() {
        return 3;
    }

    public int getLoanPeriodDays() {
        return 14;
    }

    public int getFineRate() {
        return 5; // ₹5 per day
    }

}
