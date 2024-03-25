package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
class OrderIT extends BaseIT {

    private final OrderRepository orderRepository;

    @Test
    void checkFindAllByUserId() {
        var orders = orderRepository.findAllByFilter(1L);

        assertThat(orders.size()).isEqualTo(3);
    }
}
