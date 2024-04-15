package org.bgs.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CartGoodsIT extends BaseIT {

    private final CartGoodsRepository cartGoodsRepository;

    @Test
    void checkFindByIdCart() {
        var cartsGoods = cartGoodsRepository.findAllByCartId(1L);

        assertThat(cartsGoods.size()).isEqualTo(2);
    }
}
