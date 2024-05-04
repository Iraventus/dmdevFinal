package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.Status;
import org.bgs.service.CartGoodsService;
import org.bgs.service.CustomerService;
import org.bgs.service.OrderGoodsService;
import org.bgs.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final CartGoodsService cartGoodsService;
    private final OrderGoodsService orderGoodsService;

    @GetMapping
    public String findAll(Principal principal, Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("goods", cartGoodsService.
                            findAllByUserId(customerService.findByLogin(principal.getName()).
                                    map(CustomerReadDto::getId).
                                    orElseThrow()));
                    model.addAttribute("totalPrice", cartGoodsService
                            .showAllGoodsInCart(customerService.findByLogin(principal.getName()).
                                    orElseThrow()).
                            stream()
                            .mapToInt(good -> good.getGoods().getPrice() * good.getTotalGoods())
                            .sum()
                    );
                    return "order/testOrder";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/successful")
    public String successfulOrder(Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "order/successfulOrder";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {

        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("goods", orderGoodsService.findAllByOrderId(id));
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/current-orders")
    public String findAllOrdersByUser(Principal principal, Model model) {
        CustomerReadDto customer = customerService.findByLogin(principal.getName())
                .orElseThrow();
        model.addAttribute(
                "paidOrders", orderService.findAllByUserLoginAndStatus(customer.getLogin(), Status.PAID));
        model.addAttribute(
                "reservedOrders", orderService.findAllByUserLoginAndStatus(customer.getLogin(), Status.RESERVED));
        return "order/customerOrders";
    }

    @PostMapping("/create-order")
    public String createOrder(Principal principal, RedirectAttributes redirectAttributes) {
        CustomerReadDto customer = customerService.findByLogin(principal.getName())
                .orElseThrow();
        var order = orderService.createOrder(customer);
        redirectAttributes.addAttribute("id", order.getId());
        return "redirect:/orders";
    }

    @PostMapping("/reserved")
    public String createReservedOrder(Principal principal,
                                      Long orderId,
                                      RedirectAttributes redirectAttributes) {
        CustomerReadDto customer = customerService.findByLogin(principal.getName())
                .orElseThrow();
        orderService.setStatus(orderId, Status.RESERVED);
        cartGoodsService.findAllByUserId(customer.getId())
                .forEach(cartGoodsReadDto ->
                        orderGoodsService.createCardGoodsOrder(orderId, cartGoodsReadDto.getId()));
        redirectAttributes.addAttribute("id", orderId);
        return "redirect:/orders/successful";
    }

    @PostMapping("/paid")
    public String createPaidOrder(Principal principal,
                                  Long orderId,
                                  RedirectAttributes redirectAttributes) {
        CustomerReadDto customer = customerService.findByLogin(principal.getName())
                .orElseThrow();
        orderService.setStatus(orderId, Status.PAID);
        cartGoodsService.findAllByUserId(customer.getId())
                .forEach(cartGoodsReadDto ->
                        orderGoodsService.createCardGoodsOrder(orderId, cartGoodsReadDto.getId()));
        redirectAttributes.addAttribute("id", orderId);
        return "redirect:/orders/successful";
    }
}
