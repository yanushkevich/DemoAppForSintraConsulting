package com.sintra.loanapp.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "customer_attachment")
public class CustomerAttachment {
	
	@ApiModelProperty(notes = "The autogenerated attachment ID")
	private Long customerDetailsId;
	@ApiModelProperty(notes = "The attachment")
	@Lob
	byte[] attachment;
	@ApiModelProperty(notes = "The attachment name")
	private String documentName;
	
	private Loan loan;
	
	public CustomerAttachment() {
		
	}
	
	public CustomerAttachment(byte[] attachment, String documentName) {
		this.attachment = attachment;
		this.documentName = documentName;		
	}
	
	public CustomerAttachment(byte[] attachment, String documentName, Loan loan) {
		this.attachment = attachment;
		this.documentName = documentName;		
		this.loan = loan;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return customerDetailsId;
	}

	public void setId(Long id) {
		this.customerDetailsId = id;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
	@JsonBackReference
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}


	public String getDocumentName() {
		return documentName;
	}


	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	@Override
	public String toString() {
		return "CustomerAttachment [customerDetailsId=" + customerDetailsId + ", attachment="
				+ Arrays.toString(attachment) + ", documentName=" + documentName + ", loan=" + loan + "]";
	}
	
	
	
	
	
	

}
