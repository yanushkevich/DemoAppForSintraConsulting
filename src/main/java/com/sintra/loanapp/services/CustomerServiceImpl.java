package com.sintra.loanapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sintra.loanapp.exceptions.EntityNotFoundException;
import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;
import com.sintra.loanapp.repositories.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = {"customers"})
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
		
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	@Cacheable
	public List<Customer> getAllCustomers() {
		
		List<Customer> customerList  = (List<Customer>) customerRepository.findAll();
		
		return customerList;
	}

	@Override
	public List<Customer> filterCustomersByLoanType(LoanType loanType) {
		
		
		List<Customer> custList = customerRepository.findAllCustomersByLoanLoanType(loanType);
		List<Customer> filteredList = new ArrayList<>();
		for (Customer cust : custList) {
			Set<Loan> loan = cust.getLoan();
			cust.setLoan(loan.stream().filter(l -> l.getLoanType() == loanType)
					.collect(Collectors.toSet()));
			filteredList.add(cust);
		}
		
		return filteredList;
			
	} 

	@Override
	public Customer getCustomer(Long id) {
		
		Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		return customer;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		Customer cust = customerRepository.save(customer);
		return cust;
	}

}
