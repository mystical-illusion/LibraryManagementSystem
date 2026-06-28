package system;

import java.util.ArrayList;
import java.util.List;
import models.Book;
import models.Member;

public class LibrarySystem {
    private static LibrarySystem instance;
    private List<Book> books;
    private List<Member> members;

    private LibrarySystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        System.out.println("LibrarySystem initialized!");
    }

    public static synchronized LibrarySystem getInstance() {
        if (instance == null) {
            instance = new LibrarySystem();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added to library!");
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member added to library!");
    }

    public void listAllBooks() {
        for (Book b : books) {
            b.getDetails();
            System.out.println("---");
        }
    }
}