import java.util.Scanner;

class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    private double balance;

    // Constructor
    public BankAccount(int accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    // Getter methods
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful! New balance: $" + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful! New balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    // Display account details
    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: $" + balance);
    }
}

class BankSystem {
    private static BankAccount[] accounts = new BankAccount[100];
    private static int numAccounts = 0;

    // Create a new account
    public static void createAccount(int accountNumber, String accountHolderName, double initialBalance) {
        accounts[numAccounts++] = new BankAccount(accountNumber, accountHolderName, initialBalance);
        System.out.println("Account created successfully!");
    }

    // Find account by account number
    public static BankAccount findAccount(int accountNumber) {
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                return accounts[i];
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    // Transfer funds between accounts
    public static void transferFunds(int fromAccount, int toAccount, double amount) {
        BankAccount source = findAccount(fromAccount);
        BankAccount destination = findAccount(toAccount);
        if (source != null && destination != null) {
            if (source.getBalance() >= amount) {
                source.withdraw(amount);
                destination.deposit(amount);
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient funds in the source account.");
            }
        }
    }

    // Display all accounts
    public static void displayAllAccounts(String username) {
        System.out.println("Displaying accounts for user: " + username);
        for (int i = 0; i < numAccounts; i++) {
            System.out.println("Account #" + (i + 1));
            accounts[i].displayAccountDetails();
            System.out.println();
        }
    }
}

public class onlineBanking {
    private static final String ACCESS_CODE = "123456";
    private static String loggedInUser;

    // Login method
    private static boolean login(Scanner scanner) {
        System.out.print("Enter access code: ");
        String enteredCode = scanner.next();
        scanner.nextLine();  // Consume newline

        if (!ACCESS_CODE.equals(enteredCode)) {
            System.out.println("Incorrect access code. Access denied.");
            return false;
        }

        System.out.print("Enter your name to log in: ");
        loggedInUser = scanner.nextLine();
        System.out.println("Welcome, " + loggedInUser + "! You are now logged into the online banking system.");
        return true;
    }

    // Verify password before executing any operation
    private static boolean verifyAccessCode(Scanner scanner) {
        System.out.print("Enter access code to proceed: ");
        String enteredCode = scanner.next();
        return ACCESS_CODE.equals(enteredCode);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User login
        if (!login(scanner)) {
            System.out.println("Exiting system.");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- Online Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Display Account Details");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Logout and Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (verifyAccessCode(scanner)) {
                        System.out.print("Enter account number: ");
                        int accNumber = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter account holder name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter initial balance: ");
                        double initialBalance = scanner.nextDouble();
                        BankSystem.createAccount(accNumber, name, initialBalance);
                    } else {
                        System.out.println("Incorrect access code. Operation cancelled.");
                    }
                    break;

                case 2:
                    if (verifyAccessCode(scanner)) {
                        System.out.print("Enter account number: ");
                        int accNumber = scanner.nextInt();
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        BankAccount account = BankSystem.findAccount(accNumber);
                        if (account != null) {
                            account.deposit(depositAmount);
                        }
                    } else {
                        System.out.println("Incorrect access code. Operation cancelled.");
                    }
                    break;

                case 3:
                    if (verifyAccessCode(scanner)) {
                        System.out.print("Enter account number: ");
                        int accNumber = scanner.nextInt();
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        BankAccount account = BankSystem.findAccount(accNumber);
                        if (account != null) {
                            account.withdraw(withdrawAmount);
                        }
                    } else {
                        System.out.println("Incorrect access code. Operation cancelled.");
                    }
                    break;

                case 4:
                    if (verifyAccessCode(scanner)) {
                        System.out.print("Enter source account number: ");
                        int fromAccount = scanner.nextInt();
                        System.out.print("Enter destination account number: ");
                        int toAccount = scanner.nextInt();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        BankSystem.transferFunds(fromAccount, toAccount, transferAmount);
                    } else {
                        System.out.println("Incorrect access code. Operation cancelled.");
                    }
                    break;

                case 5:
                    if (verifyAccessCode(scanner)) {
                        System.out.print("Enter account number: ");
                        int accNumber = scanner.nextInt();
                        BankAccount account = BankSystem.findAccount(accNumber);
                        if (account != null) {
                            account.displayAccountDetails();
                        }
                    } else {
                        System.out.println("Incorrect access code. Operation cancelled.");
                    }
                    break;

                case 6:
                    BankSystem.displayAllAccounts(loggedInUser);
                    break;

                case 7:
                    System.out.println("Logging out and exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }
}
