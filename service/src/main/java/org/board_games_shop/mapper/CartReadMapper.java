package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartReadDto;
import org.board_games_shop.dto.CustomerReadDto;
import org.board_games_shop.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<Cart, CartReadDto> {

    private final CustomerReadMapper customerReadMapper;

    @Override
    public CartReadDto map(Cart object) {
        CustomerReadDto customer = Optional.ofNullable(object.getUser())
                .map(customerReadMapper::map)
                .orElse(null);
        return new CartReadDto(
                object.getId(),
                customer
        );
    }
}
