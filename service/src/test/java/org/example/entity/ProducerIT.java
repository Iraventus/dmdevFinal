package org.example.entity;

import org.example.entity.goods.Accessories;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProducerIT {

    @Test
    void checkProducerCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var producer = getProducer();
            session.beginTransaction();
            session.persist(producer);

            assertThat(session.get(Producer.class, producer.getId())).isEqualTo(producer);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkProducerUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var producer = getProducer();
            session.beginTransaction();
            session.persist(producer);
            session.evict(producer);
            producer.setName("producerName");
            session.merge(producer);

            assertThat(session.get(Producer.class, producer.getId()).getName()).isEqualTo("producerName");
            session.getTransaction().commit();
        }
    }

    @Test
    void checkAdditionAccessoryToProducer() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var producer = getProducer();
            var accessory1 = getAccessory("someName1", producer);
            var accessory2 = getAccessory("someName2", producer);
            session.persist(accessory1);
            session.persist(accessory2);
            session.persist(producer);
            session.clear();

            assertThatList(session.get(Producer.class, producer.getId()).getAccessories()).size().isEqualTo(2);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkProducerDeletion() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var producer = getProducer();
            session.beginTransaction();
            session.persist(producer);
            session.remove(producer);

            assertNull(session.get(Producer.class, producer.getId()));
            session.getTransaction().commit();
        }
    }

    private Accessories getAccessory(String name, Producer producer) {
        return Accessories.builder()
                .name(name)
                .quantity(1)
                .producer(producer)
                .build();
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
