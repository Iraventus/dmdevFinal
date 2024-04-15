package org.bgs.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
}
