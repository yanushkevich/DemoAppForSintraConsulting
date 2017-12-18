package com.sintra.loanapp.services;

import java.util.List;

import javax.validation.Valid;

import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;

public interface LoanService {
	
	List<Loan> getAllLoans();
	List<Loan> filterByLoanType(LoanType loanType);
	Loan getLoan(Long id);
	void updateLoan(Long id, Loan loan);
	Loan patchLoan(Long id, Loan loan);
	Loan createLoan(Long customerId, @Valid Loan loan);

}
