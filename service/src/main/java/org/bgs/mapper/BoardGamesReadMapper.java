package org.bgs.mapper;

import org.bgs.dto.BoardGamesReadDto;
import org.bgs.entity.goods.BoardGames;
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
                object.getCreator(),
                object.getImage()
        );
    }
}
