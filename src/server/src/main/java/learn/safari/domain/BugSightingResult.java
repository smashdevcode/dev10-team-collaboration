package learn.safari.domain;

import learn.safari.models.BugSighting;

import java.util.ArrayList;
import java.util.List;

public class BugSightingResult {

    private ActionStatus status = ActionStatus.SUCCESS;
    private ArrayList<String> messages = new ArrayList<>();
    private BugSighting sighting;

    public ActionStatus getStatus() {
        return status;
    }

    public BugSighting getSighting() {
        return sighting;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setSighting(BugSighting sighting) {
        this.sighting = sighting;
    }

    public void addMessage(ActionStatus status, String message) {
        this.status = status;
        messages.add(message);
    }
}
