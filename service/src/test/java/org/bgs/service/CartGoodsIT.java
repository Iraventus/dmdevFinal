package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.bgs.dto.CartGoodsCreateEditDto;
import org.bgs.dto.CartGoodsReadDto;
import org.bgs.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CartGoodsIT extends BaseIT {

    private static final Long CART_GOODS_1 = 1L;
    private static final Long GOODS_1 = 1L;
    private static final Long GOODS_4 = 4L;
    private static final Long CART_1 = 1L;
    private static final Long USER_1 = 1L;
    private final CartGoodsService cartGoodsService;
    private final CustomerService customerService;

    @Test
    void findAll() {
        List<CartGoodsReadDto> result = cartGoodsService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findAllByUser() {
        List<CartGoodsReadDto> result = cartGoodsService.
                showAllGoodsInCart(customerService.findById(USER_1).orElseThrow());
        assertThat(result).hasSize(2);
    }

    @Test
    void checkAddToCart() {
        var customer = customerService.findById(USER_1).orElseThrow();
        cartGoodsService.addToCart(GOODS_4, customer);
        assertThat(cartGoodsService.showAllGoodsInCart(customer).stream()
                .filter(good -> good.getGoods().getName().contains("Arkham Horror"))).hasSize(1);
    }

    @Test
    void findById() {
        Optional<CartGoodsReadDto> maybeCartGoods = cartGoodsService.findById(CART_GOODS_1);
        assertTrue(maybeCartGoods.isPresent());
        maybeCartGoods.ifPresent(cartGoods -> Assertions.assertThat(cartGoods.getTotalGoods()).isEqualTo(3));
    }

    @Test
    void create() {
        CartGoodsCreateEditDto cartGoods = new CartGoodsCreateEditDto(
                GOODS_1,
                CART_1,
                3
        );

        CartGoodsReadDto actualResult = cartGoodsService.create(cartGoods);

        assertEquals(cartGoods.getGoodsId(), actualResult.getGoods().getId());
        assertEquals(cartGoods.getCartId(), actualResult.getCart().getId());
        assertEquals(cartGoods.getTotalGoods(), actualResult.getTotalGoods());
    }

    @Test
    void update() {
        CartGoodsCreateEditDto cartGoods = new CartGoodsCreateEditDto(
                GOODS_1,
                CART_1,
                3
        );

        Optional<CartGoodsReadDto> actualResult = cartGoodsService.update(CART_GOODS_1, cartGoods);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> {
            assertEquals(cartGoods.getGoodsId(), actual.getGoods().getId());
            assertEquals(cartGoods.getCartId(), actual.getCart().getId());
            assertEquals(cartGoods.getTotalGoods(), actual.getTotalGoods());
        });
    }

    @Test
    void delete() {
        assertTrue(cartGoodsService.delete(CART_GOODS_1));
    }

    @Test
    void deleteNonExistingManager() {
        assertFalse(cartGoodsService.delete(-12L));
    }
}
