package service;

import entity.HousingObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.HousingObjectRepository;

import java.util.List;

/**
 * Imports: Necessary classes and annotations are imported, including HousingObject, ApplicationScoped, Inject, Transactional, and HousingObjectRepository.
 * Class Annotation: The @ApplicationScoped annotation indicates that this service is a CDI (Contexts and Dependency Injection) bean with application scope.
 * Dependency Injection: The HousingObjectRepository is injected into the service using the @Inject annotation.
 * Methods:
 * getAllHousingObjects(): Retrieves a list of all HousingObject entities from the repository.
 * getHousingObject(long id): Retrieves a specific HousingObject by its ID.
 * createHousingObject(HousingObject housingObject): Persists a new HousingObject entity to the repository within a transactional context.
 */
@ApplicationScoped
public class HousingObjectService {

    @Inject
    HousingObjectRepository housingObjectRepository;

    public List<HousingObject> getAllHousingObjects() {
        return housingObjectRepository.listAll();
    }

    public HousingObject getHousingObject(long id) {
        return housingObjectRepository.findById(id);
    }

    @Transactional
    public HousingObject createHousingObject(HousingObject housingObject) {
        housingObjectRepository.persist(housingObject);
        return housingObject;
    }

    /**
     * The method is annotated with @Transactional, indicating that it should be executed within a transactional context.
     * The method takes two parameters: id (the ID of the HousingObject to update) and housingObject (the new data for the HousingObject).
     * It retrieves the existing HousingObject from the repository using the provided id.
     * If the HousingObject with the given id does not exist, the method returns null.
     * If the HousingObject exists, it updates the fields of the existing HousingObject with the values from the provided housingObject, but only if the provided values are not null or zero (for numberOfApartments).
     * The updated HousingObject is then persisted back to the repository.
     * Finally, the method returns the updated HousingObject.
     */
    @Transactional
    public HousingObject updateHousingObject(long id, HousingObject housingObject) {
        HousingObject existingHousingObject = housingObjectRepository.findById(id);
        if (existingHousingObject == null) {
            return null;
        }
        if (housingObject.getName() != null) {
            existingHousingObject.setName(housingObject.getName());
        }
        if (housingObject.getStreet() != null) {
            existingHousingObject.setStreet(housingObject.getStreet());
        }
        if (housingObject.getCity() != null) {
            existingHousingObject.setCity(housingObject.getCity());
        }
        if (housingObject.getState() != null) {
            existingHousingObject.setState(housingObject.getState());
        }
        if (housingObject.getZipCode() != null) {
            existingHousingObject.setZipCode(housingObject.getZipCode());
        }
        if (housingObject.getNumberOfApartments() != 0) {
            existingHousingObject.setNumberOfApartments(housingObject.getNumberOfApartments());
        }
        housingObjectRepository.persist(existingHousingObject);
        return existingHousingObject;
    }

   /**
    * The deleteHousingObject method is responsible for deleting a HousingObject entity from the repository
    * The method is annotated with @Transactional, indicating that it should be executed within a transactional context.
    * The method takes a single parameter, id, which is the ID of the HousingObject to be deleted.
    * It retrieves the HousingObject from the repository using the provided id.
    * If the HousingObject with the given id does not exist, the method returns false.
    * If the HousingObject exists, it deletes the HousingObject from the repository.
    * Finally, the method returns true to indicate that the deletion was successful.
    */
    @Transactional
    public boolean deleteHousingObject(long id) {
        HousingObject housingObject = housingObjectRepository.findById(id);
        if (housingObject == null) {
            return false;
        }
        housingObjectRepository.delete(housingObject);
        return true;
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur & Zohal Mohammadi
 */