package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CustomerReadDto;
import org.board_games_shop.dto.OrderReadDto;
import org.board_games_shop.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final CustomerReadMapper customerReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        CustomerReadDto customer = Optional.ofNullable(object.getUser())
                .map(customerReadMapper::map)
                .orElse(null);
        return new OrderReadDto(
                object.getId(),
                object.getStatus(),
                object.getReservationEndDate(),
                customer
        );
    }
}
