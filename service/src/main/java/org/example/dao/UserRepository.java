package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.example.dto.UserFilter;
import org.example.entity.goods.Goods;
import org.example.entity.users.User;
import org.hibernate.Session;

import java.util.List;

import static org.example.entity.users.QUser.user;

public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findByFilters(Session session, UserFilter userFilter) {
        var predicate = QPredicate.builder()
                .add(userFilter.getLogin(), user.login::eq)
                .add(userFilter.getFirstName(), user.firstname::eq)
                .add(userFilter.getLastName(), user.lastname::eq)
                .buildAnd();
        return new JPAQuery<Goods>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
