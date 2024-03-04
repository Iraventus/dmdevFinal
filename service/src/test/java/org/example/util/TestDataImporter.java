package org.example.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.example.entity.*;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.Manager;
import org.example.entity.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Customer nick = saveUser(session, "Nick@gmail.com", "Nick");
        Customer alex = saveUser(session, "Alex@gmail.com", "Alex");
        saveManagerUser(session, "Bob@gmail.com", "Bob", 20);

        Producer ultraPro = saveProducer(session, "UltraPro");
        Producer ultimateGuard = saveProducer(session, "Ultimate Guard");
        Producer cardPro = saveProducer(session, "Card-Pro");

        BoardGames arkhamHorror = saveBoardGame(session, "Arkham Horror",
                5000, Localization.EN);
        BoardGames gloomhaven = saveBoardGame(session, "Gloomhaven",
                15000, Localization.FR);
        BoardGames mageKnight = saveBoardGame(session, "Mage-Knight",
                10000, Localization.RU);
        saveBoardGame(session, "Euthia",
                13000, Localization.EN);

        saveAccessory(session, "Dragon Shield sleeves",
                ultimateGuard, 500);
        saveAccessory(session, "Mayday",
                cardPro, 300);
        Accessories ultraProSleeves = saveAccessory(session, "UltraPro Sleeves",
                ultraPro, 200);

        Cart firstNickCart = saveCart(session, nick, "first Nick Cart");
        Cart secondNickCart = saveCart(session, nick, "Second Nick Cart");
        Cart alexCart = saveCart(session, alex, "Alex cart");

        CartGoods firstNickCartGoods = saveCartGoods(session, ultraProSleeves, firstNickCart, 200);
        CartGoods secondNickCartGoods = saveCartGoods(session, gloomhaven, firstNickCart, 15000);
        CartGoods thirdNickCartGoods = saveCartGoods(session, mageKnight, secondNickCart, 10000);
        CartGoods firstAlexCartGoods = saveCartGoods(session, arkhamHorror, alexCart, 5000);

        saveOrder(session, firstNickCartGoods, Status.PAID);
        saveOrder(session, secondNickCartGoods, Status.PAID);
        saveOrder(session, thirdNickCartGoods, Status.RESERVED);
        saveOrder(session, firstAlexCartGoods, Status.RESERVED);

        session.getTransaction().commit();
    }

    private Accessories saveAccessory(Session session, String name,
                                      Producer producer, Integer price) {
        Accessories accessory = Accessories.builder()
                .name(name)
                .producer(producer)
                .description("Reliable protection of your cards")
                .price(price)
                .build();
        session.persist(accessory);
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
        session.persist(order);
    }

    private CartGoods saveCartGoods(Session session, Goods goods, Cart cart, Integer totalPrice) {
        CartGoods cartGoods = CartGoods.builder()
                .goods(goods)
                .cart(cart)
                .totalPrice(totalPrice)
                .build();
        session.persist(cartGoods);
        return cartGoods;
    }

    private Cart saveCart(Session session, User user, String name) {
        Cart cart = Cart.builder()
                .name(name)
                .user(user)
                .build();
        session.persist(cart);
        return cart;
    }

    private Customer saveUser(Session session, String login, String firstName) {
        Customer customer = Customer.builder()
                .login(login)
                .firstname(firstName)
                .build();
        session.persist(customer);
        return customer;
    }

    private Manager saveManagerUser(Session session, String login, String firstName, Integer discount) {
        Manager manager = Manager.builder()
                .login(login)
                .firstname(firstName)
                .personalDiscount(discount)
                .build();
        session.persist(manager);
        return manager;
    }
}
