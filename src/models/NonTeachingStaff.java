package models;

public class NonTeachingStaff extends Member {
    public NonTeachingStaff(int memberId, String name, String department, String email) {
        super(memberId, name, department, email);
    }

    public int getMaxBooks() {
        return 3;
    }

    public int getLoanPeriodDays() {
        return 10;
    }

    public int getFineRate() {
        return 3; // ₹3 per day
    }

}
