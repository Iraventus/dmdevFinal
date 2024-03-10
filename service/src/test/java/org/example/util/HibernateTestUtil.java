package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.entity.Cart;
import org.example.entity.CartGoods;
import org.example.entity.Order;
import org.example.entity.Producer;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.Manager;
import org.example.entity.users.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestUtil {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    static {
        postgres.start();
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.addAnnotatedClass(Goods.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Manager.class);
        configuration.addAnnotatedClass(BoardGames.class);
        configuration.addAnnotatedClass(Accessories.class);
        configuration.addAnnotatedClass(Producer.class);
        configuration.addAnnotatedClass(Cart.class);
        configuration.addAnnotatedClass(CartGoods.class);
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
