package org.boardGamesShop.repository;

import org.boardGamesShop.dto.BoardGamesFilters;
import org.boardGamesShop.entity.goods.BoardGames;

import java.util.List;

public interface FilterBoardGamesRepository {

    List<BoardGames> findAllByFilter(BoardGamesFilters filter);
}
