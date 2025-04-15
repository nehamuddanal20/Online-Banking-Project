package com.Banking_System;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingServiceImpl implements BankingService {

    @Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO Customer (customerID, name, address, contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getContact());
            stmt.executeUpdate();
            System.out.println("✅ Customer added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    @Override
    public void addAccount(Account account) {
        String query = "INSERT INTO Account (accountID, customerID, type, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, account.getAccountID());
            stmt.setInt(2, account.getCustomerID());
            stmt.setString(3, account.getType());
            stmt.setDouble(4, account.getBalance());
            stmt.executeUpdate();
            System.out.println("✅ Account added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding account: " + e.getMessage());
        }
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO Transactions (accountID, type, amount) VALUES (?, ?, ?)";
        String updateBalance = "UPDATE Account SET balance = balance + ? WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement updateStmt = conn.prepareStatement(updateBalance)) {

            stmt.setInt(1, transaction.getAccountID());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.executeUpdate();

            double balanceChange = transaction.getType().equalsIgnoreCase("Deposit") ? transaction.getAmount() : -transaction.getAmount();
            updateStmt.setDouble(1, balanceChange);
            updateStmt.setInt(2, transaction.getAccountID());
            updateStmt.executeUpdate();

            System.out.println("✅ Transaction added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
    }

    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        String query = "INSERT INTO Beneficiary (beneficiaryID, customerID, name, accountNumber, bankDetails) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, beneficiary.getBeneficiaryID());
            stmt.setInt(2, beneficiary.getCustomerID());
            stmt.setString(3, beneficiary.getName());
            stmt.setString(4, beneficiary.getAccountNumber());
            stmt.setString(5, beneficiary.getBankDetails());
            stmt.executeUpdate();
            System.out.println("✅ Beneficiary added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding beneficiary: " + e.getMessage());
        }
    }

    @Override
    public Customer findCustomerById(int id) {
        String query = "SELECT * FROM Customer WHERE customerID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getInt("customerID"), rs.getString("name"), rs.getString("address"), rs.getString("contact"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding customer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Account findAccountById(int id) {
        String query = "SELECT * FROM Account WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("accountID"), rs.getInt("customerID"), rs.getString("type"), rs.getDouble("balance"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding account: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE accountID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                            rs.getInt("transactionID"),
                            rs.getInt("accountID"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getTimestamp("timestamp").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public List<Beneficiary> getBeneficiariesByCustomerId(int customerId) {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        String query = "SELECT * FROM Beneficiary WHERE customerID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    beneficiaries.add(new Beneficiary(
                            rs.getInt("beneficiaryID"),
                            rs.getInt("customerID"),
                            rs.getString("name"),
                            rs.getString("accountNumber"),
                            rs.getString("bankDetails")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving beneficiaries: " + e.getMessage());
        }
        return beneficiaries;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("customerID"), rs.getString("name"), rs.getString("address"), rs.getString("contact")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                accounts.add(new Account(rs.getInt("accountID"), rs.getInt("customerID"), rs.getString("type"), rs.getDouble("balance")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving accounts: " + e.getMessage());
        }
        return accounts;
    }
}
