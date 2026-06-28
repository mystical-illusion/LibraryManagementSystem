package builder;

import models.Book;
import models.TechnicalBook;
import models.StoryBook;

public class BookBuilder {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String edition;
    private String shelfLocation;
    private String category;

    public BookBuilder setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    // setAuthor, setIsbn, setPublisher,setEdition, setShelfLocation, setCategory
    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setEdition(String edition) {
        this.edition = edition;
        return this;
    }

    public BookBuilder setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public Book build() {
        if (category.equals("technical")) {
            return new TechnicalBook(bookId, title, author, isbn, publisher, edition, shelfLocation);
        }
        return new StoryBook(bookId, title, author, isbn, publisher, edition, shelfLocation);
    }
}