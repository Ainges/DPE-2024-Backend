/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.Tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link Tenant} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class TenantRepository implements PanacheRepository<Tenant> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */