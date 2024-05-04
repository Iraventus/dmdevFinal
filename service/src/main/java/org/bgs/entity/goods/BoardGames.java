package org.bgs.entity.goods;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.bgs.entity.BoardGameTheme;
import org.bgs.entity.Localization;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("boardGames")
public class BoardGames extends Goods {

    @Enumerated(EnumType.STRING)
    private BoardGameTheme boardGameTheme;
    @Enumerated(EnumType.STRING)
    private Localization localization;
    private String contents;
    private String creator;
    private String image;

    @Builder
    public BoardGames(String name, String description, Integer quantity, Integer price, BoardGameTheme boardGameTheme, Localization localization, String contents, String creator, String image) {
        super(name, description, quantity, price);
        this.boardGameTheme = boardGameTheme;
        this.localization = localization;
        this.contents = contents;
        this.creator = creator;
        this.image = image;
    }
}
