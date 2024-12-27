/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package repository;

import entity.RentalAgreement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing {@link RentalAgreement} entities.
 * This repository is implemented by Quarkus.
 */
@ApplicationScoped
public class RentalAgreementRepository implements PanacheRepository<RentalAgreement> {
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */