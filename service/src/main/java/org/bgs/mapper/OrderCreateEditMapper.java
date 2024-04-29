package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.OrderCreateEditDto;
import org.bgs.entity.CartGoods;
import org.bgs.entity.Order;
import org.bgs.entity.users.Customer;
import org.bgs.repository.CartGoodsRepository;
import org.bgs.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;
    private final CartGoodsRepository cartGoodsRepository;

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
        order.setCartGoods(getCartGoods(object.getGoodsIds()));
    }

    public Customer getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(customerRepository::findById)
                .orElseThrow();
    }

    public List<CartGoods> getCartGoods(List<Long> ids) {
        return cartGoodsRepository.findAllById(ids);
    }
}
