/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.HousingObject;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link HousingObject} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class HousingObjectRepository implements PanacheRepository<HousingObject> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */