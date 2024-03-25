package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Cart;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CartIT extends BaseIT {

    private final CartRepository cartRepository;

    @Test
    void checkFindByUserId() {
        var carts = cartRepository.findAllByUserIdOrderByName(1L);

        assertThat(carts.stream().map(Cart::getName).toList()).containsExactly("first Nick cart", "second Nick cart");
    }
}
