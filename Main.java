
// File: src/Main.java
import models.Book;
import models.BookFactory;
import models.LibrarySystem;
import models.Member;
import models.MemberFactory;
import models.Student;
import models.Professor;
import models.NonTeachingStaff;

public class Main {
        public static void main(String[] args) {
                Member m1 = new Student(101, "Ravi", "CS", "ravi@email.com");

                // NEW way — using Factory!
                Member m2 = MemberFactory.createMember("professor", 201,
                                "Dr. Sharma", "CS", "sharma@email.com");
                Member m3 = MemberFactory.createMember("staff", 301,
                                "Amit", "Admin", "amit@email.com");

                m1.display();
                System.out.println("Max books: " + m1.getMaxBooks());
                System.out.println("---");

                m2.display();
                System.out.println("Max books: " + m2.getMaxBooks());
                m3.display();
                System.out.println("Max books: " + m3.getMaxBooks());

                Book b1 = BookFactory.createBook("technical", "B001", "Clean Code",
                                "Robert Martin", "978-0132350884",
                                "Prentice Hall", "1st", "A1-101");
                Book b2 = BookFactory.createBook("story", "B002", "Harry Potter",
                                "J.K. Rowling", "978-0747532699",
                                "Bloomsbury", "1st", "B2-205");

                b1.getDetails();
                System.out.println("---");
                b2.getDetails();

                LibrarySystem library = LibrarySystem.getInstance();

                // reuse the EXISTING m1, b1 instead of creating new ones!
                library.addMember(m1); // m1 already exists from earlier!
                library.addBook(b1); // b1 already exists from earlier!

                library.listAllBooks();

        }
}