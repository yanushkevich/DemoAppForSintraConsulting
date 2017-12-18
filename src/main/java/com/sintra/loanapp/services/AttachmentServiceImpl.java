package com.sintra.loanapp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sintra.loanapp.exceptions.EntityNotFoundException;
import com.sintra.loanapp.model.CustomerAttachment;
import com.sintra.loanapp.model.Loan;
import com.sintra.loanapp.repositories.CustomerAttachmentRepository;
import com.sintra.loanapp.repositories.LoanRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {

	private CustomerAttachmentRepository attachmentRepository;
	private LoanRepository loanRepository;

	public AttachmentServiceImpl(CustomerAttachmentRepository attachmentRepository, LoanRepository loanRepository) {
		this.attachmentRepository = attachmentRepository;
		this.loanRepository = loanRepository;
	}

	@Override
	public List<CustomerAttachment> deleteAttachment(Long loanId, Long documentId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(EntityNotFoundException::new);
		
		List<CustomerAttachment> list = loan.getDocuments().stream()
				.filter(doc -> doc.getId() != documentId)
				.collect(Collectors.toList());
			
	return list;

	}

	@Override
	@Transactional
	public InputStreamResource downloadAttachment(Long loanId, Long documentId) throws FileNotFoundException {

		Loan loan = loanRepository.findById(loanId).orElseThrow(EntityNotFoundException::new);
		CustomerAttachment customerDoc = loan.getDocuments().stream()
				.filter(doc -> doc.getId() == documentId).
				findFirst().orElseThrow(EntityNotFoundException::new);

		File file = null;

		if (customerDoc.getAttachment() != null) {
			file = new File(customerDoc.getDocumentName());
		}

		return new InputStreamResource(new FileInputStream(file));

	}

	@Override
	public List<CustomerAttachment> uploadAttachment(Long id, MultipartFile file) throws IOException {
		
		Loan loan = loanRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		loan.getDocuments().add(new CustomerAttachment(file.getBytes(), file.getName(), loan));
		return loan.getDocuments();

	}

	@Override
	public List<CustomerAttachment> getAllLoanDocs(Long loanId) {
		List<CustomerAttachment> list = loanRepository.findById(loanId).get().getDocuments();
		return list;
	}

}
