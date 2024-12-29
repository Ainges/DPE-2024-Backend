/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.StatementEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link StatementEntry} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class StatementEntryRepository implements PanacheRepository<StatementEntry> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */