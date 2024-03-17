package org.boardGamesShop.util;

import lombok.experimental.UtilityClass;
import org.boardGamesShop.entity.Cart;
import org.boardGamesShop.entity.CartGoods;
import org.boardGamesShop.entity.Order;
import org.boardGamesShop.entity.Producer;
import org.boardGamesShop.entity.goods.Accessories;
import org.boardGamesShop.entity.goods.BoardGames;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.Manager;
import org.boardGamesShop.entity.users.User;
import org.boardGamesShop.entity.goods.Goods;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
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
        return configuration;
    }
}
