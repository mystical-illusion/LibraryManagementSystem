package models;

public class BookFactory {
    public static Book createBook(String category, String bookId,
            String title, String author, String isbn,
            String publisher, String edition,
            String shelfLocation) {
        if (category.equals("technical")) {
            return new TechnicalBook(bookId, title, author, isbn,
                    publisher, edition, shelfLocation);
        }
        if (category.equals("story")) {
            return new StoryBook(bookId, title, author, isbn,
                    publisher, edition, shelfLocation);
        }
        return null;
    }
}
