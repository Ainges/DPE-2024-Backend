/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.InvoiceCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link InvoiceCategory} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class InvoiceCategoryRepository implements PanacheRepository<InvoiceCategory> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */