package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.boardGamesShop.entity.BoardGameTheme;
import org.boardGamesShop.entity.Localization;
import org.boardGamesShop.entity.goods.BoardGames;
import org.boardGamesShop.dto.BoardGamesFilters;
import org.boardGamesShop.entity.goods.Goods;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public class BoardGamesIT extends BaseIT {

    private final BoardGamesRepository boardGamesRepository;

    @Test
    void checkFindBoardGamesById() {
        var boardGamesWithName = boardGamesRepository.findByName(entityManager, "Gloomhaven").orElseThrow();

        BoardGames boardGames = boardGamesRepository.findById(boardGamesWithName.getId()).orElseThrow();

        Assertions.assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
        assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
        assertThat(boardGames.getPrice()).isEqualTo(15000);
        assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkFindBoardGamesByName() {
        var boardGames = boardGamesRepository.findByName(entityManager, "Gloomhaven").orElseThrow();

        Assertions.assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
        assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
        assertThat(boardGames.getPrice()).isEqualTo(15000);
        Assertions.assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkFindBoardGamesByFilter() {
        BoardGamesFilters filter = BoardGamesFilters.builder()
                .localization(Localization.EN)
                .build();

        List<BoardGames> boardGames = boardGamesRepository.findByFilter(entityManager, filter);

        assertThat(boardGames.size()).isEqualTo(2);
    }

    @Test
    void checkBoardGameCreation() {
        var boardGames = getBoardGame();

        BoardGames boardGamesSaved = boardGamesRepository.save(boardGames);
        entityManager.clear();

        Assertions.assertThat(boardGamesRepository.findById(boardGamesSaved.getId()).orElseThrow()).isEqualTo(
                boardGames);
        Assertions.assertThat(
                boardGamesRepository.findById(boardGamesSaved.getId()).orElseThrow().getClass()).isEqualTo(
                BoardGames.class);
    }

    @Test
    void checkGoodsUpdate() {
        var boardGames = (BoardGames) boardGamesRepository.findByName(entityManager, "Gloomhaven")
                .orElseThrow();

        boardGames.setContents("play with pleasure");
        boardGamesRepository.update(boardGames);
        entityManager.clear();
        BoardGames boardGames1 = boardGamesRepository.findById(boardGames.getId()).orElseThrow();

        assertThat(boardGames1.getContents()).isEqualTo("play with pleasure");
    }

    @Test
    void checkGoodsDeletion() {
        var goods = getBoardGame();

        boardGamesRepository.save(goods);
        entityManager.clear();
        boardGamesRepository.delete(goods);

        assertNull(boardGamesRepository.findById(goods.getId()).orElse(null));
    }

    @Test
    void checkAllGoodsList() {
        List<BoardGames> goods = boardGamesRepository.findAll();

        assertThat(goods.size()).isEqualTo(4);
        assertThatList(goods.stream().map(Goods::getName).toList())
                .contains("Arkham Horror", "Gloomhaven", "Mage-Knight", "Euthia");
    }

    private BoardGames getBoardGame() {
        return BoardGames.builder()
                .name("someName")
                .localization(Localization.FR)
                .quantity(1)
                .boardGameTheme(BoardGameTheme.COOP)
                .build();
    }
}
