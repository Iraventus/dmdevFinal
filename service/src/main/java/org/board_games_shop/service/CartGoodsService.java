package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.*;
import org.board_games_shop.mapper.CartGoodsCreateEditMapper;
import org.board_games_shop.mapper.CartGoodsReadMapper;
import org.board_games_shop.repository.CartGoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartGoodsService {

    private final CartGoodsCreateEditMapper cartGoodsCreateEditMapper;
    private final CartGoodsReadMapper cartGoodsReadMapper;
    private final CartGoodsRepository cartGoodsRepository;

    public List<CartGoodsReadDto> findAll() {
        return cartGoodsRepository.findAll().stream()
                .map(cartGoodsReadMapper::map)
                .toList();
    }

    public Optional<CartGoodsReadDto> findById(Long id) {
        return cartGoodsRepository.findById(id)
                .map(cartGoodsReadMapper::map);
    }

    @Transactional
    public CartGoodsReadDto create(CartGoodsCreateEditDto cartGoodsCreateEditDto) {
        return Optional.of(cartGoodsCreateEditDto)
                .map(cartGoodsCreateEditMapper::map)
                .map(cartGoodsRepository::save)
                .map(cartGoodsReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CartGoodsReadDto> update(Long id, CartGoodsCreateEditDto cartGoodsCreateEditDto) {
        return cartGoodsRepository.findById(id)
                .map(entity -> cartGoodsCreateEditMapper.map(cartGoodsCreateEditDto, entity))
                .map(cartGoodsRepository::saveAndFlush)
                .map(cartGoodsReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return cartGoodsRepository.findById(id)
                .map(entity -> {
                    cartGoodsRepository.delete(entity);
                    cartGoodsRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
