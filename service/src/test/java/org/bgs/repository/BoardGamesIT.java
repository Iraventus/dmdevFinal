package org.bgs.repository;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.BoardGamesFilters;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.goods.BoardGames;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bgs.entity.Localization.EN;

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
