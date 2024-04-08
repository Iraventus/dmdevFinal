package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.board_games_shop.dto.CartGoodsCreateEditDto;
import org.board_games_shop.dto.CartGoodsReadDto;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CartGoodsIT extends BaseIT {

    private static final Long CART_GOODS_1 = 1L;
    private static final Long ORDER_1 = 1L;
    private static final Long GOODS_1 = 1L;
    private static final Long CART_1 = 1L;
    private final CartGoodsService cartGoodsService;

    @Test
    void findAll() {
        List<CartGoodsReadDto> result = cartGoodsService.findAll();
        assertThat(result).hasSize(3);
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
                ORDER_1,
                3
        );

        CartGoodsReadDto actualResult = cartGoodsService.create(cartGoods);

        assertEquals(cartGoods.getGoodsId(), actualResult.getGoods().getId());
        assertEquals(cartGoods.getCartId(), actualResult.getCart().getId());
        assertEquals(cartGoods.getOrderId(), actualResult.getOrder().getId());
        assertEquals(cartGoods.getTotalGoods(), actualResult.getTotalGoods());
    }

    @Test
    void update() {
        CartGoodsCreateEditDto cartGoods = new CartGoodsCreateEditDto(
                GOODS_1,
                CART_1,
                ORDER_1,
                3
        );

        Optional<CartGoodsReadDto> actualResult = cartGoodsService.update(CART_GOODS_1, cartGoods);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> {
            assertEquals(cartGoods.getGoodsId(), actual.getGoods().getId());
            assertEquals(cartGoods.getCartId(), actual.getCart().getId());
            assertEquals(cartGoods.getOrderId(), actual.getOrder().getId());
            assertEquals(cartGoods.getTotalGoods(), actual.getTotalGoods());
        });
    }

    @Test
    void delete() {
        assertFalse(cartGoodsService.delete(-124L));
        assertTrue(cartGoodsService.delete(CART_GOODS_1));
    }
}
