package learn.safari.controllers;

import learn.safari.domain.BugSightingResult;
import learn.safari.domain.BugSightingService;
import learn.safari.models.BugSighting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BugSightingUIController {

    private final BugSightingService service;

    public BugSightingUIController(BugSightingService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String displayAll(Model model) {
        model.addAttribute("sightings", service.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String displayAdd(@ModelAttribute("sighting") BugSighting sighting, Model model) {
        model.addAttribute("orders", service.findAllBugOrders());
        return "form";
    }

    @PostMapping("/add")
    public String handleAdd(
            @ModelAttribute("sighting") @Valid BugSighting sighting,
            BindingResult result, Model model) {

        if (sighting != null && sighting.getOrder() != null && sighting.getOrder().getBugOrderId() <= 0) {
            result.addError(new FieldError("sighting", "order", "Select an Order."));
        }

        if (result.hasErrors()) {
            model.addAttribute("orders", service.findAllBugOrders());
            return "form";
        }

        BugSightingResult serviceResult = service.add(sighting);

        return "redirect:/";
    }

    @GetMapping("/edit/{sightingId}")
    public String displayEdit(@PathVariable int sightingId, Model model) {

        BugSighting sighting = service.findById(sightingId);
        if (sighting == null) {
            return "not-found";
        }

        model.addAttribute("sighting", sighting);
        model.addAttribute("orders", service.findAllBugOrders());

        return "form";
    }

    @PostMapping("/edit/*")
    public String handleEdit(
            @ModelAttribute("sighting") @Valid BugSighting sighting,
            BindingResult result, Model model) {

        if (sighting != null && sighting.getOrder() != null && sighting.getOrder().getBugOrderId() <= 0) {
            result.addError(new FieldError("sighting", "order", "Select an Order."));
        }

        if (result.hasErrors()) {
            model.addAttribute("orders", service.findAllBugOrders());
            return "form";
        }

        BugSightingResult serviceResult = service.update(sighting);

        return "redirect:/";
    }

    @GetMapping("/delete/{sightingId}")
    public String displayDelete(@PathVariable int sightingId, Model model) {

        BugSighting sighting = service.findById(sightingId);
        if (sighting == null) {
            return "not-found";
        }

        model.addAttribute("sighting", sighting);

        return "delete";
    }

    @PostMapping("/delete/{sightingId}")
    public String handleEdit(@PathVariable int sightingId) {
        // Don't really care about the outcome since there's
        // not much we can do about it.
        // It's likely a bad identifier.
        service.deleteById(sightingId);
        return "redirect:/";
    }

}
