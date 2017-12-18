package com.sintra.loanapp.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;
import com.sintra.loanapp.services.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@ApiOperation(value = "List of customers requested for a loan",response = Iterable.class)
	  @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "List of customers successfully fetched"),
	            @ApiResponse(code = 500, message = "Generic Error"),
	            @ApiResponse(code = 404, message = "Requested resource not found")
	    })
	
	@RequestMapping(value="/customers/", method=RequestMethod.GET)
	public ResponseEntity<List<Customer>> listCustomers() {
		
		List<Customer> customerList = customerService.getAllCustomers();
			if(customerList.isEmpty()) {
				return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Search a customer by Id",response = Customer.class)
	@RequestMapping(value="/customers/{id}", method=RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
		return new ResponseEntity<Customer>(customerService.getCustomer(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Filter customers by Loan Type",response = Iterable.class)
	@RequestMapping(value = "/customers/filter", method=RequestMethod.GET)
	public ResponseEntity<List<Customer>> getFliteredCustomers(@RequestParam("loanType") String loanType) throws JsonGenerationException, JsonMappingException, IOException {
		
		LoanType loan = null;
		
		switch(loanType.toUpperCase())  {
			case "AUTOMOTIVE" : loan = LoanType.AUTOMOTIVE;
			break;
			case "CUSTOMER" : loan = LoanType.CUSTOMER;
			break;
			case "MORTGAGE" : loan = LoanType.MORTGAGE;
			break;
			default : loan = null;
			break;
		}

		List<Customer> filteredCustomers = customerService.filterCustomersByLoanType(loan);
			if(filteredCustomers.isEmpty()) {
				return new ResponseEntity<List<Customer>>( HttpStatus.NOT_FOUND);
			}
	
			return new ResponseEntity<List<Customer>>(filteredCustomers, HttpStatus.OK);
		
	}	
	
	@ApiOperation(value = "Add a customer", response = Customer.class)
	@RequestMapping(value="/customers", method=RequestMethod.POST)
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, HttpServletRequest request) {
		Customer cust = customerService.saveCustomer(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	}
	
	


}
