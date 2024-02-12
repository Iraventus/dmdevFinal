package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_games", schema = "public")
public class BoardGames {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "genre_and_theme")
    private ThemesForBoardGames themesForBoardGames;
    @Enumerated(EnumType.STRING)
    private Localization localization;
    private String description;
    private String contents;
    private String creator;
    private int quantity;
    private int price;
}
