package org.bgs.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class BoardGamesReadDto extends GoodsReadDto {

    BoardGameTheme boardGameTheme;
    Localization localization;
    String contents;
    String creator;
    String image;

    @Builder
    public BoardGamesReadDto(Long id, String name, String description, Integer quantity, Integer price,
                             BoardGameTheme boardGameTheme, Localization localization, String contents, String creator, String image) {
        super(id, name, description, quantity, price);
        this.boardGameTheme = boardGameTheme;
        this.localization = localization;
        this.contents = contents;
        this.creator = creator;
        this.image = image;
    }
}
