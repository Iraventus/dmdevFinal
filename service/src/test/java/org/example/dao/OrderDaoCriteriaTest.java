package org.example.dao;

import org.example.entity.Order;
import org.example.util.HibernateTestUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDaoCriteriaTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final OrderCriteriaDao orderCriteriaDao = OrderCriteriaDao.getInstance();
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
    void findALLOrdersByUserLogin() {

        List<Order> results = orderCriteriaDao.
                findALLOrdersByUserLogin(session, "Nick@gmail.com");

        assertThat(results).hasSize(3);
    }
}
