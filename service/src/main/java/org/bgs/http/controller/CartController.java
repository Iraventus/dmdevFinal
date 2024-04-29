package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CartGoodsReadDto;
import org.bgs.dto.CustomerReadDto;
import org.bgs.service.CartGoodsService;
import org.bgs.service.CustomerService;
import org.bgs.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CUSTOMER')")
public class CartController {

    private final CustomerService customerService;
    private final CartGoodsService cartGoodsService;
    private final OrderService orderService;

    @GetMapping
    public String userCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        CustomerReadDto customer = customerService.findByLogin(userDetails.getUsername())
                .orElseThrow();
        model.addAttribute("cartGoods", cartGoodsService.showAllGoodsInCart(customer));
        model.addAttribute("totalPrice", cartGoodsService
                .showAllGoodsInCart(customer).stream()
                .mapToInt(good -> good.getGoods().getPrice() * good.getTotalGoods())
                .sum()
        );
        model.addAttribute("goodsIds", cartGoodsService.showAllGoodsInCart(customer)
                .stream().mapToLong(CartGoodsReadDto::getId));
        return "cart/cart";
    }

    @PostMapping
    public String deletePosition(@AuthenticationPrincipal UserDetails userDetails, Long goodId) {
        CustomerReadDto customer = customerService.findByLogin(userDetails.getUsername())
                .orElseThrow();
        cartGoodsService.deletePosition(goodId, customer);
        return "redirect:/cart";
    }
}
