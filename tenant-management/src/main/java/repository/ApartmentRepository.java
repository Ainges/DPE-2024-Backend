/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.Apartment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link Apartment} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class ApartmentRepository implements PanacheRepository<Apartment> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */