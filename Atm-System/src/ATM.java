import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ATM {
    private static Map<Integer, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        initializeAccounts();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the ATM.");
            int accountNumber = authenticate(scanner);
            if (accountNumber != -1) {
                BankAccount account = accounts.get(accountNumber);

                while (true) {
                    displayMenu();
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            checkBalance(account);
                            break;
                        case 2:
                            deposit(account, scanner);
                            break;
                        case 3:
                            withdraw(account, scanner);
                            break;
                        case 4:
                            System.out.println("Thank you for using the ATM.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid option. Please select a valid option.");
                    }
                }
            } else {
                System.out.println("Authentication failed. Please try again or exit.");
            }
        }
    }

    private static void initializeAccounts() {
        accounts.put(12345, new BankAccount("John Doe", 6789, 1000.0));
        // Add more accounts as needed
    }

    private static int authenticate(Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        BankAccount account = accounts.get(accountNumber);
        if (account != null && account.authenticate(pin)) {
            System.out.println("Authentication successful. Welcome, " + account.getOwnerName() + "!");
            return accountNumber;
        }
        return -1;
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private static void checkBalance(BankAccount account) {
        System.out.println("Account Balance: $" + account.getBalance());
    }

    private static void deposit(BankAccount account, Scanner scanner) {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        System.out.println("Deposited $" + amount);
    }

    private static void withdraw(BankAccount account, Scanner scanner) {
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (account.withdraw(amount)) {
            System.out.println("Withdrawn $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount. Withdrawal failed.");
        }
    }
}

class BankAccount {
    private String ownerName;
    private int pin;
    private double balance;

    public BankAccount(String ownerName, int pin, double initialBalance) {
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean authenticate(int enteredPin) {
        return pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
