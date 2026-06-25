package models;

public class Professor extends Member {
    public Professor(int memberId, String name, String department, String email) {
        super(memberId, name, department, email);
    }

    public int getMaxBooks() {
        return 5;
    }

    public int getLoanPeriodDays() {
        return 20;
    }

    public int getFineRate() {
        return 1; // ₹1 per day
    }

}