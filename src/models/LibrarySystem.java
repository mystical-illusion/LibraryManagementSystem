package models;

import java.util.ArrayList;
import java.util.List;

public class LibrarySystem {
    // Rule 1 — private static instance
    private static LibrarySystem instance;

    // storage for books and members
    private List<Book> books;
    private List<Member> members;

    // Rule 2 — private constructor
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
