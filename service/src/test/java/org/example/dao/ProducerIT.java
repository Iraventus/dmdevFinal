package org.example.dao;

import org.example.entity.Producer;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProducerIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private ProducerRepository producerRepository = null;


    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        producerRepository = new ProducerRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
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
        session.evict(savedProducer);

        assertThat(producerRepository.findById(savedProducer.getId()).orElseThrow()).isEqualTo(producer);

        producerRepository.delete(producer.getId());
    }

    @Test
    void checkProducerUpdate() {

        var producer = producerRepository.findById(1L).orElseThrow();

        producer.setProducerInfo("changed info");
        producerRepository.update(producer);
        session.evict(producer);

        assertThat(producerRepository.findById(producer.getId()).orElseThrow().getProducerInfo()).isEqualTo("changed info");
    }

    @Test
    void checkProducerDeletion() {

        var producer = getProducer();
        producerRepository.save(producer);
        session.evict(producer);

        producerRepository.delete(producer.getId());

        assertNull(producerRepository.findById(producer.getId()).orElse(null));
    }

    @Test
    void checkAllProducersList() {

        List<Producer> producers = producerRepository.findAll();

        assertThat(producers.size()).isEqualTo(3);
        assertThatList(producers.stream().map(Producer::getName).toList()).contains("UltraPro", "Ultimate Guard", "Card-Pro");
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
