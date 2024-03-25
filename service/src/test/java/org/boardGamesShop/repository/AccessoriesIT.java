package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.goods.Accessories;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    void checkPageable() {
        var pageable = PageRequest.of(0, 2, Sort.by("name"));
        var slice = accessoriesRepository.findAllBy(pageable);

        assertThat(slice.getSize()).isEqualTo(2);
        assertThat(slice.stream().map(Accessories::getName).toList()).containsExactly("Dragon Shield sleeves", "Mayday");
        while (slice.hasNext()) {
            slice = accessoriesRepository.findAllBy(slice.nextPageable());
            assertThat(slice.getSize()).isEqualTo(2);
            assertThat(slice.stream().map(Accessories::getName).toList()).containsExactly("UltraPro Sleeves");
        }
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
