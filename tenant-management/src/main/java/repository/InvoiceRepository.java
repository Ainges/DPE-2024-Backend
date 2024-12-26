/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package repository;

import entity.Invoice;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */