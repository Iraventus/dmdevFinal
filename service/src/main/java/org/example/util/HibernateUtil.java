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
