package org.bgs.http.controller;

import lombok.RequiredArgsConstructor;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;
import org.bgs.dto.BoardGamesCreateEditDto;
import org.bgs.service.BoardGamesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/boardGames")
@RequiredArgsConstructor
public class BoardGamesController {

    private final BoardGamesService boardGamesService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("boardGames", boardGamesService.findAll());
        return "boardGame/boardGames";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return boardGamesService.findById(id)
                .map(boardGame -> {
                    model.addAttribute("boardGame", boardGame);
                    model.addAttribute("themes", BoardGameTheme.values());
                    model.addAttribute("localizations", Localization.values());
                    return "boardGame/boardGame";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String create(@ModelAttribute BoardGamesCreateEditDto boardGame) {
        return "redirect:/boardGames/" + boardGamesService.create(boardGame).getId();
    }

    @GetMapping("/addingPosition")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String registration(Model model, @ModelAttribute("boardGame") BoardGamesCreateEditDto boardGame) {
        model.addAttribute("boardGame", boardGame);
        model.addAttribute("themes", BoardGameTheme.values());
        model.addAttribute("localizations", Localization.values());
        return "boardGame/addingPosition";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String update(@PathVariable("id") Long id, @ModelAttribute("boardGame") BoardGamesCreateEditDto boardGame) {
        return boardGamesService.update(id, boardGame)
                .map(it -> "redirect:/boardGames/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String delete(@PathVariable("id") Long id) {
        if (!boardGamesService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/boardGames";
    }
}
