package org.boardGamesShop.util;

import jakarta.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import org.boardGamesShop.entity.*;
import org.boardGamesShop.entity.goods.Accessories;
import org.boardGamesShop.entity.goods.BoardGames;
import org.boardGamesShop.entity.goods.Goods;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.Manager;
import org.boardGamesShop.entity.users.User;

@UtilityClass
public class TestDataImporter {

    public void importData(EntityManager entityManager) {
        Customer nick = saveUser(entityManager, "Nick@gmail.com", "Nick");
        Customer alex = saveUser(entityManager, "Alex@gmail.com", "Alex");
        saveManagerUser(entityManager, "Bob@gmail.com", "Bob", 20);

        Producer ultraPro = saveProducer(entityManager, "UltraPro");
        Producer ultimateGuard = saveProducer(entityManager, "Ultimate Guard");
        Producer cardPro = saveProducer(entityManager, "Card-Pro");

        BoardGames arkhamHorror = saveBoardGame(entityManager, "Arkham Horror",
                5000, Localization.EN);
        BoardGames gloomhaven = saveBoardGame(entityManager, "Gloomhaven",
                15000, Localization.FR);
        BoardGames mageKnight = saveBoardGame(entityManager, "Mage-Knight",
                10000, Localization.RU);
        saveBoardGame(entityManager, "Euthia",
                13000, Localization.EN);

        saveAccessory(entityManager, "Dragon Shield sleeves",
                ultimateGuard, 500);
        saveAccessory(entityManager, "Mayday",
                cardPro, 300);
        Accessories ultraProSleeves = saveAccessory(entityManager, "UltraPro Sleeves",
                ultraPro, 200);

        Cart firstNickCart = saveCart(entityManager, nick, "first Nick Cart");
        Cart secondNickCart = saveCart(entityManager, nick, "Second Nick Cart");
        Cart alexCart = saveCart(entityManager, alex, "Alex cart");

        CartGoods firstNickCartGoods = saveCartGoods(entityManager, ultraProSleeves, firstNickCart, 200);
        CartGoods secondNickCartGoods = saveCartGoods(entityManager, gloomhaven, firstNickCart, 15000);
        CartGoods thirdNickCartGoods = saveCartGoods(entityManager, mageKnight, secondNickCart, 10000);
        CartGoods firstAlexCartGoods = saveCartGoods(entityManager, arkhamHorror, alexCart, 5000);

        saveOrder(entityManager, firstNickCartGoods, Status.PAID);
        saveOrder(entityManager, secondNickCartGoods, Status.PAID);
        saveOrder(entityManager, thirdNickCartGoods, Status.RESERVED);
        saveOrder(entityManager, firstAlexCartGoods, Status.RESERVED);
    }

    private Accessories saveAccessory(EntityManager entityManager, String name,
                                      Producer producer, Integer price) {
        Accessories accessory = Accessories.builder()
                .name(name)
                .producer(producer)
                .description("Reliable protection of your cards")
                .price(price)
                .build();
        entityManager.persist(accessory);
        return accessory;
    }

    private BoardGames saveBoardGame(EntityManager entityManager, String name,
                                     Integer price, Localization localization) {
        BoardGames boardGame = BoardGames.builder()
                .name(name)
                .price(price)
                .localization(localization)
                .build();
        entityManager.persist(boardGame);
        return boardGame;
    }

    private Producer saveProducer(EntityManager entityManager, String name) {
        Producer producer = Producer.builder()
                .name(name)
                .producerInfo("Producer info")
                .build();
        entityManager.persist(producer);
        return producer;
    }

    private void saveOrder(EntityManager entityManager, CartGoods cartGoods, Status status) {
        Order order = Order.builder()
                .cartGoods(cartGoods)
                .status(status)
                .build();
        entityManager.persist(order);
    }

    private CartGoods saveCartGoods(EntityManager entityManager, Goods goods, Cart cart, Integer totalPrice) {
        CartGoods cartGoods = CartGoods.builder()
                .goods(goods)
                .cart(cart)
                .totalPrice(totalPrice)
                .build();
        entityManager.persist(cartGoods);
        return cartGoods;
    }

    private Cart saveCart(EntityManager entityManager, User user, String name) {
        Cart cart = Cart.builder()
                .name(name)
                .user(user)
                .build();
        entityManager.persist(cart);
        return cart;
    }

    private Customer saveUser(EntityManager entityManager, String login, String firstName) {
        Customer customer = Customer.builder()
                .login(login)
                .firstname(firstName)
                .build();
        entityManager.persist(customer);
        return customer;
    }

    private Manager saveManagerUser(EntityManager entityManager, String login, String firstName, Integer discount) {
        Manager manager = Manager.builder()
                .login(login)
                .firstname(firstName)
                .personalDiscount(discount)
                .build();
        entityManager.persist(manager);
        return manager;
    }
}

