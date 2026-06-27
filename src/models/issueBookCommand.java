package models;

public class IssueBookCommand implements Command {
    private Member member;
    private Book book;
    private String transactionId;
    private String issueDate;

    public IssueBookCommand(Member member, Book book,
            String transactionId, String issueDate) {
        this.member = member;
        this.book = book;
        this.transactionId = transactionId;
        this.issueDate = issueDate;
    }

   public void execute() {
    if (book.isAvailable()) {
        book.setAvailable(false); // actually change the status!
        // create a Transaction object here too!
        new Transaction(transactionId, member.getMemberId(), transactionId, issueDate);
        // hint: new Transaction(transactionId, member.getMemberId(), ???, issueDate)
        System.out.println("Book issued: " + transactionId);
    } else {
        System.out.println("Book is not available");
    }
}

    public void undo() {
        book.setAvailable(true);
        System.out.println("Book issue undone — returned!");
    }
}