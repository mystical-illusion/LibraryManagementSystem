import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import facade.LibraryFacade;
import factories.BookFactory;
import factories.MemberFactory;
import models.Book;
import models.Member;
import system.LibrarySystem;
import builder.BookBuilder;

public class LibraryUI extends JFrame {
    private LibraryFacade libraryFacade;

    private JTextField memberIdField, bookIdField;
    private JButton issueButton, returnButton;

    private JTextArea transactionLogArea;
    private JTextArea notificationLogArea;
    private JTextArea fineLogArea;

    public LibraryUI() {
        libraryFacade = new LibraryFacade();
        loadSampleData();

        setTitle("Library Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Operation Parameters"));

        inputPanel.add(new JLabel("  Member ID (Numeric):"));
        memberIdField = new JTextField();
        inputPanel.add(memberIdField);

        inputPanel.add(new JLabel("  Book ID (e.g., B001):"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        add(inputPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        transactionLogArea = createStyledTextArea();
        tabbedPane.addTab("Live Transactions", new JScrollPane(transactionLogArea));

        notificationLogArea = createStyledTextArea();
        tabbedPane.addTab("Notification Hub", new JScrollPane(notificationLogArea));

        fineLogArea = createStyledTextArea();
        tabbedPane.addTab("Fines & Compliance", new JScrollPane(fineLogArea));

        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        issueButton = new JButton("Issue Book");
        returnButton = new JButton("Return Book");

        issueButton.setBackground(new Color(70, 130, 180));
        issueButton.setForeground(Color.WHITE);
        returnButton.setBackground(new Color(46, 139, 87));
        returnButton.setForeground(Color.WHITE);

        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setupActionListeners();
        populateInitialLogs();
    }

    private void loadSampleData() {
        Member m1 = MemberFactory.createMember("student", 101,
                "Ravi", "CS", "ravi@email.com");
        Member m2 = MemberFactory.createMember("professor", 201,
                "Dr. Sharma", "CS", "sharma@email.com");
        Member m3 = MemberFactory.createMember("staff", 301,
                "Amit", "Admin", "amit@email.com");

        Book b1 = new BookBuilder()
                .setBookId("B001").setTitle("Clean Code")
                .setAuthor("Robert Martin").setIsbn("978-0132350884")
                .setPublisher("Prentice Hall").setEdition("1st")
                .setShelfLocation("A1-101").setCategory("technical")
                .build();

        Book b2 = new BookBuilder()
                .setBookId("B002").setTitle("Harry Potter")
                .setAuthor("J.K. Rowling").setIsbn("978-0747532699")
                .setPublisher("Bloomsbury").setEdition("1st")
                .setShelfLocation("B2-205").setCategory("story")
                .build();

        Book b3 = new BookBuilder()
                .setBookId("B003").setTitle("Effective Java")
                .setAuthor("Joshua Bloch").setIsbn("978-0134685991")
                .setPublisher("Addison-Wesley").setEdition("3rd")
                .setShelfLocation("C3-301").setCategory("technical")
                .build();

        libraryFacade.registerMember(m1, "student");
        libraryFacade.registerMember(m2, "professor");
        libraryFacade.registerMember(m3, "staff");
        libraryFacade.addBook(b1, "technical");
        libraryFacade.addBook(b2, "story");
        libraryFacade.addBook(b3, "technical");
    }

    private JTextArea createStyledTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textArea;
    }

    private void populateInitialLogs() {
        transactionLogArea.append("[INIT] LibrarySystem initialized!\n");
        transactionLogArea.append("[INIT] 3 Members and 3 Books loaded!\n");
        transactionLogArea.append("Members: Ravi(101), Dr.Sharma(201), Amit(301)\n");
        transactionLogArea.append("Books: B001-Clean Code, B002-Harry Potter, B003-Effective Java\n");
        transactionLogArea.append("---\n");

        notificationLogArea.append("[SYSTEM] Observer registered for all 3 members!\n");
        notificationLogArea.append("---\n");

        fineLogArea.append("[SYSTEM] Fine strategies ready!\n");
        fineLogArea.append("[INFO] Student fine rate: Rs.5/day\n");
        fineLogArea.append("[INFO] Professor fine rate: Rs.2/day\n");
        fineLogArea.append("[INFO] Staff fine rate: Rs.3/day\n");
        fineLogArea.append("---\n");
    }

    private void setupActionListeners() {
        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rawMemberId = memberIdField.getText().trim();
                String bookId = bookIdField.getText().trim();

                if (rawMemberId.isEmpty() || bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter both Member ID and Book ID!");
                    return;
                }

                try {
                    int memberId = Integer.parseInt(rawMemberId);

                    Member member = LibrarySystem.getInstance().findMember(memberId);
                    Book book = LibrarySystem.getInstance().findBook(bookId);

                    if (member == null) {
                        JOptionPane.showMessageDialog(null,
                                "Member ID " + memberId + " not found!\n" +
                                        "Available: 101 (Ravi), 201 (Dr.Sharma), 301 (Amit)");
                        return;
                    }

                    if (book == null) {
                        JOptionPane.showMessageDialog(null,
                                "Book ID " + bookId + " not found!\n" +
                                        "Available: B001, B002, B003");
                        return;
                    }

                    if (!book.isAvailable()) {
                        JOptionPane.showMessageDialog(null,
                                "Book " + bookId + " is already issued!");
                        return;
                    }

                    String txId = "TX-" + System.currentTimeMillis() % 100000;
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    libraryFacade.issueBook(member, book, txId, date);

                    transactionLogArea.append(String.format(
                            "[%s] Book %s issued to %s (TX: %s)\n",
                            date, bookId, member.getName(), txId));

                    notificationLogArea.append(String.format(
                            "[ALERT] %s notified: Issued book %s\n",
                            member.getName(), bookId));

                    clearInputs();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Member ID must be a number!");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rawMemberId = memberIdField.getText().trim();
                String bookId = bookIdField.getText().trim();

                if (rawMemberId.isEmpty() || bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter both Member ID and Book ID!");
                    return;
                }

                try {
                    int memberId = Integer.parseInt(rawMemberId);

                    Member member = LibrarySystem.getInstance().findMember(memberId);
                    Book book = LibrarySystem.getInstance().findBook(bookId);

                    if (member == null) {
                        JOptionPane.showMessageDialog(null,
                                "Member ID " + memberId + " not found!");
                        return;
                    }

                    if (book == null) {
                        JOptionPane.showMessageDialog(null,
                                "Book ID " + bookId + " not found!");
                        return;
                    }

                    if (book.isAvailable()) {
                        JOptionPane.showMessageDialog(null,
                                "Book " + bookId + " is not currently issued!");
                        return;
                    }

                    String txId = "TX-" + System.currentTimeMillis() % 100000;
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    libraryFacade.returnBook(member, book, txId);

                    transactionLogArea.append(String.format(
                            "[%s] Book %s returned by %s (TX: %s)\n",
                            date, bookId, member.getName(), txId));

                    double fine = libraryFacade.checkFine(member, 5);
                    fineLogArea.append(String.format(
                            "[%s] %s returned %s. Fine (5 days): Rs.%.1f\n",
                            date, member.getName(), bookId, fine));

                    clearInputs();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Member ID must be a number!");
                }
            }
        });
    }

    private void clearInputs() {
        memberIdField.setText("");
        bookIdField.setText("");
    }
}