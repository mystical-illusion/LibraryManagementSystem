# 📚 Library Management System

A full-stack Library Management System built in Java, 
demonstrating mastery of Object-Oriented Programming, 
7 Design Patterns, SOLID Principles, MySQL database 
integration, and a Swing GUI.

---

## 🏗️ Architecture

```
src/
├── models/      → Member hierarchy, Book hierarchy, Transaction
├── factories/   → MemberFactory, BookFactory (Factory Pattern)
├── system/      → LibrarySystem (Singleton Pattern)
├── commands/    → IssueBookCommand, ReturnBookCommand (Command Pattern)
├── observers/   → NotificationSystem (Observer Pattern)
├── strategy/    → FineCalculator, Fine strategies (Strategy Pattern)
├── builder/     → BookBuilder (Builder Pattern)
├── facade/      → LibraryFacade (Facade Pattern)
└── database/    → DatabaseConnection, MemberDAO, BookDAO, TransactionDAO
```

---

## ✅ Design Patterns Implemented (7)

| Pattern | Where Used | Purpose |
|---------|-----------|---------|
| Singleton | LibrarySystem | One central library instance |
| Factory | MemberFactory, BookFactory | Create member/book types |
| Observer | NotificationSystem | Auto-notify members on transactions |
| Strategy | FineCalculator | Swap fine calculation logic at runtime |
| Builder | BookBuilder | Construct Book objects with method chaining |
| Command | IssueBookCommand, ReturnBookCommand | Issue/Return with undo support |
| Facade | LibraryFacade | Unified simple API over complex subsystems |

---

## 👥 Member Hierarchy (Polymorphism + LSP)

```
Member (abstract)
├── Student     → maxBooks: 3, loanPeriod: 14 days, fine: ₹5/day
├── Professor   → maxBooks: 5, loanPeriod: 20 days, fine: ₹1/day
└── NonTeachingStaff → maxBooks: 3, loanPeriod: 10 days, fine: ₹3/day
```

Librarian kept SEPARATE from Member hierarchy (LSP compliance)

---

## 📖 Book Types (Factory + Builder)

```
Book (interface)
├── TechnicalBook → subject books, shelf-coded by department
└── StoryBook     → fiction/general books, separate section
```

---

## 🗄️ Database Schema (MySQL)

```sql
members      → member_id, name, department, email, member_type
books        → book_id, title, author, isbn, publisher, 
               edition, shelf_location, is_available, category
transactions → transaction_id, member_id (FK), book_id (FK), 
               issue_date, return_date, fine_amount
```

JOIN query for full transaction details:
```sql
SELECT m.name, b.title, t.issue_date, t.fine_amount
FROM transactions t
JOIN members m ON t.member_id = m.member_id
JOIN books b ON t.book_id = b.book_id;
```

---

## 🖥️ GUI Features (Java Swing)

- Issue Book with real-time validation
- Return Book with automatic fine calculation
- Live Transactions tab
- Notification Hub tab
- Fines & Compliance tab

---

## 🔑 SOLID Principles Applied

- **SRP** — Each class has one responsibility (DAO classes handle only DB operations)
- **OCP** — Add new book/member types without modifying existing code
- **LSP** — Librarian separate from Member (doesn't borrow books)
- **ISP** — Book interface only defines what ALL books need
- **DIP** — LibraryFacade depends on abstractions, not concrete classes

---

## 🛠️ Tech Stack

- Java (OOP, Design Patterns)
- MySQL (JDBC, Normalization, Foreign Keys)
- Java Swing (GUI)
- Git/GitHub

---

## 🚀 How to Run

1. Clone the repository
2. Set up MySQL database using `schema.sql`
3. Add `mysql-connector-j-9.7.0.jar` to `lib/` folder
4. Configure VS Code classpath to include the JAR
5. Run `Main.java`

---

## 📊 Key Design Decisions

- **Book as interface** (not abstract class) — TechnicalBook and StoryBook share contract, not state
- **Librarian separate from Member** — LSP compliance, Librarian doesn't borrow books
- **DAO pattern for database** — separates business logic from SQL (SRP)
- **Facade as single entry point** — GUI only talks to LibraryFacade, never directly to subsystems
