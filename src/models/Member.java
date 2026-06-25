package models;

public abstract class Member {
    private int memberId;
    private String name;
    private String department;
    private String email;

    Member(int memberId, String name, String department, String email) {
        this.memberId = memberId;
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public abstract int getMaxBooks();

    public abstract int getLoanPeriodDays();

    public abstract int getFineRate();

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public void display() {
        System.out.println("ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Email: " + email);
    }
}
