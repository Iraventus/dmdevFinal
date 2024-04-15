package org.bgs.dto;

import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;


public record BoardGamesFilters(BoardGameTheme boardGameTheme, Localization localization) {
}
