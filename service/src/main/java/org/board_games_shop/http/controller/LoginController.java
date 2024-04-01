package org.board_games_shop.http.controller;

import org.board_games_shop.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto) {
        return "redirect:/login";
    }
}

