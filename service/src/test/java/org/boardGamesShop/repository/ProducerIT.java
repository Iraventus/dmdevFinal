package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class ProducerIT extends BaseIT {

    private final ProducerRepository producerRepository;

    @Test
    void checkFindProducerByAccessoriesName() {

        var producer = producerRepository.findProducerByAccessoriesName("Dragon Shield sleeves");

        assertTrue(producer.isPresent());
        assertThat(producer.get().getId()).isEqualTo(1L);
    }

    @Test
    void checkAllProducersSortedList() {
        var sortBy = Sort.sort(Producer.class);
        var sort = sortBy.by(Producer::getName);

        List<Producer> producers = producerRepository.findAllBy(sort);

        assertThatList(producers.stream().map(Producer::getName).toList())
                .containsExactly("Card-Pro", "Ultimate Guard", "UltraPro");
    }
}
