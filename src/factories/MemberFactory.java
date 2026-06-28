package factories;

import models.Member;
import models.Student;
import models.Professor;
import models.NonTeachingStaff;

public class MemberFactory {
    public static Member createMember(String type, int memberId,
            String name, String department,
            String email) {
        if (type.equals("student")) {
            return new Student(memberId, name, department, email);
        }
        if (type.equals("professor")) {
            return new Professor(memberId, name, department, email);
        }
        if (type.equals("staff")) {
            return new NonTeachingStaff(memberId, name, department, email);
        }
        return null;
    }
}