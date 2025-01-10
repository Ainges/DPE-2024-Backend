package service;

import entity.Invoice;
import repository.InvoiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoiceCategorySumService {

    @Inject
    InvoiceRepository invoiceRepository;

    /**
     * Calculates the total sum of invoices for a given category by ID.
     *

     * @return the total sum of invoice amounts for the given category
     */
    //basierend auf dem Housingobject und pro CategoryID und pro Jahr

    //public double getCategoryTotalSumById(long categoryId) {
       // List<Invoice> invoices = invoiceRepository.find("invoiceCategory.invoiceCategoryId", categoryId).list();
        //return invoices.stream().mapToDouble(Invoice::getInvoiceAmount).sum();
   // }
    public Map<Long, Double> getCategoryTotalSumByHousingObjectAndYear(long housingObjectId, long invoiceCategoryId, int year) {
        // Finde alle Rechnungen basierend auf HousingObjectId, CategoryId und Jahr
        List<Invoice> invoices = invoiceRepository.find(
                "housingObject.housingObjectId = ?1 AND invoiceCategory.invoiceCategoryId = ?2 AND EXTRACT(YEAR FROM invoiceDate) = ?3",
                housingObjectId, invoiceCategoryId, year
        ).list();

        return invoices.stream()
                .collect(Collectors.groupingBy(
                        invoice -> invoice.getInvoiceCategory().getInvoiceCategoryId(),
                        Collectors.summingDouble(Invoice::getInvoiceAmount)
                ));
    }
}
/**
 * End
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */