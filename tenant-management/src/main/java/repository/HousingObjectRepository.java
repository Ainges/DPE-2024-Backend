/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package repository;

import entity.HousingObject;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HousingObjectRepository implements PanacheRepository<HousingObject> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */