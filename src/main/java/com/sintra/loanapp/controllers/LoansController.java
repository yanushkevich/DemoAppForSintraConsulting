package com.sintra.loanapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;
import com.sintra.loanapp.services.LoanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
// @RequestMapping(AttachmentController.BASE_URL)
public class LoansController {

	// public static final String BASE_URL = "/customers/{id}";

	private LoanService loanService;

	public LoansController(LoanService loanService) {
		this.loanService = loanService;
	}

	@ApiOperation(value = "View of all submitted loans",response = Iterable.class)
	  @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "List of loans successfully fetched"),
	            @ApiResponse(code = 500, message = "Generic Error"),
	            @ApiResponse(code = 404, message = "Requested resource not found")
	    })
	
	@RequestMapping(value= "/loans", method=RequestMethod.GET)
	public ResponseEntity<List<Loan>> getAllLoans() {
		
		List<Loan> loans = loanService.getAllLoans();
			if(loans.isEmpty()) {
				return new ResponseEntity<List<Loan>>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<List<Loan>>(loans, HttpStatus.OK);
	}

	@ApiOperation(value = "Search a loan by Id",response = Loan.class)
	@RequestMapping(value = "/loans/{id}", method = RequestMethod.GET)
	public ResponseEntity<Loan> getLoan(@PathVariable("id") Long id) {
		return new ResponseEntity<Loan>(loanService.getLoan(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Filter loans by Loan Type",response = Iterable.class)
	@RequestMapping(value = "loans/filter", method = RequestMethod.GET)
	public ResponseEntity<List<Loan>> filterByLoanType(@RequestParam("loanType") String loanType) {

		LoanType loanT = null;

		switch (loanType.toUpperCase()) {
		case "AUTOMOTIVE":
			loanT = LoanType.AUTOMOTIVE;
			break;
		case "CUSTOMER":
			loanT = LoanType.CUSTOMER;
			break;
		case "MORTGAGE":
			loanT = LoanType.MORTGAGE;
			break;
		default:
			loanT = null;
			break;
		}

		List<Loan> filteredLoans = loanService.filterByLoanType(loanT);
		if (filteredLoans.isEmpty()) {
			return new ResponseEntity<List<Loan>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Loan>>(filteredLoans, HttpStatus.OK);

	}
	
	@ApiOperation(value = "Update a loan", response = Loan.class)
	@PatchMapping("loans/{id}")
	public ResponseEntity<Loan> patchLoan(@PathVariable("id") Long id, @Valid @RequestBody Loan loan) {
		return new ResponseEntity<Loan>(loanService.patchLoan(id, loan), HttpStatus.OK);
	}

	@ApiOperation(value = "Add a loan", response = Loan.class)
	@RequestMapping(value = "customers/{id}/loans", method = RequestMethod.POST)
	public ResponseEntity<Loan> createLoan(@PathVariable("id") Long customerId, @Valid @RequestBody Loan loan) {

		Loan newLoan = loanService.createLoan(customerId, loan);

		return new ResponseEntity<Loan>(newLoan, HttpStatus.CREATED);

	}

}
