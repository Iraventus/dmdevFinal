package org.board_games_shop.repository;

import org.board_games_shop.dto.BoardGamesFilters;
import org.board_games_shop.entity.goods.BoardGames;

import java.util.List;

public interface FilterBoardGamesRepository {

    List<BoardGames> findAllByFilter(BoardGamesFilters filter);
}
