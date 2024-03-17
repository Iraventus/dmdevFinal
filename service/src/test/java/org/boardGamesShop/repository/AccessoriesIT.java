package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Producer;
import org.boardGamesShop.entity.goods.Accessories;
import org.boardGamesShop.entity.goods.Goods;
import org.boardGamesShop.nodeModel.AddressNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;


@RequiredArgsConstructor
public class AccessoriesIT extends BaseIT {

    private final AccessoriesRepository accessoriesRepository;
    private final ProducerRepository producerRepository;

    @Test
    void checkFindAccessoryById() {
        var accessoryWithName = accessoriesRepository.findByName(entityManager, "Dragon Shield sleeves").orElseThrow();

        Accessories accessories = accessoriesRepository.findById(accessoryWithName.getId()).orElseThrow();

        assertThat(accessories.getName()).isEqualTo("Dragon Shield sleeves");
        assertThat(accessories.getPrice()).isEqualTo(500);
        assertThat(accessories.getClass()).isEqualTo(Accessories.class);
    }

    @Test
    void checkFindAccessoryByName() {
        var accessories = accessoriesRepository.findByName(entityManager, "Dragon Shield sleeves").orElseThrow();

        assertThat(accessories.getPrice()).isEqualTo(500);
        assertThat(accessories.getClass()).isEqualTo(Accessories.class);
    }

    @Test
    void checkFindAccessoryByProducerName() {
        List<Accessories> accessories = accessoriesRepository.findByProducerName(entityManager, "UltraPro");

        assertThat(accessories.size()).isEqualTo(1);
        assertThat(accessories.get(0).getName()).isEqualTo("UltraPro Sleeves");
        assertThat(accessories.get(0).getPrice()).isEqualTo(200);
    }

    @Test
    void checkAccessoryCreation() {
        var producer = getProducer();
        var accessories = getAccessories(producer);

        producerRepository.save(producer);
        Accessories accessoriesSaved = accessoriesRepository.save(accessories);
        entityManager.clear();

        assertThat(accessoriesRepository.findById(accessoriesSaved.getId()).orElseThrow()).isEqualTo(accessories);
        assertThat(accessoriesRepository.findById(accessoriesSaved.getId()).orElseThrow().getClass()).isEqualTo(Accessories.class);
    }

    @Test
    void checkAccessoryUpdate() {
        var accessories = accessoriesRepository.findByName(entityManager, "Dragon Shield sleeves").orElseThrow();

        accessories.setDescription("play with pleasure");
        accessoriesRepository.update(accessories);
        entityManager.clear();
        Accessories accessories1 = accessoriesRepository.findById(accessories.getId()).orElseThrow();

        assertThat(accessories1.getDescription()).isEqualTo("play with pleasure");
    }

    @Test
    void checkAccessoryDeletion() {
        var producer = getProducer();
        var accessories = getAccessories(producer);

        producerRepository.save(producer);
        accessoriesRepository.save(accessories);
        entityManager.clear();
        accessoriesRepository.delete(accessories);

        assertNull(accessoriesRepository.findById(accessories.getId()).orElse(null));
    }

    @Test
    void checkAllAccessoryList() {
        List<Accessories> goods = accessoriesRepository.findAll();

        assertThat(goods.size()).isEqualTo(3);
        assertThatList(goods.stream().map(Goods::getName).toList()).contains(
                "Dragon Shield sleeves", "Mayday", "UltraPro Sleeves");
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

    private Accessories getAccessories(Producer producer) {
        return Accessories.builder()
                .name("someName")
                .producer(producer)
                .quantity(1)
                .build();
    }
}
