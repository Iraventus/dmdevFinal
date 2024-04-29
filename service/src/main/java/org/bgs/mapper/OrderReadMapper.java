package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CartGoodsReadDto;
import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.Order;
import org.bgs.dto.OrderReadDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final CustomerReadMapper customerReadMapper;
    private final CartGoodsReadMapper cartGoodsReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        CustomerReadDto customer = Optional.ofNullable(object.getUser())
                .map(customerReadMapper::map)
                .orElse(null);
        List<CartGoodsReadDto> cartGoodsReadDto = object.getCartGoods().stream()
                .map(cartGoodsReadMapper::map).toList();
        return new OrderReadDto(
                object.getId(),
                object.getStatus(),
                object.getReservationEndDate(),
                customer,
                cartGoodsReadDto
        );
    }
}
