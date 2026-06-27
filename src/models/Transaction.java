package models;

public class Transaction {
    private String transactionId;
    private int memberId;
    private String bookId;
    private String issueDate;
    private String returnDate;
    private double fineAmount;

    public Transaction(String transactionId, int memberId,
            String bookId, String issueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = null;
        this.fineAmount = 0;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public void display() {
        System.out.println("Transaction Detail : ");
        System.out.println("Transaction Id of user : " + transactionId);
        System.out.println("Member Id of user: " + memberId);
        System.out.println("Book Id of book : " + bookId);
        System.out.println("Issue date of book : " + issueDate);
        System.out.println("Return date of book : " + returnDate);
        System.out.println("Fine Amount of book : " + fineAmount);
    }
}