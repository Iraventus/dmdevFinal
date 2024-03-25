package org.boardGamesShop.repository;

import org.boardGamesShop.entity.goods.BoardGames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardGamesRepository extends JpaRepository<BoardGames, Long>,
        FilterBoardGamesRepository {

    Optional<BoardGames> findByName(String name);

    Page<BoardGames> findAllBy(Pageable pageable);

    List<BoardGames> findAllByNameContains(String name);
}
