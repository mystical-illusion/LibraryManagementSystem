
// File: src/Main.java
import models.Book;
import models.Member;
import models.Student;
import models.Professor;
import models.NonTeachingStaff;
import factories.MemberFactory;
import factories.BookFactory;
import system.LibrarySystem;
import builder.BookBuilder;
import commands.Command;
import commands.IssueBookCommand;
import commands.ReturnBookCommand;
import observers.NotificationSystem;
import strategy.FineCalculator;
import strategy.StandardFineStrategy;
import strategy.GracePeriodFineStrategy;
import builder.BookBuilder;
import facade.LibraryFacade;
import factories.MemberFactory;
import factories.BookFactory;

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

                Member m4 = MemberFactory.createMember("student", 401, "Priya", "IT", "priya@email.com");
                Book b4 = BookFactory.createBook("technical", "B004", "Design Patterns",
                                "Gang of Four", "978-0201633610",
                                "Addison-Wesley", "1st", "C3-301");

                Command issueCommand = new IssueBookCommand(m4, b4, "T001", "2026-06-26");
                issueCommand.execute(); // should issue the book!

                System.out.println("Is book available now? " + b4.isAvailable());

                issueCommand.undo(); // undo the issue!
                System.out.println("Is book available after undo? " + b4.isAvailable());

                Command returnCommand = new ReturnBookCommand(m4, b4, "T002");
                returnCommand.execute(); // book should already be available (from earlier undo), so this should say
                                         // "not issued"!

                issueCommand.execute(); // issue it again first
                returnCommand.execute(); //

                NotificationSystem notifier = new NotificationSystem();
                notifier.addObserver(m1); // m1 is a Student - now also an Observer!
                notifier.addObserver(m2); // m2 is a Professor

                notifier.notifyAll("Library closes early today at 5 PM!");

                notifier.removeObserver(m2);
                notifier.notifyAll("New books arrived in CS section!");

                FineCalculator calculator = new FineCalculator(new StandardFineStrategy());
                double fine1 = calculator.calculate(m1, 5); // Student, 5 days late
                System.out.println("Standard fine for Ravi (5 days late): ₹" + fine1);

                calculator.setStrategy(new GracePeriodFineStrategy());
                double fine2 = calculator.calculate(m1, 5); // same 5 days, different strategy!
                System.out.println("Grace period fine for Ravi (5 days late): ₹" + fine2);

                double fine3 = calculator.calculate(m1, 2); // within grace period!
                System.out.println("Grace period fine for Ravi (2 days late): ₹" + fine3);

                Book b5 = new BookBuilder()
                                .setBookId("B005")
                                .setTitle("Effective Java")
                                .setAuthor("Joshua Bloch")
                                .setIsbn("978-0134685991")
                                .setPublisher("Addison-Wesley")
                                .setEdition("3rd")
                                .setShelfLocation("D4-410")
                                .setCategory("technical")
                                .build();

                b5.getDetails();

                LibraryFacade facade = new LibraryFacade();

                Member student = MemberFactory.createMember("student", 501, "Anjali", "CS", "anjali@email.com");
                Book book = BookFactory.createBook("technical", "B006", "The Pragmatic Programmer",
                                "Dave Thomas", "978-0135957059",
                                "Addison-Wesley", "2nd", "E5-501");

                facade.registerMember(student); // ONE call does Singleton + Observer!
                facade.addBook(book);

                facade.issueBook(student, book, "T003", "2026-06-26"); // ONE call does Command + Observer!

                double fine = facade.checkFine(student, 7);
                System.out.println("Fine for 7 days late: ₹" + fine);

                facade.returnBook(student, book, "T003");

        }
}