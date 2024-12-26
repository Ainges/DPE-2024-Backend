/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package repository;

import entity.InvoiceCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvoiceCategoryRepository implements PanacheRepository<InvoiceCategory> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */