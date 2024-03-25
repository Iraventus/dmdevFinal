package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.dto.BoardGamesFilters;
import org.boardGamesShop.entity.BoardGameTheme;
import org.boardGamesShop.entity.goods.BoardGames;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.boardGamesShop.entity.Localization.EN;

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
    void checkPageable() {
        var pageable = PageRequest.of(0, 2, Sort.by("name"));

        var slice = boardGamesRepository.findAllBy(pageable);

        assertThat(slice.getSize()).isEqualTo(2);
        assertThat(slice.stream().map(BoardGames::getName).toList()).containsExactly("Arkham Horror", "Gloomhaven");
        while (slice.hasNext()) {
            slice = boardGamesRepository.findAllBy(slice.nextPageable());
            assertThat(slice.getSize()).isEqualTo(2);
            assertThat(slice.stream().map(BoardGames::getName).toList()).containsExactly("Mage-Knight");
        }
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
