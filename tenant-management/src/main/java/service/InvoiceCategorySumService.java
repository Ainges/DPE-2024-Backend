/**
 * Service class for calculating the total sum of invoices by category for a specific housing object and year.
 * <p>
 * This service provides a method to calculate the total sum of invoices for a given housing object,
 * invoice category, year, and relevance for the annual statement.
 *
 * @see Invoice
 * @see InvoiceRepository
 * <p>
 * Authors:
 * - Zohal Mohammadi
 * - GitHub Copilot
 */
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
     * Calculates the total sum of invoices for a specific housing object, invoice category, year,
     * and relevance for the annual statement.
     *
     * @param housingObjectId            the ID of the housing object
     * @param invoiceCategoryId          the ID of the invoice category
     * @param year                       the year for which the total sum is calculated
     * @param relevantForAnnualStatement indicates if the invoice is relevant for the annual statement
     * @return the total sum of invoices
     */
    public Double getCategoryTotalSumByHousingObjectAndYear(long housingObjectId, long invoiceCategoryId, int year, boolean relevantForAnnualStatement) {
        // Find all invoices based on housingObjectId, categoryId, year, and relevance for the annual statement
        List<Invoice> invoices = invoiceRepository.find(
                "housingObject.housingObjectId = ?1 AND invoiceCategory.invoiceCategoryId = ?2 AND EXTRACT(YEAR FROM invoiceDate) = ?3 AND relevantForAnnualStatement = ?4",
                housingObjectId, invoiceCategoryId, year, relevantForAnnualStatement
        ).list();

        Double invoiceCategorySum = 0.0;
        for (Invoice invoice : invoices) {
            invoiceCategorySum += invoice.getInvoiceAmount();
        }

        return invoiceCategorySum;
    }
}
