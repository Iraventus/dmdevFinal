package org.board_games_shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.Manager;

import java.util.List;

import static org.board_games_shop.entity.users.QManager.manager;

@RequiredArgsConstructor
public class FilterManagerRepositoryImpl implements FilterManagerRepository {

    private final EntityManager entityManager;

    @Override
    public List<Manager> findAllByFilter(UserFilter filter) {
        var predicate = QPredicate.builder().add(filter.login(), manager.login::eq).add(filter.firstname(), manager.firstname::eq).add(filter.lastname(), manager.lastname::eq).buildAnd();
        return new JPAQuery<Manager>(entityManager).select(manager).from(manager).where(predicate).fetch();
    }
}
