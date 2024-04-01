package org.board_games_shop.repository;

import org.board_games_shop.entity.goods.BoardGames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardGamesRepository extends JpaRepository<BoardGames, Long>,
        FilterBoardGamesRepository {

    Optional<BoardGames> findByName(String name);

    List<BoardGames> findAllByNameContains(String name);
}
