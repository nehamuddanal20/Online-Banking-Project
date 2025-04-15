package com.Banking_System;

import java.util.List;

public interface BankingService {
    void addCustomer(Customer customer);
    void addAccount(Account account);
    void addTransaction(Transaction transaction);
    void addBeneficiary(Beneficiary beneficiary);
    Customer findCustomerById(int id);
    Account findAccountById(int id);
    List<Transaction> getTransactionsByAccountId(int accountId);
    List<Beneficiary> getBeneficiariesByCustomerId(int customerId);
    List<Customer> getAllCustomers();
    List<Account> getAllAccounts();
}

