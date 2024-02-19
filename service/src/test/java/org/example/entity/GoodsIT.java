package org.example.entity;

import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class GoodsIT {

    @Test
    void checkBoardGameCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var boardGame = getBoardGame();
            session.beginTransaction();
            session.persist(boardGame);

            assertThat(session.get(Goods.class, boardGame.getId())).isEqualTo(boardGame);
            assertThat(session.get(Goods.class, boardGame.getId()).getClass()).isEqualTo(BoardGames.class);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkAccessoryCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var producer = getProducer();
            var accessory = Accessories.builder()
                    .name("someName")
                    .quantity(1)
                    .producer(producer)
                    .build();
            session.beginTransaction();
            session.persist(producer);
            session.persist(accessory);

            assertThat(session.get(Goods.class, accessory.getId())).isEqualTo(accessory);
            assertThat(session.get(Goods.class, accessory.getId()).getClass()).isEqualTo(Accessories.class);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkAccessoryUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            var producer = getProducer();
            var accessory = Accessories.builder()
                    .name("someName")
                    .producer(producer)
                    .quantity(1)
                    .build();
            session.persist(producer);
            session.persist(accessory);
            accessory.setPrice(1000);
            session.evict(accessory);
            session.merge(accessory);

            assertThat(session.get(Accessories.class, accessory.getId()).getPrice()).isEqualTo(1000);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkGoodsDeletion() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var goods = getBoardGame();
            session.beginTransaction();
            session.persist(goods);
            session.remove(goods);

            assertNull(session.get(Goods.class, goods.getId()));
            session.getTransaction().commit();
        }
    }

    private BoardGames getBoardGame() {
        return BoardGames.builder()
                .name("someName")
                .localization(Localization.FR)
                .quantity(1)
                .boardGameTheme(BoardGameTheme.COOP)
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
