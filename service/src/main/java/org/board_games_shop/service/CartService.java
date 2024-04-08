package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartCreateEditDto;
import org.board_games_shop.dto.CartReadDto;
import org.board_games_shop.mapper.CartCreateEditMapper;
import org.board_games_shop.mapper.CartReadMapper;
import org.board_games_shop.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartCreateEditMapper cartCreateEditMapper;
    private final CartReadMapper cartReadMapper;
    private final CartRepository cartRepository;

    public List<CartReadDto> findAll() {
        return cartRepository.findAll().stream()
                .map(cartReadMapper::map)
                .toList();
    }

    public Optional<CartReadDto> findById(Long id) {
        return cartRepository.findById(id)
                .map(cartReadMapper::map);
    }

    @Transactional
    public CartReadDto create(CartCreateEditDto cartCreateEditDto) {
        return Optional.of(cartCreateEditDto)
                .map(cartCreateEditMapper::map)
                .map(cartRepository::save)
                .map(cartReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CartReadDto> update(Long id, CartCreateEditDto cartCreateEditDto) {
        return cartRepository.findById(id)
                .map(entity -> cartCreateEditMapper.map(cartCreateEditDto, entity))
                .map(cartRepository::saveAndFlush)
                .map(cartReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return cartRepository.findById(id)
                .map(entity -> {
                    cartRepository.delete(entity);
                    cartRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
