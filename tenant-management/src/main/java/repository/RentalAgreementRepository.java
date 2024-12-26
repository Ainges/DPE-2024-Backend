/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package repository;

import entity.RentalAgreement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RentalAgreementRepository implements PanacheRepository<RentalAgreement> {
    //Will be implemented by Quarkus
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */