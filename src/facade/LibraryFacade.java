package facade;

import models.Member;
import models.Book;
import system.LibrarySystem;
import commands.IssueBookCommand;
import commands.ReturnBookCommand;
import observers.NotificationSystem;
import strategy.FineCalculator;
import strategy.StandardFineStrategy;

public class LibraryFacade {
    private LibrarySystem library;
    private NotificationSystem notifier;
    private FineCalculator fineCalculator;

    public LibraryFacade() {
        library = LibrarySystem.getInstance();
        notifier = new NotificationSystem();
        fineCalculator = new FineCalculator(new StandardFineStrategy());
    }

    public void registerMember(Member member) {
        library.addMember(member);
        notifier.addObserver(member);
    }

    public void addBook(Book book) {
        library.addBook(book);
    }

    public void issueBook(Member member, Book book, String transactionId, String issueDate) {
        IssueBookCommand command = new IssueBookCommand(member, book, transactionId, issueDate);
        command.execute();
        notifier.notifyAll(member.getName() + " issued: " + book.getBookId());
    }

    public void returnBook(Member member, Book book, String transactionId) {
        ReturnBookCommand command = new ReturnBookCommand(member, book, transactionId);
        command.execute();
    }

    public double checkFine(Member member, int daysLate) {
        return fineCalculator.calculate(member, daysLate);
    }
}
