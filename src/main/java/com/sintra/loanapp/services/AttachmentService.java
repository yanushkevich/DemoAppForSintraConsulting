package com.sintra.loanapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.sintra.loanapp.model.CustomerAttachment;

public interface AttachmentService {
	
	List<CustomerAttachment> deleteAttachment(Long loanId, Long documentId);
	InputStreamResource downloadAttachment(Long loanId, Long documentId) throws FileNotFoundException;
	List<CustomerAttachment> uploadAttachment(Long id, MultipartFile file) throws IOException;
	List<CustomerAttachment> getAllLoanDocs(Long loanId);

}
