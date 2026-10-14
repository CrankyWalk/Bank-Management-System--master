# Bank Management System — Review 1 assessment build

This project retains the original Java Swing/MySQL application and includes a separate assessment layer. The assessment layer is deliberately isolated from the legacy Swing tables so it can be demonstrated without changing existing customer/signup records.

## Review 1 rubric map (33 marks)

| Criteria | Code evidence | How to demonstrate |
|---|---|---|
| OOP — 10 | `model/BankAccount.java`, `SavingsAccount.java`, `CurrentAccount.java`, `service/TransactionOperations.java`, `AccountService.java` | `BankAccount` is abstract; concrete account classes inherit and override `getAccountType`; use a `TransactionOperations` reference for `AccountService`; private fields and validation demonstrate encapsulation. Existing Swing screens also extend `JFrame` and implement `ActionListener`. |
| Collections & Generics — 6 | `AccountService.java`, `repository/CrudRepository.java`, `dao/AccountDAO.java`, `demo/AssessmentDemo.java` | `Map`, `Set`, `List`, `Optional`, `Future<T>` and `CrudRepository<T, ID>`; demo prints map/set and transaction history. |
| Multithreading & synchronization — 4 | `service/TransactionWorker.java`, `AccountService.java` | A fixed thread pool runs deposit/withdraw tasks; synchronized service methods prevent overlapping balance changes; call `Future.get()` to observe task completion. |
| Database operation classes/models — 7 | `model/AccountRecord.java`, `model/BankTransaction.java`, `dao/AccountDAO.java`, `dao/TransactionDAO.java`, `db/Database.java` | Models are separate from JDBC. `AccountDAO` implements generic CRUD; `TransactionDAO` handles transaction history and atomic money movement. |
| JDBC CRUD / PreparedStatement — 3 | `dao/AccountDAO.java`, `dao/TransactionDAO.java` | Create/read/list/update/delete account operations and parameterized SQL. |
| JDBC transaction management — 3 | `dao/TransactionDAO.java`, `service/LegacyBankService.java` | `setAutoCommit(false)`, row lock, balance validation, ledger insert, `commit()`, and `rollback()` on failure. |

## Requirements

- JDK 17 or later is recommended (the source uses modern Java APIs).
- IntelliJ IDEA.
- MySQL Server and MySQL Connector/J; the Connector/J JAR is in `lib/`.
- JCalendar JAR is in `lib/` for the original signup form.

## Open and run the desktop app

1. Extract the project ZIP.
2. Open the `Bank-Management-System--master` folder in IntelliJ.
3. Ensure `src` is marked as **Sources Root** and both JARs under `lib/` are included as project libraries.
4. Run `legacy-bankSystem-schema.sql` in MySQL Workbench to create the tables expected by the original Swing screens. Back up any existing database before modifying it.
5. Configure `BANK_DB_USER` and `BANK_DB_PASSWORD`, plus optional `BANK_LEGACY_DB_URL` (default: `jdbc:mysql://localhost:3306/bankSystem`). No database password is stored in source code. The web module uses `BANK_ASSESSMENT_DB_URL` (default: `jdbc:mysql://localhost:3306/bank_assessment`) so it does not accidentally use the legacy schema.
6. Run `bank.management.system.Login` or `bank.management.system.main_Class` according to the original flow.

## Run the assessment demonstrations

- Run `bank.management.system.demo.AssessmentDemo` for the guided OOP, Collections/Generics, and concurrency demonstration. It does not change the database.
- Run `bank.management.system.demo.AssessmentSelfTest` for repeatable smoke tests, including 100 concurrent deposits and insufficient-balance validation. It does not change the database.
- The original Swing `Deposit`, `Withdrawl`, `FastCash`, `BalanceEnquriy`, and `mini` screens now use `LegacyBankService`/parameterized SQL for legacy ledger access. `LegacyBankService` validates amounts and wraps each deposit/withdrawal in a JDBC transaction. PIN-based legacy schema is retained for compatibility.

## Set up the assessment database (for JDBC CRUD/transaction demonstrations)

1. In MySQL Workbench, run `web-module/src/main/resources/schema.sql`. It creates the separate `bank_assessment` database, `accounts` table, and `transactions` table. It does not overwrite the legacy `bankSystem` database.
2. Configure `BANK_DB_USER`, `BANK_DB_PASSWORD`, and optionally `BANK_ASSESSMENT_DB_URL` (default: `jdbc:mysql://localhost:3306/bank_assessment`).
3. `AccountDAO` uses the generic CRUD contract; `TransactionDAO.recordTransaction(accountNumber, "DEPOSIT"/"WITHDRAWAL", amount)` updates the balance and inserts the ledger entry in one JDBC transaction. `findByAccount` reads history.
4. Create a throwaway demo account in the assessment schema before testing transaction DAO calls. The web module's `PasswordHashTool` can generate a PBKDF2 password hash for its demo account.

## Environment variables

Windows PowerShell example (only for the current terminal session):

```powershell
$env:BANK_DB_USER = "root"
$env:BANK_DB_PASSWORD = "your-local-mysql-password"
$env:BANK_LEGACY_DB_URL = "jdbc:mysql://localhost:3306/bankSystem"
$env:BANK_ASSESSMENT_DB_URL = "jdbc:mysql://localhost:3306/bank_assessment"
```

Use your actual local credentials; do not commit them into source code or share them in screenshots.

## Important limitations

- The original Swing screens are preserved. Core legacy ledger actions (deposit, withdrawal, fast cash, balance enquiry, mini statement) use parameterized SQL and the transaction service; signup flows still depend on the legacy schema and should be reviewed against your local database. All new assessment DAOs use `PreparedStatement`.
- The assessment schema is separate from the legacy schema. The new DAO/web assessment account records are not automatically the same customers as the original Swing signup records.
- This is an educational demonstration, not production banking software. Do not use real customer data.
- Servlets are in `web-module` and are a separate later criterion; use Tomcat 10.1+ with JDK 17+ and build the WAR from that module.

## Viva checklist

- [ ] Explain encapsulation, inheritance, abstraction, interface and polymorphism with `AssessmentDemo`.
- [ ] Show `List`, `Set`, `Map`, `Optional`, `Future<T>` and `CrudRepository<T, ID>`.
- [ ] Run the worker with deposit and withdrawal tasks and explain synchronization.
- [ ] Explain model, DAO and database connection factory responsibilities.
- [ ] Demonstrate account CRUD with `AccountDAO` after configuring MySQL.
- [ ] Demonstrate `TransactionDAO` commit on success and rollback for insufficient funds or a database error.
- [ ] Run the original Swing deposit and withdrawal screens against your local MySQL database and verify the ledger before submission.
