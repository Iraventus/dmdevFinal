package org.example.entity;

import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProducerIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private Session session;

    @BeforeEach
    void getTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @AfterAll
    void finish() {
        sessionFactory.close();
    }

    @Test
    void checkProducerCreation() {

        var producer = getProducer();
        session.persist(producer);
        session.clear();

        assertThat(session.get(Producer.class, producer.getId()).getId()).isEqualTo(producer.getId());
    }

    @Test
    void checkProducerUpdate() {

        var producer = getProducer();
        session.persist(producer);
        session.evict(producer);
        producer.setName("producerName");
        session.merge(producer);

        assertThat(session.get(Producer.class, producer.getId()).getName()).isEqualTo("producerName");
    }

    @Test
    void checkProducerDeletion() {

        var producer = getProducer();
        session.persist(producer);
        session.remove(producer);

        assertNull(session.get(Producer.class, producer.getId()));
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
