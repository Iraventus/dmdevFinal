package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.service.AccessoriesService;
import org.bgs.service.BoardGamesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {

    private final AccessoriesService accessoriesService;
    private final BoardGamesService boardGamesService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("accessories", accessoriesService.findAll());
        model.addAttribute("boardGames", boardGamesService.findAll());
        return "mainPage/goods";
    }
}
