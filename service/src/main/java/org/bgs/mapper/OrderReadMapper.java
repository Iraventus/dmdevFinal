package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.Order;
import org.bgs.dto.OrderReadDto;
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
