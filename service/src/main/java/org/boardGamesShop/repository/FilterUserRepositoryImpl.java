package org.boardGamesShop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.User;

import java.util.List;

import static org.boardGamesShop.entity.users.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.login(), user.login::eq)
                .add(filter.firstName(), user.firstname::eq)
                .add(filter.lastName(), user.lastname::eq)
                .buildAnd();
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
