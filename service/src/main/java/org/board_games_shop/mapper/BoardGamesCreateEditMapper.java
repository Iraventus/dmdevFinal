package org.board_games_shop.mapper;

import org.board_games_shop.dto.BoardGamesCreateEditDto;
import org.board_games_shop.entity.goods.BoardGames;
import org.springframework.stereotype.Component;

@Component
public class BoardGamesCreateEditMapper implements Mapper<BoardGamesCreateEditDto, BoardGames> {
    @Override
    public BoardGames map(BoardGamesCreateEditDto object) {
        BoardGames boardGames = new BoardGames();
        copy(object, boardGames);
        return boardGames;
    }

    @Override
    public BoardGames map(BoardGamesCreateEditDto fromObject, BoardGames toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(BoardGamesCreateEditDto object, BoardGames boardGames) {
        boardGames.setName(object.getName());
        boardGames.setPrice(object.getPrice());
        boardGames.setDescription(object.getDescription());
        boardGames.setQuantity(object.getQuantity());
        boardGames.setBoardGameTheme(object.getBoardGameTheme());
        boardGames.setCreator(object.getCreator());
        boardGames.setLocalization(object.getLocalization());
        boardGames.setContents(object.getContents());
    }
}
