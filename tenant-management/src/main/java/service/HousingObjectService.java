package service;

import entity.HousingObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.HousingObjectRepository;

import java.util.List;

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