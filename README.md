# 📚 Advanced Library Management System

A robust, enterprise-grade desktop Library Management System built with **Java Swing** and a persistent **MySQL database**. This project showcases the seamless architectural integration of **7 core Object-Oriented Design Patterns** (Creational, Structural, and Behavioral) to build scalable, clean, and decoupling application logic.

---

## 🛠️ System Architecture & Design Patterns

This system deliberately avoids messy, tightly-coupled code by splitting responsibilities across dedicated design patterns:

### 1. Creational Patterns

- **Factory Method:** Abstracted model generation through `BookFactory` and `MemberFactory` to dynamically instantiate concrete types (e.g., _Technical Books_, _Story Books_, _Students_, _Faculty_) at runtime.
- **Singleton:** Enforces a single, globally accessible instance of the core `LibrarySystem` and database connection managers to maintain an accurate transactional state.

### 2. Structural Patterns

- **Facade (`LibraryFacade`):** Acts as a unified entry bridge for the user interface, wrapping complex backend subsystem operations (`issueBook`, `returnBook`) into simplified, high-level API calls.

### 3. Behavioral Patterns

- **Command:** Encapsulates system transactions (`IssueBookCommand`, `ReturnBookCommand`) as standalone transaction objects, allowing decoupled execution tracking and native `undo()` capabilities.
- **Observer:** Implements a dynamic `NotificationSystem`. Registered library members are automatically tracked as observers and notified instantly when system milestones or transaction modifications trigger.
- **Strategy:** Decouples late-return compliance and fine calculation structures (`StandardFineStrategy`, `GracePeriodFineStrategy`) allowing the application to swap penalty computations dynamically based on member categories.

---

## 🖥️ Graphical User Interface (GUI)

The user interface features a sleek, multi-tab layout built using Java Swing (`JTabbedPane`) to organize system output cleanly and prevent terminal clutter:

- **📋 Live Transactions Tab:** A dedicated console panel capturing real-time transaction inputs, generating polymorphic tracking tokens (`TX-XXXXX`), and routing issues and returns.
- **🔔 Notification Hub Tab:** Tracks the live broadcast stream from the Observer pattern, displaying automated alerts to specific members instantly.
- **💰 Fines & Compliance Tab:** Isolates penalty strategy evaluations and rule checks away from standard transaction logging views.

---

## ⚙️ Configuration & Local Security

Database configurations are strictly decoupled from the code to prevent security leakage on public version control histories:

- Credentials are stored locally in an untracked `.env` configuration file.
- The `.gitignore` engine blocks deployment of environment variables while allowing clean contribution tracking across the public codebase.
