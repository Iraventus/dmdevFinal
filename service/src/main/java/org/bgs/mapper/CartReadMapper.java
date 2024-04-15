package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.Cart;
import org.bgs.dto.CartReadDto;
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
