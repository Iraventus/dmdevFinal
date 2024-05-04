package org.bgs.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class BoardGamesCreateEditDto extends GoodsCreateEditDto {

    BoardGameTheme boardGameTheme;
    Localization localization;
    String contents;
    String creator;
    MultipartFile image;

    @Builder
    public BoardGamesCreateEditDto(String name, String description, Integer quantity, Integer price,
                                   BoardGameTheme boardGameTheme, Localization localization, String contents,
                                   String creator, MultipartFile image) {
        super(name, description, quantity, price);
        this.boardGameTheme = boardGameTheme;
        this.localization = localization;
        this.contents = contents;
        this.creator = creator;
        this.image = image;
    }
}
