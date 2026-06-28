package models;

public class TechnicalBook implements Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String edition;
    private String shelfLocation;
    private boolean isAvailable;

    public TechnicalBook(String bookId, String title, String author,
            String isbn, String publisher, String edition,
            String shelfLocation) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.edition = edition;
        this.shelfLocation = shelfLocation;
        this.isAvailable = true; // available by default!
    }

    public void getDetails() {
        System.out.println("Type: Technical Book");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Publisher: " + publisher);
        System.out.println("Edition: " + edition);
        System.out.println("Shelf: " + shelfLocation);
        System.out.println("Available: " + isAvailable);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public String getBookId() {
        return bookId;
    }

}
