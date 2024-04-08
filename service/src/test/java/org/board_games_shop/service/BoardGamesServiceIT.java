package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.BoardGamesCreateEditDto;
import org.board_games_shop.dto.BoardGamesReadDto;
import org.board_games_shop.entity.BoardGameTheme;
import org.board_games_shop.entity.Localization;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class BoardGamesServiceIT extends BaseIT {

    private static final Long GAME_1 = 4L;
    private final BoardGamesService boardGamesService;

    @Test
    void findAll() {
        List<BoardGamesReadDto> result = boardGamesService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<BoardGamesReadDto> maybeGame = boardGamesService.findById(GAME_1);
        assertTrue(maybeGame.isPresent());
        maybeGame.ifPresent(game -> assertEquals("Arkham Horror", game.getName()));
    }

    @Test
    void create() {
        BoardGamesCreateEditDto gameDto = new BoardGamesCreateEditDto(
                "Citadels",
                "battle for influence",
                3,
                1000,
                BoardGameTheme.ECONOMIC,
                Localization.EN,
                "100 CARDS",
                "CREATOR"
        );

        BoardGamesReadDto actualResult = boardGamesService.create(gameDto);

        assertEquals(gameDto.getName(), actualResult.getName());
        assertEquals(gameDto.getContents(), actualResult.getContents());
        assertEquals(gameDto.getBoardGameTheme(), actualResult.getBoardGameTheme());
        assertEquals(gameDto.getLocalization(), actualResult.getLocalization());
    }

    @Test
    void update() {
        BoardGamesCreateEditDto gameDto = new BoardGamesCreateEditDto(
                "Citadels",
                "battle for influence",
                3,
                1000,
                BoardGameTheme.ECONOMIC,
                Localization.EN,
                "100 CARDS",
                "CREATOR"
        );

        Optional<BoardGamesReadDto> actualResult = boardGamesService.update(GAME_1, gameDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(game -> {
            assertEquals(gameDto.getName(), game.getName());
            assertEquals(gameDto.getContents(), game.getContents());
            assertEquals(gameDto.getBoardGameTheme(), game.getBoardGameTheme());
            assertEquals(gameDto.getLocalization(), game.getLocalization());
        });
    }

    @Test
    void delete() {
        assertFalse(boardGamesService.delete(-124L));
        assertTrue(boardGamesService.delete(GAME_1));
    }
}
