package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.OrderCreateEditDto;
import org.bgs.entity.Order;
import org.bgs.entity.Status;
import org.bgs.entity.users.Customer;
import org.bgs.repository.CartGoodsRepository;
import org.bgs.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;
    private final CartGoodsRepository cartGoodsRepository;

    @Override
    public Order map(OrderCreateEditDto orderCreateEditDto) {
        Order order = new Order();
        copy(orderCreateEditDto, order);
        if (orderCreateEditDto.getStatus() == Status.RESERVED) {
            order.setReservationEndDate(Instant.now().plus(2, DAYS));
        }
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto orderCreateEditDto, Order order) {
        copy(orderCreateEditDto, order);
        return order;
    }

    private void copy(OrderCreateEditDto orderCreateEditDto, Order order) {
        order.setUser(getUser(orderCreateEditDto.getUserId()));
        order.setStatus(orderCreateEditDto.getStatus());
    }

    public Customer getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(customerRepository::findById)
                .orElseThrow();
    }
}
