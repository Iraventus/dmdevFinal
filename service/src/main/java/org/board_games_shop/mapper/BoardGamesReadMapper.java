package org.board_games_shop.mapper;

import org.board_games_shop.dto.BoardGamesReadDto;
import org.board_games_shop.entity.goods.BoardGames;
import org.springframework.stereotype.Component;

@Component
public class BoardGamesReadMapper implements Mapper<BoardGames, BoardGamesReadDto> {
    @Override
    public BoardGamesReadDto map(BoardGames object) {
        return new BoardGamesReadDto(
                object.getId(),
                object.getName(),
                object.getDescription(),
                object.getQuantity(),
                object.getPrice(),
                object.getBoardGameTheme(),
                object.getLocalization(),
                object.getContents(),
                object.getCreator()
        );
    }
}
