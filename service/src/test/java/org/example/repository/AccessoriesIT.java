package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.entity.Producer;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.Goods;
import org.example.nodeModel.AddressNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AccessoriesIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static AccessoriesRepository accessoriesRepository;
  private static ProducerRepository producerRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    producerRepository = context.getBean(ProducerRepository.class);
    accessoriesRepository = context.getBean(AccessoriesRepository.class);
    session = context.getBean(EntityManager.class);
    dataInit(context);
  }

  @AfterAll
  static void closeContext() {
    context.close();
  }

  @BeforeEach
  void getTransaction() {
    session.getTransaction().begin();
  }

  @AfterEach
  void rollbackTransaction() {
    session.getTransaction().rollback();
  }

  @Test
  void checkFindAccessoryById() {

    var accessoryWithName = accessoriesRepository.findByName(session, "Dragon Shield sleeves")
        .orElseThrow();

    Accessories accessories = accessoriesRepository.findById(accessoryWithName.getId())
        .orElseThrow();

    assertThat(accessories.getName()).isEqualTo("Dragon Shield sleeves");
    assertThat(accessories.getPrice()).isEqualTo(500);
    assertThat(accessories.getClass()).isEqualTo(Accessories.class);
  }

  @Test
  void checkFindAccessoryByName() {

    var accessories = accessoriesRepository.findByName(session, "Dragon Shield sleeves")
        .orElseThrow();

    assertThat(accessories.getPrice()).isEqualTo(500);
    assertThat(accessories.getClass()).isEqualTo(Accessories.class);
  }

  @Test
  void checkFindAccessoryByProducerName() {

    List<Accessories> accessories = accessoriesRepository.findByProducerName(session, "UltraPro");

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
    session.clear();

    assertThat(accessoriesRepository.findById(accessoriesSaved.getId()).orElseThrow()).isEqualTo(
        accessories);
    assertThat(accessoriesRepository.findById(accessoriesSaved.getId()).orElseThrow()
        .getClass()).isEqualTo(Accessories.class);
  }

  @Test
  void checkAccessoryUpdate() {

    var accessories = accessoriesRepository.findByName(session, "Dragon Shield sleeves")
        .orElseThrow();

    accessories.setDescription("play with pleasure");
    accessoriesRepository.update(accessories);
    session.clear();
    Accessories accessories1 = accessoriesRepository.findById(accessories.getId()).orElseThrow();

    assertThat(accessories1.getDescription()).isEqualTo("play with pleasure");
  }

  @Test
  void checkAccessoryDeletion() {

    var producer = getProducer();
    var accessories = getAccessories(producer);

    producerRepository.save(producer);
    accessoriesRepository.save(accessories);
    session.clear();
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
