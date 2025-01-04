package service;

import entity.Invoice;
import repository.InvoiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class InvoiceCategorySumService {

    @Inject
    InvoiceRepository invoiceRepository;

    /**
     * Calculates the total sum of invoices for a given category.
     *
     * @param categoryName the name of the invoice category
     * @return the total sum of invoice amounts for the given category
     */
    public double getCategoryTotalSum(String categoryName) {
        List<Invoice> invoices = invoiceRepository.find("invoiceCategory.name", categoryName).list();
        return invoices.stream().mapToDouble(Invoice::getInvoiceAmount).sum();
    }
}