package service;

import dto.InvoiceDTO;
import entity.HousingObject;
import entity.Invoice;
import entity.InvoiceCategory;
import exception.InvoiceServiceException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.HousingObjectRepository;
import repository.InvoiceCategoryRepository;
import repository.InvoiceRepository;

import java.util.List;

@ApplicationScoped
public class InvoiceService {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    HousingObjectRepository housingObjectRepository;

    @Inject
    InvoiceCategoryRepository invoiceCategoryRepository;

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {

        // Check if an invoice with the same externalInvoiceNumber and receiver already exists
        List<Invoice> existingInvoices = invoiceRepository.find("externalInvoiceNumber = ?1 and receiver = ?2", invoiceDTO.getExternalInvoiceNumber(), invoiceDTO.getReceiver()).list();

        if (!existingInvoices.isEmpty()) {
            throw new InvoiceServiceException("An invoice with the external invoice number " + invoiceDTO.getExternalInvoiceNumber() + " and the receiver " + invoiceDTO.getReceiver() + " already exists.");
        }

        InvoiceCategory invoiceCategory = invoiceCategoryRepository.findById(Long.parseLong(invoiceDTO.getInvoiceCategoryId()));
        HousingObject housingObject = housingObjectRepository.findById(Long.parseLong(invoiceDTO.getHousingObjectId()));

        if(invoiceCategory == null) {
            throw new InvoiceServiceException("The invoice category with the ID " + invoiceDTO.getInvoiceCategoryId() + " does not exist.");
        }
        if(housingObject == null) {
            throw new InvoiceServiceException("The housing object with the ID " + invoiceDTO.getHousingObjectId() + " does not exist.");
        }

        Invoice invoice = new Invoice(invoiceDTO.getInvoiceDate(), invoiceDTO.getInvoiceAmount(), invoiceDTO.getDescription(), invoiceDTO.getStatus(), invoiceDTO.getReceiver(), invoiceDTO.getReceiverIban(), invoiceDTO.getReceiverBic(), invoiceDTO.getExternalInvoiceNumber(), invoiceDTO.getCurrency(),Boolean.parseBoolean(invoiceDTO.getRelevantForAnnualStatement()), invoiceCategory, housingObject);
        invoiceRepository.persist(invoice);

        return invoice;
    }
}