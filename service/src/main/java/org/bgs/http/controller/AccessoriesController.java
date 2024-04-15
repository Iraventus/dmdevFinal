package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.AccessoriesCreateEditDto;
import org.bgs.service.AccessoriesService;
import org.bgs.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoriesController {

    private final AccessoriesService accessoriesService;
    private final ProducerService producerService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("accessories", accessoriesService.findAll());
        return "accessory/accessories";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return accessoriesService.findById(id)
                .map(accessory -> {
                    model.addAttribute("accessory", accessory);
                    model.addAttribute("producers", producerService.findAll());
                    return "accessory/accessory";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String create(@ModelAttribute AccessoriesCreateEditDto accessory) {
        return "redirect:/accessories/" + accessoriesService.create(accessory).getId();
    }

    @GetMapping("/addingPosition")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String registration(Model model, @ModelAttribute("accessory") AccessoriesCreateEditDto accessory) {
        model.addAttribute("accessory", accessory);
        model.addAttribute("producers", producerService.findAll());
        return "accessory/addingPosition";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String update(@PathVariable("id") Long id, @ModelAttribute("accessory") AccessoriesCreateEditDto accessory) {
        return accessoriesService.update(id, accessory)
                .map(it -> "redirect:/accessories/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String delete(@PathVariable("id") Long id) {
        if (!accessoriesService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/accessories";
    }
}
