package com.sintra.loanapp.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sintra.loanapp.model.CustomerAttachment;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.services.AttachmentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(AttachmentController.BASE_URL)
public class AttachmentController {
	
	public static final String BASE_URL = "/loans/{loanId}/document/";
	
	public AttachmentService attachmentService;
	
	public AttachmentController(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	
	@ApiOperation(value = "Add an attachment to customer's loan")
	  @ApiResponses(value = {
	            @ApiResponse(code = 201, message = "Attachment added"),
	            @ApiResponse(code = 500, message = "Generic Error"),
	            @ApiResponse(code = 404, message = "Requested resource not found")
	    })
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public ResponseEntity<Void> addDocument(@PathVariable("loanId") Long loanId, @RequestParam("file") MultipartFile file) throws IOException {
		attachmentService.uploadAttachment(loanId, file);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Delete an attachment from loan")
	@RequestMapping(value = "/delete", method=RequestMethod.DELETE)
	public  ResponseEntity<List<CustomerAttachment>> deleteDocument(@PathVariable("loanId") Long loanId, @RequestParam("document") Long documentId){
		List<CustomerAttachment> list = attachmentService.deleteAttachment(loanId, documentId);
		
		return new ResponseEntity<List<CustomerAttachment>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Download a document")
	@RequestMapping(value = "/{documentId}", method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable("loanId") Long loanId, @PathVariable("documentId") Long documentId) throws FileNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", attachmentService.downloadAttachment(loanId, documentId).getFilename());
		return new ResponseEntity<InputStreamResource>(attachmentService.downloadAttachment(loanId, documentId), headers,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get all loan document")
	@RequestMapping(value = "/document/", method=RequestMethod.GET)
	public ResponseEntity<List<CustomerAttachment>> getLoanDocs(@PathVariable("loanId") Long loanId) throws FileNotFoundException {
		List<CustomerAttachment> list = attachmentService.getAllLoanDocs(loanId);
		return new ResponseEntity<List<CustomerAttachment>>(list,HttpStatus.OK);
	}

}
