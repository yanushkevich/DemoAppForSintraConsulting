package com.sintra.loanapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.LoanType;


public interface CustomerRepository extends CrudRepository<Customer, Long>{

	List<Customer> findAllCustomersByLoanLoanType(LoanType loanType);
	
	

}
