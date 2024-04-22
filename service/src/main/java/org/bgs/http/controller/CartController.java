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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CUSTOMER')")
public class CartController {

    private final CustomerService customerService;
    private final CartGoodsService cartGoodsService;

    @GetMapping
    public String userCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        CustomerReadDto customer = customerService.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("cartGoods", cartGoodsService.showAllGoodsInCart(customer));
        return "cart/cart";
    }
}
