package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Producer;
import org.boardGamesShop.nodeModel.AddressNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class ProducerIT extends BaseIT {

    private final ProducerRepository producerRepository;

    @Test
    void checkFindByIdProducer() {
        var producer = getProducer();
        producerRepository.save(producer);

        var savedProducer = producerRepository.findById(producer.getId());

        assertTrue(savedProducer.isPresent());
        assertThat(savedProducer.get().getName()).isEqualTo("someProducerName");
    }

    @Test
    void checkProducerCreation() {
        var producer = getProducer();
        var savedProducer = producerRepository.save(producer);
        entityManager.clear();

        assertThat(producerRepository.findById(savedProducer.getId()).orElseThrow()).isEqualTo(
                producer);
    }

    @Test
    void checkProducerUpdate() {
        var producer = getProducer();
        producerRepository.save(producer);

        producer.setProducerInfo("changed info");
        producerRepository.update(producer);
        entityManager.clear();

        assertThat(
                producerRepository.findById(producer.getId()).orElseThrow().getProducerInfo()).isEqualTo(
                "changed info");
    }

    @Test
    void checkProducerDeletion() {
        var producer = getProducer();
        producerRepository.save(producer);
        entityManager.clear();

        producerRepository.delete(producer);

        assertNull(producerRepository.findById(producer.getId()).orElse(null));
    }

    @Test
    void checkAllProducersList() {
        List<Producer> producers = producerRepository.findAll();

        assertThat(producers.size()).isEqualTo(3);
        assertThatList(producers.stream().map(Producer::getName).toList()).contains("UltraPro",
                "Ultimate Guard", "Card-Pro");
    }

    private Producer getProducer() {
        return Producer.builder()
                .name("someProducerName")
                .producerInfo("producerInformation")
                .legalAddress(new AddressNode()
                        .getAddressConvertedToJsonNode("someCountry", "someCity",
                                "someStreetName", 1, 1))
                .build();
    }
}
