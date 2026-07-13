import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import facade.LibraryFacade;
import factories.MemberFactory;
import models.Book;
import models.Member;
import system.LibrarySystem;
import builder.BookBuilder;
import database.DatabaseConnection;

public class LibraryUI extends JFrame {
    private LibraryFacade libraryFacade;

    // Issue/Return fields
    private JTextField memberIdField, bookIdField;
    private JButton issueButton, returnButton;

    // Register Member fields
    private JTextField memberNameField, memberDeptField, memberEmailField;
    private JComboBox<String> memberTypeCombo;
    private JButton registerMemberButton;
    private int nextMemberId = 401;

    // Log areas
    private JTextArea transactionLogArea;
    private JTextArea notificationLogArea;
    private JTextArea fineLogArea;

    public LibraryUI() {
        libraryFacade = new LibraryFacade();
        nextMemberId = getNextMemberId();
        loadSampleData();

        setTitle("Library Management System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── Issue/Return Panel ──
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                "Issue / Return Book"));

        inputPanel.add(new JLabel("  Member ID (Numeric):"));
        memberIdField = new JTextField();
        inputPanel.add(memberIdField);

        inputPanel.add(new JLabel("  Book ID (e.g., B001):"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        // ── Register Member Panel ──
        JPanel registerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        registerPanel.setBorder(BorderFactory.createTitledBorder(
                "Register New Member"));

        registerPanel.add(new JLabel("  Name:"));
        memberNameField = new JTextField();
        registerPanel.add(memberNameField);

        registerPanel.add(new JLabel("  Department:"));
        memberDeptField = new JTextField();
        registerPanel.add(memberDeptField);

        registerPanel.add(new JLabel("  Email:"));
        memberEmailField = new JTextField();
        registerPanel.add(memberEmailField);

        registerPanel.add(new JLabel("  Type:"));
        String[] types = { "student", "professor", "staff" };
        memberTypeCombo = new JComboBox<>(types);
        registerPanel.add(memberTypeCombo);

        // ── North Panel combines both ──
        JPanel northPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        northPanel.add(inputPanel);
        northPanel.add(registerPanel);
        add(northPanel, BorderLayout.NORTH);

        // ── Tabbed Pane (CENTER) ──
        JTabbedPane tabbedPane = new JTabbedPane();

        transactionLogArea = createStyledTextArea();
        tabbedPane.addTab("Live Transactions",
                new JScrollPane(transactionLogArea));

        notificationLogArea = createStyledTextArea();
        tabbedPane.addTab("Notification Hub",
                new JScrollPane(notificationLogArea));

        fineLogArea = createStyledTextArea();
        tabbedPane.addTab("Fines & Compliance",
                new JScrollPane(fineLogArea));

        add(tabbedPane, BorderLayout.CENTER);

        // ── Buttons (BOTTOM) ──
        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 20, 10));

        issueButton = new JButton("Issue Book");
        issueButton.setBackground(new Color(70, 130, 180));
        issueButton.setForeground(Color.WHITE);

        returnButton = new JButton("Return Book");
        returnButton.setBackground(new Color(46, 139, 87));
        returnButton.setForeground(Color.WHITE);

        registerMemberButton = new JButton("Register Member");
        registerMemberButton.setBackground(new Color(153, 50, 204));
        registerMemberButton.setForeground(Color.WHITE);

        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(registerMemberButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setupActionListeners();
        populateInitialLogs();
    }

    // Load next available member ID from MySQL
    private int getNextMemberId() {
        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(
                    "SELECT MAX(member_id) FROM members");
            if (rs.next()) {
                int maxId = rs.getInt(1);
                return maxId == 0 ? 401 : maxId + 1;
            }
        } catch (Exception e) {
            System.err.println("Error getting max member ID: "
                    + e.getMessage());
        }
        return 401;
    }

    private void loadSampleData() {
        // Only load if not already in DB
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
        transactionLogArea.append(
                "Members: Ravi(101), Dr.Sharma(201), Amit(301)\n");
        transactionLogArea.append(
                "Books: B001-Clean Code, B002-Harry Potter, B003-Effective Java\n");
        transactionLogArea.append("Next auto Member ID: "
                + nextMemberId + "\n");
        transactionLogArea.append("---\n");

        notificationLogArea.append(
                "[SYSTEM] Observer registered for all 3 members!\n");
        notificationLogArea.append("---\n");

        fineLogArea.append("[SYSTEM] Fine strategies ready!\n");
        fineLogArea.append("[INFO] Student fine rate: Rs.5/day\n");
        fineLogArea.append("[INFO] Professor fine rate: Rs.2/day\n");
        fineLogArea.append("[INFO] Staff fine rate: Rs.3/day\n");
        fineLogArea.append("---\n");
    }

    private void setupActionListeners() {

        // ── ISSUE BOOK ──
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
                    Member member = LibrarySystem.getInstance()
                            .findMember(memberId);
                    Book book = LibrarySystem.getInstance()
                            .findBook(bookId);

                    if (member == null) {
                        JOptionPane.showMessageDialog(null,
                                "Member ID " + memberId + " not found!\n"
                                        + "Available: 101, 201, 301 or any registered member");
                        return;
                    }
                    if (book == null) {
                        JOptionPane.showMessageDialog(null,
                                "Book ID " + bookId + " not found!\n"
                                        + "Available: B001, B002, B003");
                        return;
                    }
                    if (!book.isAvailable()) {
                        JOptionPane.showMessageDialog(null,
                                "Book " + bookId + " is already issued!");
                        return;
                    }

                    String txId = "TX-"
                            + System.currentTimeMillis() % 100000;
                    String date = new SimpleDateFormat("yyyy-MM-dd")
                            .format(new Date());

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

        // ── RETURN BOOK ──
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
                    Member member = LibrarySystem.getInstance()
                            .findMember(memberId);
                    Book book = LibrarySystem.getInstance()
                            .findBook(bookId);

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
                                "Book " + bookId
                                        + " is not currently issued!");
                        return;
                    }

                    String txId = "TX-"
                            + System.currentTimeMillis() % 100000;
                    String date = new SimpleDateFormat("yyyy-MM-dd")
                            .format(new Date());

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

        // ── REGISTER MEMBER ──
        registerMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = memberNameField.getText().trim();
                String dept = memberDeptField.getText().trim();
                String email = memberEmailField.getText().trim();
                String type = (String) memberTypeCombo.getSelectedItem();

                if (name.isEmpty() || dept.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please fill all member details!");
                    return;
                }

                int newId = nextMemberId++;
                Member newMember = MemberFactory.createMember(
                        type, newId, name, dept, email);

                if (newMember != null) {
                    libraryFacade.registerMember(newMember, type);

                    transactionLogArea.append(String.format(
                            "[REGISTERED] %s (ID: %d) added as %s\n",
                            name, newId, type));

                    JOptionPane.showMessageDialog(null,
                            "Member Registered Successfully!\n"
                                    + "ID: " + newId + "\n"
                                    + "Name: " + name + "\n"
                                    + "Type: " + type);

                    memberNameField.setText("");
                    memberDeptField.setText("");
                    memberEmailField.setText("");
                }
            }
        });
    }

    private void clearInputs() {
        memberIdField.setText("");
        bookIdField.setText("");
    }
}