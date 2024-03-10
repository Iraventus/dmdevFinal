package org.example.repository;

import static org.example.entity.goods.QBoardGames.boardGames;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.example.dto.BoardGamesFilters;
import org.example.entity.goods.BoardGames;
import org.springframework.stereotype.Repository;

@Repository
public class BoardGamesRepository extends RepositoryBase<Long, BoardGames> {

  public BoardGamesRepository(EntityManager entityManager) {
    super(BoardGames.class, entityManager);
  }

  public Optional<BoardGames> findByName(EntityManager entityManager, String name) {
    return Optional.ofNullable(new JPAQuery<BoardGames>(entityManager)
        .select(boardGames)
        .from(boardGames)
        .where(boardGames.name.eq(name))
        .fetchOne());
  }

  public List<BoardGames> findByFilter(EntityManager entityManager,
      BoardGamesFilters boardGamesFilters) {
    var predicate =
        QPredicate.builder()
            .add(boardGamesFilters.getBoardGameTheme(), boardGames.boardGameTheme::eq)
            .add(boardGamesFilters.getLocalization(), boardGames.localization::eq).buildAnd();
    return new JPAQuery<BoardGames>(entityManager)
        .select(boardGames)
        .from(boardGames)
        .where(predicate)
        .fetch();
  }
}
