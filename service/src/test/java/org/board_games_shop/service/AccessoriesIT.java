package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.AccessoriesCreateEditDto;
import org.board_games_shop.dto.AccessoriesReadDto;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class AccessoriesIT extends BaseIT {

    private static final Long ACCESSORY_1 = 1L;
    private static final Long PRODUCER_1 = 1L;
    private final AccessoriesService accessoriesService;

    @Test
    void findAll() {
        List<AccessoriesReadDto> result = accessoriesService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<AccessoriesReadDto> maybeAccessory = accessoriesService.findById(ACCESSORY_1);
        assertTrue(maybeAccessory.isPresent());
        maybeAccessory.ifPresent(game -> assertEquals("Dragon Shield sleeves", game.getName()));
    }

    @Test
    void create() {
        AccessoriesCreateEditDto accessoryDto = new AccessoriesCreateEditDto(
                "someAccName",
                "someDesc",
                5,
                100,
                PRODUCER_1
        );
        AccessoriesReadDto actualResult = accessoriesService.create(accessoryDto);

        assertEquals(accessoryDto.getName(), actualResult.getName());
        assertEquals(accessoryDto.getProducerId(), actualResult.getProducer().getId());
        assertEquals(accessoryDto.getDescription(), actualResult.getDescription());
    }

    @Test
    void update() {
        AccessoriesCreateEditDto accessoryDto = new AccessoriesCreateEditDto(
                "someAccName",
                "someDesc",
                5,
                100,
                PRODUCER_1
        );

        Optional<AccessoriesReadDto> actualResult = accessoriesService.update(ACCESSORY_1, accessoryDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(game -> {
            assertEquals(accessoryDto.getName(), game.getName());
            assertEquals(accessoryDto.getQuantity(), game.getQuantity());
            assertEquals(accessoryDto.getDescription(), game.getDescription());
        });
    }

    @Test
    void delete() {
        assertTrue(accessoriesService.delete(ACCESSORY_1));
        assertFalse(accessoriesService.delete(-12L));
    }
}
