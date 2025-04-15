package com.Banking_System;

import java.util.List;
import java.util.Scanner;

public class BankingSystemApp {
    public static void main(String[] args) {
        BankingService service = (BankingService) new BankingServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Banking System ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Account");
            System.out.println("3. Add Transaction");
            System.out.println("4. Add Beneficiary");
            System.out.println("5. View Transactions by Account ID");
            System.out.println("6. View Beneficiaries by Customer ID");
            System.out.println("7. Find Customer by ID");
            System.out.println("8. Find Account by ID");
            System.out.println("9. List All Customers");
            System.out.println("10. List All Accounts");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add Customer
                    System.out.println("Enter Customer Details:");
                    System.out.print("Customer ID: ");
                    int customerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Contact: ");
                    String contact = scanner.nextLine();

                    Customer customer = new Customer(customerId, name, address, contact);
                    service.addCustomer(customer);
                    System.out.println("Customer added successfully!");
                    break;

                case 2:
                    // Add Account
                    System.out.println("Enter Account Details:");
                    System.out.print("Account ID: ");
                    int accountId = scanner.nextInt();
                    System.out.print("Customer ID: ");
                    int accCustomerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Account Type (Saving/Current): ");
                    String type = scanner.nextLine();
                    System.out.print("Initial Balance: ");
                    double balance = scanner.nextDouble();

                    Account account = new Account(accountId, accCustomerId, type, balance);
                    service.addAccount(account);
                    System.out.println("Account added successfully!");
                    break;

                case 3:
                    // Add Transaction
                    System.out.println("Enter Transaction Details:");
                    System.out.print("Account ID: ");
                    int transAccountId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Transaction Type (Deposit/Withdrawal): ");
                    String transType = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();

                    Transaction transaction = new Transaction(0, transAccountId, transType, amount, null);
                    service.addTransaction(transaction);
                    System.out.println("Transaction added successfully!");
                    break;

                case 4:
                    // Add Beneficiary
                    System.out.println("Enter Beneficiary Details:");
                    System.out.print("Beneficiary ID: ");
                    int beneficiaryId = scanner.nextInt();
                    System.out.print("Customer ID: ");
                    int benCustomerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String benName = scanner.nextLine();
                    System.out.print("Account Number: ");
                    String benAccountNumber = scanner.nextLine();
                    System.out.print("Bank Details: ");
                    String benBankDetails = scanner.nextLine();

                    Beneficiary beneficiary = new Beneficiary(beneficiaryId, benCustomerId, benName, benAccountNumber, benBankDetails);
                    service.addBeneficiary(beneficiary);
                    System.out.println("Beneficiary added successfully!");
                    break;

                case 5:
                    // View Transactions by Account ID
                    System.out.print("Enter Account ID: ");
                    int transViewAccountId = scanner.nextInt();
                    List<Transaction> transactions = service.getTransactionsByAccountId(transViewAccountId);
                    System.out.println("Transactions for Account ID " + transViewAccountId + ":");
                    for (Transaction t : transactions) {
                        System.out.println(t);
                    }
                    break;

                case 6:
                    // View Beneficiaries by Customer ID
                    System.out.print("Enter Customer ID: ");
                    int benViewCustomerId = scanner.nextInt();
                    List<Beneficiary> beneficiaries = service.getBeneficiariesByCustomerId(benViewCustomerId);
                    System.out.println("Beneficiaries for Customer ID " + benViewCustomerId + ":");
                    for (Beneficiary b : beneficiaries) {
                        System.out.println(b);
                    }
                    break;

                case 7:
                    // Find Customer by ID
                    System.out.print("Enter Customer ID: ");
                    int findCustomerId = scanner.nextInt();
                    Customer foundCustomer = service.findCustomerById(findCustomerId);
                    if (foundCustomer != null) {
                        System.out.println(foundCustomer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 8:
                    // Find Account by ID
                    System.out.print("Enter Account ID: ");
                    int findAccountId = scanner.nextInt();
                    Account foundAccount = service.findAccountById(findAccountId);
                    if (foundAccount != null) {
                        System.out.println(foundAccount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 9:
                    // List All Customers
                    List<Customer> customers = service.getAllCustomers();
                    System.out.println("All Customers:");
                    for (Customer c : customers) {
                        System.out.println(c);
                    }
                    break;

                case 10:
                    // List All Accounts
                    List<Account> accounts = service.getAllAccounts();
                    System.out.println("All Accounts:");
                    for (Account a : accounts) {
                        System.out.println(a);
                    }
                    break;

                case 11:
                    // Exit
                    System.out.println("Thank you for using the Banking System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
