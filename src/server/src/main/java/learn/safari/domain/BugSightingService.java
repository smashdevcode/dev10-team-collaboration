package learn.safari.domain;

import learn.safari.data.BugOrderRepository;
import learn.safari.data.BugSightingRepository;
import learn.safari.models.BugOrder;
import learn.safari.models.BugSighting;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class BugSightingService {

    private final BugSightingRepository bugSightingRepository;
    private final BugOrderRepository bugOrderRepository;

    public BugSightingService(BugSightingRepository bugSightingRepository, BugOrderRepository bugOrderRepository) {
        this.bugSightingRepository = bugSightingRepository;
        this.bugOrderRepository = bugOrderRepository;
    }

    public List<BugSighting> findAll() {
        return bugSightingRepository.findAll();
    }

    public BugSighting findById(int sightingId) {
        return bugSightingRepository.findById(sightingId);
    }

    public List<BugOrder> findAllBugOrders() {
        return bugOrderRepository.findAll();
    }

    public BugSightingResult add(BugSighting sighting) {

        BugSightingResult result = validate(sighting);
        if (result.getStatus() != ActionStatus.SUCCESS) {
            return result;
        }

        BugSighting inserted = bugSightingRepository.add(sighting);
        if (inserted == null) {
            result.addMessage(ActionStatus.INVALID, "insert failed");
        } else {
            result.setSighting(inserted);
        }

        return result;
    }

    public BugSightingResult update(BugSighting sighting) {

        BugSightingResult result = validate(sighting);
        if (result.getStatus() != ActionStatus.SUCCESS) {
            return result;
        }

        if (!bugSightingRepository.update(sighting)) {
            result.addMessage(ActionStatus.NOT_FOUND, "sighting id `" + sighting.getSightingId() + "` not found.");
        }

        return result;

    }

    public BugSightingResult deleteById(int sightingId) {
        BugSightingResult result = new BugSightingResult();
        boolean deleted = bugSightingRepository.deleteById(sightingId);
        if (!deleted) {
            result.addMessage(ActionStatus.NOT_FOUND, "sighting id `" + sightingId + "` not found.");
        }
        return result;
    }

    private BugSightingResult validate(BugSighting sighting) {

        BugSightingResult result = new BugSightingResult();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BugSighting>> violations = validator.validate(sighting);

        for (ConstraintViolation<BugSighting> violation : violations) {
            result.addMessage(ActionStatus.INVALID, violation.getMessage());
        }

        return result;
    }
}
