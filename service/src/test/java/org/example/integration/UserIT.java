package org.example.integration;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.nodeModel.AddressNode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIT {

    @Test
    void checkUserCreationInDatabase() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .login("Ivan@gmail1.com")
                    .password("12345")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(LocalDate.of(2000, 1, 19))
                    .role(Role.CUSTOMER)
                    .phone("88005553535")
                    .address(new AddressNode()
                            .getAddressConvertedToJsonNode("someCountry", "someCity",
                                    "someStreetName", 1, 1))
                    .build();
            session.persist(user);
            session.getTransaction().commit();

            assertThat(session.get(User.class, user.getId())).isEqualTo(user);
        }
    }
}
