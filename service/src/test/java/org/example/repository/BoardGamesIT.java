package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.dto.BoardGamesFilters;
import org.example.entity.BoardGameTheme;
import org.example.entity.Localization;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BoardGamesIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static BoardGamesRepository boardGamesRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    boardGamesRepository = context.getBean(BoardGamesRepository.class);
    session = context.getBean(EntityManager.class);
    dataInit(context);
  }

  @AfterAll
  static void closeContext() {
    context.close();
  }

  @BeforeEach
  void getTransaction() {
    session.getTransaction().begin();
  }

  @AfterEach
  void rollbackTransaction() {
    session.getTransaction().rollback();
  }

  @Test
  void checkFindBoardGamesById() {

    var boardGamesWithName = boardGamesRepository.findByName(session, "Gloomhaven").orElseThrow();

    BoardGames boardGames = boardGamesRepository.findById(boardGamesWithName.getId()).orElseThrow();

    assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
    assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
    assertThat(boardGames.getPrice()).isEqualTo(15000);
    assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
  }

  @Test
  void checkFindBoardGamesByName() {

    var boardGames = boardGamesRepository.findByName(session, "Gloomhaven").orElseThrow();

    assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
    assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
    assertThat(boardGames.getPrice()).isEqualTo(15000);
    assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
  }

  @Test
  void checkFindBoardGamesByFilter() {

    BoardGamesFilters filter = BoardGamesFilters.builder()
        .localization(Localization.EN)
        .build();

    List<BoardGames> boardGames = boardGamesRepository.findByFilter(session, filter);

    assertThat(boardGames.size()).isEqualTo(2);
  }

  @Test
  void checkBoardGameCreation() {

    var boardGames = getBoardGame();

    BoardGames boardGamesSaved = boardGamesRepository.save(boardGames);
    session.clear();

    assertThat(boardGamesRepository.findById(boardGamesSaved.getId()).orElseThrow()).isEqualTo(
        boardGames);
    assertThat(
        boardGamesRepository.findById(boardGamesSaved.getId()).orElseThrow().getClass()).isEqualTo(
        BoardGames.class);
  }

  @Test
  void checkGoodsUpdate() {

    var boardGames = (BoardGames) boardGamesRepository.findByName(session, "Gloomhaven")
        .orElseThrow();

    boardGames.setContents("play with pleasure");
    boardGamesRepository.update(boardGames);
    session.clear();
    BoardGames boardGames1 = boardGamesRepository.findById(boardGames.getId()).orElseThrow();

    assertThat(boardGames1.getContents()).isEqualTo("play with pleasure");
  }

  @Test
  void checkGoodsDeletion() {

    var goods = getBoardGame();

    boardGamesRepository.save(goods);
    session.clear();
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
