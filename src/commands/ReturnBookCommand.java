package commands;

import models.Member;
import models.Book;

public class ReturnBookCommand implements Command {
    private Member member;
    private Book book;
    private String transactionId;

    public ReturnBookCommand(Member member, Book book, String transactionId) {
        this.member = member;
        this.book = book;
        this.transactionId = transactionId;
    }

    public void execute() {
        if (!book.isAvailable()) {
            book.setAvailable(true);
            System.out.println("Book returned: " + transactionId);
        } else {
            System.out.println("Book was not issued — cannot return!");
        }
    }

    public void undo() {
        book.setAvailable(false);
        System.out.println("Book issue undone — not returned!");
    }
}