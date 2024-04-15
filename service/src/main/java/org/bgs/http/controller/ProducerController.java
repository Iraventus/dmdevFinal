package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ProducerCreateEditDto;
import org.bgs.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String findAll(Model model) {
        model.addAttribute("producers", producerService.findAll());
        return "producer/producers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return producerService.findById(id)
                .map(producer -> {
                    model.addAttribute("producer", producer);
                    return "producer/producer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String create(@ModelAttribute ProducerCreateEditDto producer) {
        return "redirect:/producers/" + producerService.create(producer).getId();
    }

    @GetMapping("/addingProducer")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String registration(Model model, @ModelAttribute("producer") ProducerCreateEditDto producer) {
        model.addAttribute("producer", producer);
        return "producer/addingProducer";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String update(@PathVariable("id") Long id, @ModelAttribute ProducerCreateEditDto producer) {
        return producerService.update(id, producer)
                .map(it -> "redirect:/producers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String delete(@PathVariable("id") Long id) {
        if (!producerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/producers";
    }
}
