package org.board_games_shop.http.controller;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CustomerCreateEditDto;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
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
    public String registration(Model model, @ModelAttribute("customer") CustomerCreateEditDto customer) {
        model.addAttribute("customer", customer);
        return "customer/registration";
    }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute CustomerCreateEditDto customer) {
        return "redirect:/customers/" + customerService.create(customer).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute CustomerCreateEditDto customer) {
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
