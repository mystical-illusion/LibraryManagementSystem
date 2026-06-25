
// File: src/Main.java
import models.Member;
import models.Student;
import models.Professor;
import models.NonTeachingStaff;

public class Main {
    public static void main(String[] args) {
        Member m1 = new Student(101, "Ravi", "CS", "ravi@email.com");
        Member m2 = new Professor(201, "Dr. Sharma", "CS", "sharma@email.com");
        Member m3 = new NonTeachingStaff(301, "Amit", "Admin", "amit@email.com");

        m1.display();
        System.out.println("Max books: " + m1.getMaxBooks());
        System.out.println("---");

        m2.display();
        System.out.println("Max books: " + m2.getMaxBooks());
    }
}