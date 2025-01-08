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
     * Calculates the total sum of invoices for a given category by ID.
     *
     * @param categoryId the ID of the invoice category
     * @return the total sum of invoice amounts for the given category
     */
    public double getCategoryTotalSumById(long categoryId) {
        List<Invoice> invoices = invoiceRepository.find("invoiceCategory.invoiceCategoryId", categoryId).list();
        return invoices.stream().mapToDouble(Invoice::getInvoiceAmount).sum();
    }
}
/**
 * End
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */