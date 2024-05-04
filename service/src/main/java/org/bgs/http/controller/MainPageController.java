package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.entity.Role;
import org.bgs.service.AccessoriesService;
import org.bgs.service.BoardGamesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.Objects;

@Controller
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {

    private final AccessoriesService accessoriesService;
    private final BoardGamesService boardGamesService;

    @GetMapping
    public String findAll(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream().map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.equals("MANAGER"))
                .findFirst().orElse(null);
        model.addAttribute("accessories", accessoriesService.findAll());
        model.addAttribute("boardGames", boardGamesService.findAll());
        if (Objects.equals(role, Role.MANAGER.name())) {
            return "mainPage/mainPageForManager";
        } else {
            return "mainPage/goods";
        }
    }
}
