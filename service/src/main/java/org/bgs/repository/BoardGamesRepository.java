package org.bgs.repository;

import org.bgs.entity.goods.BoardGames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardGamesRepository extends JpaRepository<BoardGames, Long>,
        FilterBoardGamesRepository {

    Optional<BoardGames> findByName(String name);

    List<BoardGames> findAllByNameContains(String name);
}
