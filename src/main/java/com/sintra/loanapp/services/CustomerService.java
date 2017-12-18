package com.sintra.loanapp.services;

import java.util.List;
import java.util.Optional;

import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.LoanType;

public interface CustomerService {
	
	List<Customer> getAllCustomers();
	List<Customer> filterCustomersByLoanType(LoanType loanType);
	Customer getCustomer(Long id);
	Customer saveCustomer(Customer customer);
	

}
