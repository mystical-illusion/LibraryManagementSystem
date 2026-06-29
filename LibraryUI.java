import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import facade.LibraryFacade;
import models.Book;
import models.Member;
import factories.BookFactory;
import factories.MemberFactory;

public class LibraryUI extends JFrame {
    private LibraryFacade libraryFacade;

    // UI Input Components
    private JTextField memberIdField, bookIdField;
    private JButton issueButton, returnButton;

    // Dedicated Organized Text Areas for Logging
    private JTextArea transactionLogArea;
    private JTextArea notificationLogArea;
    private JTextArea fineLogArea;

    public LibraryUI() {
        // 1. Initialize backend link
        libraryFacade = new LibraryFacade();

        // 2. Configure Main Window Frame
        setTitle("Library Management System - Executive Dashboard");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout(10, 10));

        // 3. Create Shared Input Panel (Top)
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Operation Parameters"));

        inputPanel.add(new JLabel("  Member ID (Numeric):"));
        memberIdField = new JTextField();
        inputPanel.add(memberIdField);

        inputPanel.add(new JLabel("  Book ID (e.g., T001):"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        add(inputPanel, BorderLayout.NORTH);

        // 4. Create the Tabbed Layout Interface (Center Component)
        JTabbedPane tabbedPane = new JTabbedPane();

        // TAB A: Transaction Logs
        transactionLogArea = createStyledTextArea();
        tabbedPane.addTab("📋 Live Transactions", new JScrollPane(transactionLogArea));

        // TAB B: Notification Stream
        notificationLogArea = createStyledTextArea();
        tabbedPane.addTab("🔔 Notification Hub", new JScrollPane(notificationLogArea));

        // TAB C: Fines & Strategies
        fineLogArea = createStyledTextArea();
        tabbedPane.addTab("💰 Fines & Compliance", new JScrollPane(fineLogArea));

        add(tabbedPane, BorderLayout.CENTER);

        // Pre-populate historical demo records cleanly inside their respective slots
        populateInitialHistoricalLogs();

        // 5. Create Control Action Buttons (Bottom)
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

        // 6. Hook up Action Handlers
        setupActionListeners();
    }

    /**
     * Component Helper to enforce clean typography across all log sub-views
     */
    private JTextArea createStyledTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textArea;
    }

    /**
     * Organizes the old messy initialization logs into their proper tabs
     * automatically on launch
     */
    private void populateInitialHistoricalLogs() {
        // Populating the Transaction Tab
        transactionLogArea.append("[INIT] LibrarySystem initialized!\n");
        transactionLogArea.append("[INIT] Default Members & Books added to system memory storage.\n");
        transactionLogArea.append("----------------------------------------------------------------\n");

        // Populating the Notification Tab
        notificationLogArea.append("[SYSTEM] Observers registered for notifications natively!\n");
        notificationLogArea.append("Ravi notified: Library closes early today at 5 PM!\n");
        notificationLogArea.append("Dr. Sharma notified: Library closes early today at 5 PM!\n");
        notificationLogArea.append("Anjali notified: Anjali issued: B006\n");
        notificationLogArea.append("----------------------------------------------------------------\n");

        // Populating the Fine Calculations Tab
        fineLogArea.append("[STRATEGY] Standard fine for Ravi (5 days late): ₹25.0\n");
        fineLogArea.append("[STRATEGY] Grace period fine for Ravi (5 days late): ₹10.0\n");
        fineLogArea.append("[STRATEGY] Grace period fine for Ravi (2 days late): ₹0.0\n");
        fineLogArea.append("[SYSTEM] Fine for 7 days late evaluated: ₹35.0\n");
        fineLogArea.append("----------------------------------------------------------------\n");
    }

    private void setupActionListeners() {
        // --- ISSUE BOOK ACTION ---
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rawMemberId = memberIdField.getText().trim();
                String bookId = bookIdField.getText().trim();

                if (rawMemberId.isEmpty() || bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please supply parameters to process transaction.");
                    return;
                }

                try {
                    int memberId = Integer.parseInt(rawMemberId);
                    String generatedTxId = "TX-" + System.currentTimeMillis() % 100000;
                    String currentDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    Member dummyMember = MemberFactory.createMember("student", memberId, "User-" + memberId, "CS",
                            "user@email.com");
                    Book dummyBook = BookFactory.createBook("technical", bookId, "Book-" + bookId, "Author", "ISBN",
                            "Publisher", "1st", "A1");
                    dummyBook.setAvailable(true);

                    // Execute unaltered backend process
                    libraryFacade.issueBook(dummyMember, dummyBook, generatedTxId, currentDateStr);

                    // Cleanly route updates straight to the Live Transactions Tab
                    transactionLogArea.append(String.format("[%s] Issued Book %s to Member %d (ID: %s)\n",
                            currentDateStr, bookId, memberId, generatedTxId));

                    // Route the side-effect alert straight to the Notification Hub Tab
                    notificationLogArea
                            .append(String.format("[BROADCAST] User-%d notified: Issued: %s\n", memberId, bookId));

                    clearInputs();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Member ID tracking token must be numeric.");
                }
            }
        });

        // --- RETURN BOOK ACTION ---
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rawMemberId = memberIdField.getText().trim();
                String bookId = bookIdField.getText().trim();

                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please define a Book ID token.");
                    return;
                }

                int memberId = 101;
                if (!rawMemberId.isEmpty()) {
                    try {
                        memberId = Integer.parseInt(rawMemberId);
                    } catch (NumberFormatException ex) {
                    }
                }

                String generatedTxId = "TX-" + System.currentTimeMillis() % 100000;
                String currentDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                Member dummyMember = MemberFactory.createMember("student", memberId, "User-" + memberId, "CS",
                        "user@email.com");
                Book dummyBook = BookFactory.createBook("technical", bookId, "Book-" + bookId, "Author", "ISBN",
                        "Publisher", "1st", "A1");
                dummyBook.setAvailable(false);

                // Execute unaltered backend process
                libraryFacade.returnBook(dummyMember, dummyBook, generatedTxId);

                // Append cleanly to the dedicated Transaction tab view
                transactionLogArea.append(String.format("[%s] Returned Book %s via Tracking Token: %s\n",
                        currentDateStr, bookId, generatedTxId));

                // Route sample fine analysis to the Fine tab view to keep layout clear
                fineLogArea.append(
                        String.format("[%s] Returned %s: Strategy checked. Calculated penalty balance applied.\n",
                                currentDateStr, bookId));

                clearInputs();
            }
        });
    }

    private void clearInputs() {
        memberIdField.setText("");
        bookIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryUI().setVisible(true);
        });
    }
}