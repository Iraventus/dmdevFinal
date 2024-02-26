package org.example.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.example.entity.*;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Customer nick = saveUser(session, "Nick@gmail.com", "Nick");
        Customer alex = saveUser(session, "Alex@gmail.com", "Alex");

        Producer ultraPro = saveProducer(session, "UltraPro");
        Producer ultimateGuard = saveProducer(session, "Ultimate Guard");
        Producer cardPro = saveProducer(session, "Card-Pro");

        BoardGames arkhamHorror = saveBoardGame(session, "Arkham Horror",
                5000, Localization.EN);
        BoardGames gloomhaven = saveBoardGame(session, "Gloomhaven",
                15000, Localization.FR);
        BoardGames mageKnight = saveBoardGame(session, "Mage-Knight",
                10000, Localization.RU);

        saveAccessory(session, "Dragon Shield sleeves",
                ultimateGuard, 500);
        saveAccessory(session, "Mayday",
                cardPro, 300);
        Accessories ultraProSleeves = saveAccessory(session, "UltraPro Sleeves",
                ultraPro, 200);

        Cart firstNickCart = saveCart(session, nick, "first Nick Cart");
        Cart secondNickCart = saveCart(session, nick, "Second Nick Cart");
        Cart alexCart = saveCart(session, alex, "Alex cart");

        CartGoods firstNickCartGoods = saveCartGoods(session, ultraProSleeves, firstNickCart);
        CartGoods secondNickCartGoods = saveCartGoods(session, gloomhaven, firstNickCart);
        CartGoods thirdNickCartGoods = saveCartGoods(session, mageKnight, secondNickCart);
        CartGoods firstAlexCartGoods = saveCartGoods(session, arkhamHorror, alexCart);

        saveOrder(session, firstNickCartGoods, Status.PAID);
        saveOrder(session, secondNickCartGoods, Status.PAID);
        saveOrder(session, thirdNickCartGoods, Status.RESERVED);
        saveOrder(session, firstAlexCartGoods, Status.RESERVED);
    }

    private Accessories saveAccessory(Session session, String name,
                                      Producer producer, Integer price) {
        Accessories accessory = Accessories.builder()
                .name(name)
                .producer(producer)
                .description("Reliable protection of your cards")
                .price(price)
                .build();
        session.save(accessory); // не знаю почему, но persist() не сохранил данные в бд
        // (пробовал persist и persist + flush). Будет круто, если подскажешь, почему так происходит.
        return accessory;
    }

    private BoardGames saveBoardGame(Session session, String name,
                                     Integer price, Localization localization) {
        BoardGames boardGame = BoardGames.builder()
                .name(name)
                .price(price)
                .localization(localization)
                .build();
        session.persist(boardGame);
        return boardGame;
    }

    private Producer saveProducer(Session session, String name) {
        Producer producer = Producer.builder()
                .name(name)
                .producerInfo("Producer info")
                .build();
        session.persist(producer);
        return producer;
    }

    private void saveOrder(Session session, CartGoods cartGoods, Status status) {
        Order order = Order.builder()
                .cartGoods(cartGoods)
                .status(status)
                .build();
        session.save(order);
    }

    private CartGoods saveCartGoods(Session session, Goods goods, Cart cart) {
        CartGoods cartGoods = CartGoods.builder()
                .goods(goods)
                .cart(cart)
                .build();
        session.save(cartGoods);
        return cartGoods;
    }

    private Cart saveCart(Session session, User user, String name) {
        Cart cart = Cart.builder()
                .name(name)
                .user(user)
                .build();
        session.save(cart);
        return cart;
    }

    private Customer saveUser(Session session, String login, String firstName) {
        Customer customer = Customer.builder()
                .login(login)
                .firstname(firstName)
                .build();
        session.save(customer);
        return customer;
    }
}
