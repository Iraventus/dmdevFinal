package org.boardGamesShop.dto;

import org.boardGamesShop.entity.BoardGameTheme;
import org.boardGamesShop.entity.Localization;

public record BoardGamesFilters(BoardGameTheme boardGameTheme, Localization localization) {
}
