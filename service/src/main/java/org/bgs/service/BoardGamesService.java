package org.bgs.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bgs.entity.goods.BoardGames;
import org.bgs.mapper.BoardGamesCreateEditMapper;
import org.bgs.repository.BoardGamesRepository;
import org.bgs.dto.BoardGamesCreateEditDto;
import org.bgs.dto.BoardGamesReadDto;
import org.bgs.mapper.BoardGamesReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardGamesService {

    private final BoardGamesCreateEditMapper boardGamesCreateEditMapper;
    private final BoardGamesReadMapper boardGamesReadMapper;
    private final BoardGamesRepository boardGamesRepository;
    private final ImageService imageService;

    public List<BoardGamesReadDto> findAll() {
        return boardGamesRepository.findAll().stream()
                .map(boardGamesReadMapper::map)
                .toList();
    }

    public Optional<BoardGamesReadDto> findById(Long id) {
        return boardGamesRepository.findById(id)
                .map(boardGamesReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return boardGamesRepository.findById(id)
                .map(BoardGames::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public BoardGamesReadDto create(BoardGamesCreateEditDto boardGamesCreateEditDto) {
        return Optional.of(boardGamesCreateEditDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return boardGamesCreateEditMapper.map(dto);
                })
                .map(boardGamesRepository::save)
                .map(boardGamesReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<BoardGamesReadDto> update(Long id, BoardGamesCreateEditDto boardGamesCreateEditDto) {
        return boardGamesRepository.findById(id)
                .map(entity -> {
                    uploadImage(boardGamesCreateEditDto.getImage());
                    return boardGamesCreateEditMapper.map(boardGamesCreateEditDto, entity);
                })
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
