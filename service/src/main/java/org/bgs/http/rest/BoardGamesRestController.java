package org.bgs.http.rest;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.BoardGamesCreateEditDto;
import org.bgs.dto.BoardGamesReadDto;
import org.bgs.service.BoardGamesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boardGames")
@RequiredArgsConstructor
public class BoardGamesRestController {

    private final BoardGamesService boardGamesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoardGamesReadDto> findAll() {
        return boardGamesService.findAll();
    }

    @GetMapping("/{id}")
    public BoardGamesReadDto findById(@PathVariable("id") Long id) {
        return boardGamesService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BoardGamesReadDto create(@RequestBody BoardGamesCreateEditDto boardGame) {
        return boardGamesService.create(boardGame);
    }

    @PutMapping("/{id}")
    public BoardGamesReadDto update(@PathVariable("id") Long id, @RequestBody BoardGamesCreateEditDto boardGame) {
        return boardGamesService.update(id, boardGame)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!boardGamesService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
