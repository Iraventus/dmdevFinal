package org.board_games_shop.repository;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.entity.goods.Accessories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AccessoriesIT extends BaseIT {

    private final AccessoriesRepository accessoriesRepository;

    @Test
    void checkFindAccessoryByName() {
        var accessories = accessoriesRepository.findByName("Dragon Shield sleeves").orElseThrow();

        assertThat(accessories.getPrice()).isEqualTo(500);
    }

    @Test
    void checkFindAllByProducerName() {
        var accessories = accessoriesRepository.findAllByProducerName("UltraPro");

        assertThat(accessories.size()).isEqualTo(1);
        assertThat(accessories.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void checkFindBoardGamesByNameContains() {
        var accessories = accessoriesRepository.findAllByNameContains("leev");

        assertThat(accessories.size()).isEqualTo(2);
        assertThat(accessories.stream().map(Accessories::getName).toList()).contains("Dragon Shield sleeves", "UltraPro Sleeves");
    }
}
