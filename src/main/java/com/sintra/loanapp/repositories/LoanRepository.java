package com.sintra.loanapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;


public interface LoanRepository extends CrudRepository<Loan, Long>{
	
	List<Loan> findByLoanType(LoanType loanType);
	

}
