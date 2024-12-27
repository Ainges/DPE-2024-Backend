/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.Invoice;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link Invoice} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */