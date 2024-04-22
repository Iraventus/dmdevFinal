package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ManagerCreateEditDto;
import org.bgs.dto.UserFilter;
import org.bgs.entity.Role;
import org.bgs.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/managers")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    public String findAll(Model model, UserFilter filter) {
        model.addAttribute("managers", managerService.findAll(filter));
        return "manager/managers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return managerService.findById(id)
                .map(manager -> {
                    model.addAttribute("manager", manager);
                    return "manager/manager";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("manager") ManagerCreateEditDto manager) {
        model.addAttribute("managers", manager);
        model.addAttribute("roles", Role.values());
        return "manager/registration";
    }

    @PostMapping
    public String create(@ModelAttribute ManagerCreateEditDto manager) {
        managerService.create(manager);
        return "redirect:/login";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute ManagerCreateEditDto manager) {
        return managerService.update(id, manager)
                .map(it -> "redirect:/managers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!managerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/managers";
    }
}
