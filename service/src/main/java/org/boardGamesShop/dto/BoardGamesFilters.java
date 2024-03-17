package org.boardGamesShop.dto;

import lombok.Builder;
import lombok.Value;
import org.boardGamesShop.entity.BoardGameTheme;
import org.boardGamesShop.entity.Localization;

@Value
@Builder
public class BoardGamesFilters {

    BoardGameTheme boardGameTheme;
    Localization localization;
}
