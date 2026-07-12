package facade;

import models.Member;
import models.Book;
import system.LibrarySystem;
import commands.IssueBookCommand;
import commands.ReturnBookCommand;
import observers.NotificationSystem;
import strategy.FineCalculator;
import strategy.StandardFineStrategy;
import database.MemberDAO;
import database.BookDAO;
import database.TransactionDAO;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryFacade {
    private LibrarySystem library;
    private NotificationSystem notifier;
    private FineCalculator fineCalculator;
    private MemberDAO memberDAO;
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;

    public LibraryFacade() {
        library = LibrarySystem.getInstance();
        notifier = new NotificationSystem();
        fineCalculator = new FineCalculator(new StandardFineStrategy());
        memberDAO = new MemberDAO();
        bookDAO = new BookDAO();
        transactionDAO = new TransactionDAO();
    }

    public void registerMember(Member member, String memberType) {
        library.addMember(member);
        notifier.addObserver(member);
        memberDAO.saveMember(member, memberType); // ✅ save to DB!
    }

    public void addBook(Book book, String category) {
        library.addBook(book);
        bookDAO.saveBook(book, category); // ✅ save to DB!
    }

    public void issueBook(Member member, Book book,
            String transactionId, String issueDate) {
        IssueBookCommand command = new IssueBookCommand(
                member, book, transactionId, issueDate);
        command.execute();
        bookDAO.updateAvailability(book.getBookId(), false); // ✅ update DB!
        transactionDAO.saveTransaction(transactionId,
                member.getMemberId(), book.getBookId(), issueDate); // ✅ save to DB!
        notifier.notifyAll(member.getName() + " issued: " + book.getBookId());
    }

    public void returnBook(Member member, Book book, String transactionId) {
        ReturnBookCommand command = new ReturnBookCommand(
                member, book, transactionId);
        command.execute();
        String returnDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        double fine = fineCalculator.calculate(member, 0);
        bookDAO.updateAvailability(book.getBookId(), true); // ✅ update DB!
        transactionDAO.updateReturn(transactionId, returnDate, fine); // ✅ update DB!
    }

    public double checkFine(Member member, int daysLate) {
        return fineCalculator.calculate(member, daysLate);
    }
}