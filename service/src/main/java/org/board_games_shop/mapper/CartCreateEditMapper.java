package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartCreateEditDto;
import org.board_games_shop.entity.Cart;
import org.board_games_shop.entity.users.Customer;
import org.board_games_shop.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartCreateEditMapper implements Mapper<CartCreateEditDto, Cart> {

    private final CustomerRepository customerRepository;

    @Override
    public Cart map(CartCreateEditDto object) {
        Cart cart = new Cart();
        copy(object, cart);
        return cart;
    }

    @Override
    public Cart map(CartCreateEditDto fromObject, Cart toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CartCreateEditDto object, Cart cart) {
        cart.setCart(getUser(object.getUserId()));
        cart.setUser(getUser(object.getUserId()));
    }

    public Customer getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(customerRepository::findById)
                .orElseThrow();
    }
}
