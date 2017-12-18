package com.sintra.loanapp.bootstrap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sintra.loanapp.model.Customer;
import com.sintra.loanapp.model.CustomerAttachment;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.model.LoanType;
import com.sintra.loanapp.repositories.CustomerRepository;
import com.sintra.loanapp.repositories.LoanRepository;


@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    CustomerRepository custRepo;
    LoanRepository loanrepo;
    
    @Autowired
    Environment environment;

    public DevBootstrap(CustomerRepository custRepo, LoanRepository loanrepo) {
       
        this.custRepo = custRepo;
        this.loanrepo = loanrepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){
    	
    	 int len = "byteString".length();
    	    byte[] data = new byte[len];

    	Customer customer = new Customer();
    	customer.setName("anton");
    	customer.setSurname("yanushkevich");
    	customer.setAddress("3101 N Woods Ln");
    	customer.setPhone("6469644730");
    	customer.setDob("17-05-1989");
    	customer.setFiscalCode("123-a34-aa-3n");
    	
    	
    	Loan loan = new Loan(LoanType.CUSTOMER, "123", "1", customer);
    	if (loan.getDocuments() == null)
    		loan.setDocuments(new ArrayList<CustomerAttachment>());
    	if (customer.getLoan() == null)
    		customer.setLoan(new HashSet<Loan>());
    	
    	loan.getDocuments().add(new CustomerAttachment(data, "testDocument", loan));
    	customer.getLoan().add(loan);
    	custRepo.save(customer);
    	System.out.println("Customer Id : " + customer.getCustomer_id());
    	getActiveProfiles();
    
      
    }
   
 
    public void getActiveProfiles() {
        for (final String profileName : environment.getActiveProfiles()) {
            System.out.println("Currently active profile - " + profileName);
        }   
    }
}
