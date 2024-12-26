/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package repository;

import entity.AnnualStatement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnnualStatementRepository implements PanacheRepository<AnnualStatement> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */