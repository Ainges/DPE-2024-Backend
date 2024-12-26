/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package repository;

import entity.Tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TenantRepository implements PanacheRepository<Tenant> {
    //Will be implemented by Quarkus
}


/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */