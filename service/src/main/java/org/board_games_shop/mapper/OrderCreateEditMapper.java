package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.OrderCreateEditDto;
import org.board_games_shop.entity.Order;
import org.board_games_shop.entity.users.Customer;
import org.board_games_shop.repository.CartGoodsRepository;
import org.board_games_shop.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setUser(getUser(object.getUserId()));
        order.setStatus(object.getStatus());
    }

    public Customer getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(customerRepository::findById)
                .orElseThrow();
    }
}
