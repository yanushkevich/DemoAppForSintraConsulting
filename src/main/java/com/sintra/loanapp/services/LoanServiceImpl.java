package com.sintra.loanapp.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sintra.loanapp.exceptions.EntityNotFoundException;
import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;
import com.sintra.loanapp.repositories.CustomerRepository;
import com.sintra.loanapp.repositories.LoanRepository;
import com.sun.jersey.api.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = {"loans"})
public class LoanServiceImpl implements LoanService {
	
	private LoanRepository loanRepository;
	private CustomerRepository customerRepository;
	
	public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository) {
		this.loanRepository = loanRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	@Cacheable
	public List<Loan> getAllLoans() {
		return  (List<Loan>) loanRepository.findAll();
	}

	@Override
	public List<Loan> filterByLoanType(LoanType loanType) {
		return loanRepository.findByLoanType(loanType);
	}

	@Override
	public Loan getLoan(Long id) {
		Optional<Loan> loanOptional = loanRepository.findById(id);
		
		if (!loanOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		
		return loanOptional.get();
		
	}

	@Override
	public void updateLoan(Long id, Loan loan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Loan patchLoan(Long id, Loan loan) {
		return loanRepository.findById(id).map(newLoan ->{
			
			if (loan.getLoanAmount() != null) {
				newLoan.setLoanAmount(loan.getLoanAmount());
			}
			
			if (loan.getRates() != null) {
				newLoan.setRates(loan.getRates());
			} 
			
			if(loan.getDocuments() != null) {
				newLoan.setDocuments(loan.getDocuments());
			}
			//Optional, as LoanType in View details grayed out and cannot be updated
			newLoan.setLoanType(loan.getLoanType());
			
			Loan lo = loanRepository.save(newLoan);
			
			return lo;
			
		}).orElseThrow(EntityNotFoundException::new);
		
	}

	@Override
	public Loan createLoan(Long customerId, @Valid Loan loan) {
		
		Customer customer = customerRepository.findById(customerId).get();
		loan.setCustomer(customer);
		loanRepository.save(loan);		
		return loan;
	}



}
