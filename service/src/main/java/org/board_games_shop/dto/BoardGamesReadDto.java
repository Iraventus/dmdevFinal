package org.board_games_shop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.board_games_shop.entity.BoardGameTheme;
import org.board_games_shop.entity.Localization;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class BoardGamesReadDto extends GoodsReadDto {

    BoardGameTheme boardGameTheme;
    Localization localization;
    String contents;
    String creator;

    @Builder
    public BoardGamesReadDto(Long id, String name, String description, Integer quantity, Integer price,
                             BoardGameTheme boardGameTheme, Localization localization, String contents, String creator) {
        super(id, name, description, quantity, price);
        this.boardGameTheme = boardGameTheme;
        this.localization = localization;
        this.contents = contents;
        this.creator = creator;
    }
}
