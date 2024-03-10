package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.entity.Producer;
import org.example.nodeModel.AddressNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ProducerIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static ProducerRepository producerRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    producerRepository = context.getBean(ProducerRepository.class);
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
  void checkFindByIdProducer() {

    var savedProducer = producerRepository.findById(1L);

    assertTrue(savedProducer.isPresent());
    assertThat(savedProducer.get().getName()).isEqualTo("UltraPro");
  }

  @Test
  void checkProducerCreation() {

    var producer = getProducer();
    var savedProducer = producerRepository.save(producer);
    session.clear();

    assertThat(producerRepository.findById(savedProducer.getId()).orElseThrow()).isEqualTo(
        producer);

    producerRepository.delete(producer);
  }

  @Test
  void checkProducerUpdate() {

    var producer = producerRepository.findById(1L).orElseThrow();

    producer.setProducerInfo("changed info");
    producerRepository.update(producer);
    session.clear();

    assertThat(
        producerRepository.findById(producer.getId()).orElseThrow().getProducerInfo()).isEqualTo(
        "changed info");
  }

  @Test
  void checkProducerDeletion() {

    var producer = getProducer();
    producerRepository.save(producer);
    session.clear();

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
