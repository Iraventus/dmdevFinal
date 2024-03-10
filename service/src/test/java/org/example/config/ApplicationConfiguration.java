package org.example.config;

import jakarta.persistence.EntityManager;
import java.lang.reflect.Proxy;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:hibernate.properties")
@ComponentScan(basePackages = "org.example")
public class ApplicationConfiguration {

  @Bean
  public SessionFactory sessionFactory() {
    return HibernateTestUtil.buildSessionFactory();
  }

  @Bean
  public EntityManager getSession(SessionFactory sessionFactory) {
    return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
        new Class[]{Session.class},
        (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
  }
}
