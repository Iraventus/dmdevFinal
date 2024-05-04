package org.bgs.mapper;

import org.bgs.dto.BoardGamesCreateEditDto;
import org.bgs.entity.goods.BoardGames;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

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

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> boardGames.setImage(image.getOriginalFilename()));
    }
}
