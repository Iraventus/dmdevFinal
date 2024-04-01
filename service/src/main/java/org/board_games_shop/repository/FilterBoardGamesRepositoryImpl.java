package org.board_games_shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.BoardGamesFilters;
import org.board_games_shop.entity.goods.BoardGames;

import java.util.List;

import static org.board_games_shop.entity.goods.QBoardGames.boardGames;

@RequiredArgsConstructor
public class FilterBoardGamesRepositoryImpl implements FilterBoardGamesRepository {

    private final EntityManager entityManager;

    @Override
    public List<BoardGames> findAllByFilter(BoardGamesFilters filter) {
        var predicate =
                QPredicate.builder()
                        .add(filter.boardGameTheme(), boardGames.boardGameTheme::eq)
                        .add(filter.localization(), boardGames.localization::eq)
                        .build();

        return new JPAQuery<BoardGames>(entityManager)
                .select(boardGames)
                .from(boardGames)
                .where(predicate)
                .fetch();
    }
}
