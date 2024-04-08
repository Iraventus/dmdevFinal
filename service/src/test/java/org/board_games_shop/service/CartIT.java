package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartCreateEditDto;
import org.board_games_shop.dto.CartReadDto;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CartIT extends BaseIT {

    private static final Long CART_1 = 1L;
    private static final Long USER_1 = 1L;
    private final CartService cartService;

    @Test
    void findAll() {
        List<CartReadDto> result = cartService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void findById() {
        Optional<CartReadDto> maybeCart = cartService.findById(CART_1);
        assertTrue(maybeCart.isPresent());
        maybeCart.ifPresent(cartReadDto -> assertThat(cartReadDto.getUser().getId()).isEqualTo(USER_1));
    }

    @Test
    void create() {
        CartCreateEditDto cartDto = new CartCreateEditDto(
                4L
        );

        CartReadDto actualResult = cartService.create(cartDto);

        assertEquals(cartDto.getUserId(), actualResult.getUser().getId());
    }

    @Test
    void update() {
        CartCreateEditDto cartDto = new CartCreateEditDto(
                4L
        );

        Optional<CartReadDto> actualResult = cartService.update(CART_1, cartDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(cart -> {
            assertEquals(cartDto.getUserId(), cart.getUser().getId());
        });
    }

    @Test
    void delete() {
        assertFalse(cartService.delete(-124L));
        assertTrue(cartService.delete(CART_1));
    }
}
