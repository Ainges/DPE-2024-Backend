/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.AnnualStatement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link AnnualStatement} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class AnnualStatementRepository implements PanacheRepository<AnnualStatement> {
}