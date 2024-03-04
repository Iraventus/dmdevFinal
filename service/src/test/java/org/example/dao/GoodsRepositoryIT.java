package org.example.dao;

import org.example.dto.BoardGamesFilters;
import org.example.entity.BoardGameTheme;
import org.example.entity.Localization;
import org.example.entity.Producer;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
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

class GoodsRepositoryIT {
    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private GoodsRepository goodsRepository = null;


    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        goodsRepository = new GoodsRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @Test
    void checkFindGoodsById() {

        var boardGamesWithName = goodsRepository.findByName(session, "Gloomhaven").orElseThrow();

        BoardGames boardGames = (BoardGames) goodsRepository.findById(boardGamesWithName.getId()).orElseThrow();

        assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
        assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
        assertThat(boardGames.getPrice()).isEqualTo(15000);
        assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkFindGoodsByName() {

        var boardGames = (BoardGames) goodsRepository.findByName(session, "Gloomhaven").orElseThrow();

        assertThat(boardGames.getLocalization()).isEqualTo(Localization.FR);
        assertThat(boardGames.getName()).isEqualTo("Gloomhaven");
        assertThat(boardGames.getPrice()).isEqualTo(15000);
        assertThat(boardGames.getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkFindAccessoryByProducerName() {

        List<Accessories> accessories = goodsRepository.findByProducerName(session, "UltraPro");

        assertThat(accessories.size()).isEqualTo(1);
        assertThat(accessories.get(0).getName()).isEqualTo("UltraPro Sleeves");
        assertThat(accessories.get(0).getPrice()).isEqualTo(200);
    }

    /**
     * Подскажи, пожалуйста, как корректно реализовать метод ниже findByFilter, при его вызове
     * ловлю ошибку Could not interpret path expression
     */
    @Test
    void checkFindGoodsByFilter() {

        BoardGamesFilters filter = BoardGamesFilters.builder()
                .localization(Localization.EN)
                .build();

        List<BoardGames> boardGames = goodsRepository.findByFilter(session, filter);

        assertThat(boardGames.size()).isEqualTo(2);
    }


    @Test
    void checkBoardGameCreation() {

        var boardGames = getBoardGame();

        BoardGames boardGamesSaved = (BoardGames) goodsRepository.save(boardGames);
        session.evict(boardGamesSaved);

        assertThat(goodsRepository.findById(boardGamesSaved.getId()).orElseThrow()).isEqualTo(boardGames);
        assertThat(goodsRepository.findById(boardGamesSaved.getId()).orElseThrow().getClass()).isEqualTo(BoardGames.class);
    }

    @Test
    void checkAccessoryCreation() {

        ProducerRepository producerRepository = new ProducerRepository(session);
        var producer = getProducer();
        var accessories = getAccessories(producer);

        producerRepository.save(producer);
        Accessories accessoriesSaved = (Accessories) goodsRepository.save(accessories);
        session.evict(accessoriesSaved);

        assertThat(goodsRepository.findById(accessoriesSaved.getId()).orElseThrow()).isEqualTo(accessories);
        assertThat(goodsRepository.findById(accessoriesSaved.getId()).orElseThrow().getClass()).isEqualTo(Accessories.class);
    }

    @Test
    void checkGoodsUpdate() {

        var boardGames = (BoardGames) goodsRepository.findByName(session, "Gloomhaven").orElseThrow();

        boardGames.setContents("play with pleasure");
        goodsRepository.update(boardGames);
        session.evict(boardGames);
        BoardGames boardGames1 = (BoardGames) goodsRepository.findById(boardGames.getId()).orElseThrow();

        assertThat(boardGames1.getContents()).isEqualTo("play with pleasure");
    }

    @Test
    void checkGoodsDeletion() {

        var goods = getBoardGame();

        goodsRepository.save(goods);
        session.clear();
        goodsRepository.delete(goods.getId());

        assertNull(goodsRepository.findById(goods.getId()).orElse(null));
    }

    @Test
    void checkAllGoodsList() {

        List<Goods> goods = goodsRepository.findAll();

        assertThat(goods.size()).isEqualTo(7);
        assertThatList(goods.stream().map(Goods::getName).toList()).contains("Arkham Horror", "Gloomhaven", "Mage-Knight",
                "Dragon Shield sleeves", "Mayday", "UltraPro Sleeves", "Euthia");
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

    private Accessories getAccessories(Producer producer) {
        return Accessories.builder()
                .name("someName")
                .producer(producer)
                .quantity(1)
                .build();
    }
}
