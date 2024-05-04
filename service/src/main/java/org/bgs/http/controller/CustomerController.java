package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerCreateEditDto;
import org.bgs.dto.UserFilter;
import org.bgs.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String findAll(Model model, UserFilter filter) {
        model.addAttribute("customers", customerService.findAll(filter));
        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/customer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("customer") CustomerCreateEditDto customer) {
        model.addAttribute("customer", customer);
        return "customer/registration";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated CustomerCreateEditDto customer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/customers/registration";
        }
        customerService.create(customer);
        return "redirect:/login";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated CustomerCreateEditDto customer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/customers/{id}";
        }
        return customerService.update(id, customer)
                .map(it -> "redirect:/customers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!customerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/customers";
    }
}
