package org.board_games_shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.User;

import java.util.List;

import static org.board_games_shop.entity.users.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl<T extends User> implements FilterUserRepository<T> {

    private final EntityManager entityManager;

    @Override
    public List<T> findAllByFilter(UserFilter filter) {
        var predicate = QPredicate.builder().add(filter.login(), user.login::eq).add(filter.firstname(), user.firstname::eq).add(filter.lastname(), user.lastname::eq).buildAnd();
        return new JPAQuery<T>(entityManager).select(user).from(user).where(predicate).fetch().stream()
                .map(entity -> (T) entity)
                .toList();
    }

    /**
     * Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception
     * [Request processing failed: java.lang.ClassCastException:
     * class org.board_games_shop.entity.users.Manager cannot be cast to class org.board_games_shop.entity.users.Customer
     * (org.board_games_shop.entity.users.Manager and org.board_games_shop.entity.users.Customer are in unnamed module of loader 'app')] with root cause
     */
}
