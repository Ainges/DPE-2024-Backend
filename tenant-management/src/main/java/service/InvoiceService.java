package service;

import dto.InvoiceCreateDto;
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

    public Invoice createInvoice(InvoiceCreateDto invoiceCreateDto) {

        // Check if an invoice with the same externalInvoiceNumber and receiver already exists
        List<Invoice> existingInvoices = invoiceRepository.find("externalInvoiceNumber = ?1 and receiver = ?2", invoiceCreateDto.getExternalInvoiceNumber(), invoiceCreateDto.getReceiver()).list();

        if (!existingInvoices.isEmpty()) {
            throw new InvoiceServiceException("An invoice with the external invoice number " + invoiceCreateDto.getExternalInvoiceNumber() + " and the receiver " + invoiceCreateDto.getReceiver() + " already exists.");
        }

        InvoiceCategory invoiceCategory = invoiceCategoryRepository.findById(Long.parseLong(invoiceCreateDto.getInvoiceCategoryId()));
        HousingObject housingObject = housingObjectRepository.findById(Long.parseLong(invoiceCreateDto.getHousingObjectId()));

        if(invoiceCategory == null) {
            throw new InvoiceServiceException("The invoice category with the ID " + invoiceCreateDto.getInvoiceCategoryId() + " does not exist.");
        }
        if(housingObject == null) {
            throw new InvoiceServiceException("The housing object with the ID " + invoiceCreateDto.getHousingObjectId() + " does not exist.");
        }

        Invoice invoice = new Invoice(invoiceCreateDto.getInvoiceDate(), invoiceCreateDto.getInvoiceAmount(), invoiceCreateDto.getDescription(), invoiceCreateDto.getStatus(), invoiceCreateDto.getReceiver(), invoiceCreateDto.getReceiverIban(), invoiceCreateDto.getReceiverBic(), invoiceCreateDto.getExternalInvoiceNumber(), invoiceCreateDto.getCurrency(), invoiceCategory, housingObject);
        invoiceRepository.persist(invoice);

        return invoice;
    }
}