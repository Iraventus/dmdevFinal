package org.board_games_shop.repository;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.BoardGamesFilters;
import org.board_games_shop.entity.BoardGameTheme;
import org.board_games_shop.entity.goods.BoardGames;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.board_games_shop.entity.Localization.EN;

@RequiredArgsConstructor
public class BoardGamesIT extends BaseIT {

    private final BoardGamesRepository boardGamesRepository;

    @Test
    void checkFindBoardGamesByFilter() {
        BoardGamesFilters filter = new BoardGamesFilters(BoardGameTheme.AMERITRASH, EN);

        var boardGames = boardGamesRepository.findAllByFilter(filter);

        assertThat(boardGames.size()).isEqualTo(1);
        assertThat(boardGames.get(0).getId()).isEqualTo(5L);
    }

    @Test
    void checkFindBoardGamesByName() {
        var boardGames = boardGamesRepository.findByName("Gloomhaven").orElseThrow();

        assertThat(boardGames.getId()).isEqualTo(5L);
    }

    @Test
    void checkFindBoardGamesByNameContains() {
        var boardGames = boardGamesRepository.findAllByNameContains("ha");

        assertThat(boardGames.size()).isEqualTo(2);
        assertThat(boardGames.stream().map(BoardGames::getName).toList()).contains("Arkham Horror", "Gloomhaven");
    }
}
