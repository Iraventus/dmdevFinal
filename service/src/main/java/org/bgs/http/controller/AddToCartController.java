package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerReadDto;
import org.bgs.service.CartGoodsService;
import org.bgs.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/add-to-cart")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CUSTOMER')")
public class AddToCartController {

    private final CustomerService customerService;
    private final CartGoodsService cartGoodsService;

    @PostMapping
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails, Long goodId) {
        CustomerReadDto customer = customerService.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cartGoodsService.addToCart(goodId, customer);
        return "redirect:/main-page";
    }
}
