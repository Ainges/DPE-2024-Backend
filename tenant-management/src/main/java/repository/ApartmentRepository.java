/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package repository;

import entity.Apartment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApartmentRepository implements PanacheRepository<Apartment> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */