package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.BoardGamesCreateEditDto;
import org.board_games_shop.dto.BoardGamesReadDto;
import org.board_games_shop.mapper.BoardGamesCreateEditMapper;
import org.board_games_shop.mapper.BoardGamesReadMapper;
import org.board_games_shop.repository.BoardGamesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardGamesService {

    private final BoardGamesCreateEditMapper boardGamesCreateEditMapper;
    private final BoardGamesReadMapper boardGamesReadMapper;
    private final BoardGamesRepository boardGamesRepository;

    public List<BoardGamesReadDto> findAll() {
        return boardGamesRepository.findAll().stream()
                .map(boardGamesReadMapper::map)
                .toList();
    }

    public Optional<BoardGamesReadDto> findById(Long id) {
        return boardGamesRepository.findById(id)
                .map(boardGamesReadMapper::map);
    }

    @Transactional
    public BoardGamesReadDto create(BoardGamesCreateEditDto boardGamesCreateEditDto) {
        return Optional.of(boardGamesCreateEditDto)
                .map(boardGamesCreateEditMapper::map)
                .map(boardGamesRepository::save)
                .map(boardGamesReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<BoardGamesReadDto> update(Long id, BoardGamesCreateEditDto boardGamesCreateEditDto) {
        return boardGamesRepository.findById(id)
                .map(entity -> boardGamesCreateEditMapper.map(boardGamesCreateEditDto, entity))
                .map(boardGamesRepository::saveAndFlush)
                .map(boardGamesReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return boardGamesRepository.findById(id)
                .map(entity -> {
                    boardGamesRepository.delete(entity);
                    boardGamesRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
