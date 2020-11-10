package learn.safari.controllers;

import learn.safari.domain.BugSightingResult;
import learn.safari.domain.BugSightingService;
import learn.safari.models.BugSighting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sighting")
@CrossOrigin(origins = {"http://localhost:3000"})
public class BugSightingController {

    private final BugSightingService service;

    public BugSightingController(BugSightingService service) {
        this.service = service;
    }

    @GetMapping
    public List<BugSighting> findAll() {
        return service.findAll();
    }

    @GetMapping("/{sightingId}")
    public ResponseEntity<BugSighting> findById(@PathVariable int sightingId) {
        BugSighting sighting = service.findById(sightingId);
        if (sighting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sighting);
    }

    @PostMapping
    public ResponseEntity<BugSighting> add(@RequestBody BugSighting sighting) {
        BugSightingResult result = service.add(sighting);
        return new ResponseEntity<>(result.getSighting(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/{sightingId}")
    public ResponseEntity<Void> update(@PathVariable int sightingId, @RequestBody BugSighting sighting) {
        if (sighting != null && sighting.getSightingId() != sightingId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        BugSightingResult result = service.update(sighting);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{sightingId}")
    public ResponseEntity<Void> delete(@PathVariable int sightingId) {
        BugSightingResult result = service.deleteById(sightingId);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    private HttpStatus getStatus(BugSightingResult result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }

}
