package org.board_games_shop.dto;

import org.board_games_shop.entity.BoardGameTheme;
import org.board_games_shop.entity.Localization;

public record BoardGamesFilters(BoardGameTheme boardGameTheme, Localization localization) {
}
