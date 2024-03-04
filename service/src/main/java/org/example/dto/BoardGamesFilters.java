package org.example.dto;

import lombok.Builder;
import lombok.Value;
import org.example.entity.BoardGameTheme;
import org.example.entity.Localization;

@Value
@Builder
public class BoardGamesFilters {

    BoardGameTheme boardGameTheme;
    Localization localization;
}
