package org.example.dao;

import org.example.entity.Localization;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.util.HibernateTestUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoodsQueryDslDaoTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final GoodsQueryDslDao goodsQueryDslDao = GoodsQueryDslDao.getInstance();
    private Session session;

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

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
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllGoods() {

        List<Goods> results = goodsQueryDslDao.findAllGoods(session);
        assertThat(results).hasSize(6);

        List<String> fullNames = results.stream().map(Goods::getName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Arkham Horror", "Dragon Shield sleeves", "Gloomhaven",
                "Mage-Knight", "Mayday", "UltraPro Sleeves");
    }

    @Test
    void findGoodsByName() {

        BoardGames results = (BoardGames) goodsQueryDslDao.findByName(session, "Gloomhaven");

        assertThat(results.getPrice()).isEqualTo(15000);
        assertThat(results.getLocalization()).isEqualTo(Localization.FR);
    }
}
