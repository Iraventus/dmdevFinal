package org.bgs.repository;

import org.bgs.entity.goods.BoardGames;
import org.bgs.dto.BoardGamesFilters;

import java.util.List;

public interface FilterBoardGamesRepository {

    List<BoardGames> findAllByFilter(BoardGamesFilters filter);
}
