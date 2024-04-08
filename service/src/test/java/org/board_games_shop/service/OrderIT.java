package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.OrderCreateEditDto;
import org.board_games_shop.dto.OrderReadDto;
import org.board_games_shop.entity.Status;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class OrderIT extends BaseIT {

    private static final Long ORDER_1 = 1L;
    private static final Long USER_1 = 1L;
    private final OrderService orderService;

    @Test
    void findAll() {
        List<OrderReadDto> result = orderService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void findById() {
        Optional<OrderReadDto> maybeCustomer = orderService.findById(ORDER_1);
        assertTrue(maybeCustomer.isPresent());
        maybeCustomer.ifPresent(order -> assertThat(order.getUser().getId()).isEqualTo(1L));
    }

    @Test
    void create() {
        OrderCreateEditDto orderCreateEditDto = new OrderCreateEditDto(
                Status.PAID,
                USER_1
        );

        OrderReadDto actualResult = orderService.create(orderCreateEditDto);

        assertEquals(orderCreateEditDto.getStatus(), actualResult.getStatus());
        assertEquals(orderCreateEditDto.getUserId(), actualResult.getUser().getId());
    }

    @Test
    void update() {
        OrderCreateEditDto orderDto = new OrderCreateEditDto(
                Status.PAID,
                USER_1
        );

        Optional<OrderReadDto> actualResult = orderService.update(ORDER_1, orderDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> {
            assertEquals(orderDto.getStatus(), actual.getStatus());
            assertEquals(orderDto.getUserId(), actual.getUser().getId());
        });
    }

    @Test
    void delete() {
        assertTrue(orderService.delete(USER_1));
        assertFalse(orderService.delete(-12L));
    }
}
