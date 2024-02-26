package org.example.entity;

import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoodsIT {

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
    void checkBoardGameCreation() {

        var boardGame = getBoardGame();
        session.persist(boardGame);
        session.evict(boardGame);

        assertThat(session.get(Goods.class, boardGame.getId()).getId()).isEqualTo(boardGame.getId());
        assertThat(session.get(Goods.class, boardGame.getId()).getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkAccessoryCreation() {

        var producer = getProducer();
        var accessory = Accessories.builder()
                .name("someName")
                .quantity(1)
                .producer(producer)
                .build();
        session.persist(producer);
        session.persist(accessory);
        session.clear();

        assertThat(session.get(Goods.class, accessory.getId()).getId()).isEqualTo(accessory.getId());
        assertThat(session.get(Goods.class, accessory.getId()).getClass()).isEqualTo(Accessories.class);
    }

    @Test
    void checkAccessoryUpdate() {

        var producer = getProducer();
        var accessory = Accessories.builder()
                .name("someName")
                .producer(producer)
                .quantity(1)
                .build();
        session.persist(producer);
        session.persist(accessory);
        session.clear();
        accessory.setPrice(1000);
        session.evict(accessory);
        session.merge(accessory);

        assertThat(session.get(Accessories.class, accessory.getId()).getPrice()).isEqualTo(1000);
    }

    @Test
    void checkGoodsDeletion() {

        var goods = getBoardGame();

        session.persist(goods);
        session.remove(goods);

        assertNull(session.get(Goods.class, goods.getId()));
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
